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
package io.github.tonyguyot.flagorama.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import io.github.tonyguyot.flagorama.R
import io.github.tonyguyot.flagorama.data.FavoriteRepository
import io.github.tonyguyot.flagorama.data.local.AppDatabase
import io.github.tonyguyot.flagorama.data.local.FavoriteLocalDataSource
import io.github.tonyguyot.flagorama.data.utils.Resource
import io.github.tonyguyot.flagorama.databinding.FragmentFavoriteBinding
import io.github.tonyguyot.flagorama.ui.region.RegionAdapter
import io.github.tonyguyot.flagorama.utils.setTitle
import io.github.tonyguyot.flagorama.utils.showIf

class FavoriteFragment : Fragment() {
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // retrieve associated view model
        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        viewModel.repository = createFavoriteRepository()

        // inflate UI
        val binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        context ?: return binding.root

        // set the title of this fragment
        setTitle(R.string.fav_title)

        // setup the list of countries
        val spanCount = resources.getInteger(R.integer.max_countries_per_row)
        binding.favCountryList.layoutManager = GridLayoutManager(activity, spanCount)
        val adapter = RegionAdapter { view, id, name ->
            val direction = FavoriteFragmentDirections.actionFavoriteFragmentToCountryFragment(id, name)
            view.findNavController().navigate(direction)
        }
        binding.favCountryList.adapter = adapter

        // subscribe to data changes
        subscribeToData(binding, adapter)

        // setup the menu
        setHasOptionsMenu(true)

        // return the root element
        return binding.root
    }

    private fun subscribeToData(binding: FragmentFavoriteBinding, adapter: RegionAdapter) {
        viewModel.list.observe(viewLifecycleOwner, { result ->
            // if loading in progress, show the progress bar
            binding.favProgressBar.showIf { result.status == Resource.Status.LOADING }

            // if no data available, show an explanation message
            binding.favEmptyMessage.showIf {
                result.status == Resource.Status.SUCCESS && result.data.isNullOrEmpty()
            }

            // if some data available, display it
            result.let { adapter.submitList(it.data) }
        })
    }

    // temporary => replace by dependency injection
    private fun createFavoriteRepository(): FavoriteRepository {
        val appContext = activity?.applicationContext
        val local = if (appContext != null) {
            val db = Room.databaseBuilder(appContext, AppDatabase::class.java, "flagorama-db").build()
            FavoriteLocalDataSource(db.favoriteDao())
        } else null
        return FavoriteRepository(local)
    }
}