/*
 * Copyright (C) 2020 Tony Guyot
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.tonyguyot.flagorama.ui.region

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import io.github.tonyguyot.flagorama.R
import io.github.tonyguyot.flagorama.data.RegionRepository
import io.github.tonyguyot.flagorama.data.remote.RestCountriesService
import io.github.tonyguyot.flagorama.data.local.AppDatabase
import io.github.tonyguyot.flagorama.data.local.RegionLocalDataSource
import io.github.tonyguyot.flagorama.data.remote.RegionRemoteDataSource
import io.github.tonyguyot.flagorama.data.utils.Resource
import io.github.tonyguyot.flagorama.data.utils.provideService
import io.github.tonyguyot.flagorama.databinding.FragmentRegionBinding
import io.github.tonyguyot.flagorama.utils.*
import timber.log.Timber

/**
 * Region fragment: display the list of countries belonging to that region
 */
class RegionFragment : Fragment() {
    private lateinit var viewModel: RegionViewModel
    private val args: RegionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // retrieve associated view model
        val viewModelFactory = RegionViewModelFactory(createRegionRepository(), args.regionId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(RegionViewModel::class.java)

        // inflate UI
        val binding = FragmentRegionBinding.inflate(inflater, container, false)
        context ?: return binding.root

        // set the title of this fragment
        args.regionName?.let { setTitle(it) }

        // setup the list of countries
        val spanCount = resources.getInteger(R.integer.max_countries_per_row)
        binding.regionCountryList.layoutManager = GridLayoutManager(activity, spanCount)
        val adapter = RegionAdapter { view, id, name ->
            val direction = RegionFragmentDirections.actionRegionFragmentToCountryFragment(id, name)
            view.findNavController().navigate(direction)
        }
        binding.regionCountryList.adapter = adapter

        // subscribe to data changes
        subscribeToData(binding, adapter)

        // setup the menu
        setHasOptionsMenu(true)

        // return the root element
        return binding.root
    }

    private fun subscribeToData(binding: FragmentRegionBinding, adapter: RegionAdapter) {
        viewModel.list.observe(viewLifecycleOwner, { result ->
            Timber.d("New data: status=%s/content=%s", result.status,
                if (result.data == null) "NULL" else "Not Null" )

            // if loading in progress, show the progress bar
            binding.regionProgressBar.showIf { result.status == Resource.Status.LOADING }

            // if loading failed, show the error message
            binding.regionErrorMessage.showIf { result.status == Resource.Status.ERROR }

            // if some data available, display it
            result?.let { adapter.submitList(it.data) }
        })
    }

    // temporary => replace by dependency injection
    private fun createRegionRepository(): RegionRepository {
        val appContext = activity?.applicationContext
        val local = if (appContext != null) {
            val db = Room.databaseBuilder(appContext, AppDatabase::class.java, "flagorama-db").build()
            RegionLocalDataSource(db.regionDao())
        } else null

        val service = provideService(
            RestCountriesService::class.java,
            RestCountriesService.ENDPOINT
        )
        val remote = RegionRemoteDataSource(service)
        return RegionRepository(local, remote)
    }
}

/** Factory for the [RegionViewModel] */
class RegionViewModelFactory(
    private val repository: RegionRepository,
    private val regionId: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(RegionRepository::class.java, String::class.java)
            .newInstance(repository, regionId)
    }
}