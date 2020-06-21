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
package io.github.tonyguyot.flagorama.ui.country

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.room.Room
import io.github.tonyguyot.flagorama.data.remote.CountriesRemoteDataSource
import io.github.tonyguyot.flagorama.data.CountriesRepository
import io.github.tonyguyot.flagorama.data.local.CountriesLocalDataSource
import io.github.tonyguyot.flagorama.data.remote.RestcountriesService
import io.github.tonyguyot.flagorama.data.local.AppDatabase
import io.github.tonyguyot.flagorama.databinding.FragmentCountryBinding
import io.github.tonyguyot.flagorama.utils.hide
import io.github.tonyguyot.flagorama.utils.provideService
import io.github.tonyguyot.flagorama.utils.setTitle

/**
 * Country fragment: display details about a given country
 */
class CountryFragment : Fragment() {
    private lateinit var viewModel: CountryViewModel
    private val args: CountryFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // retrieve associated view model
        viewModel = ViewModelProvider(this).get(CountryViewModel::class.java)
        viewModel.repository = createRepository()
        viewModel.countryCode = args.countryId

        // inflate UI
        val binding = FragmentCountryBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        context ?: return binding.root

        // set the title of this fragment
        args.countryName?.let { setTitle(it) }

        // subscribe to data changes
        subscribeToData(binding)

        // setup the menu
        setHasOptionsMenu(true)

        // return the root element
        return binding.root
    }

    private fun subscribeToData(binding: FragmentCountryBinding) {
        viewModel.details.observe(viewLifecycleOwner, Observer { result ->
            binding.countryProgressBar.hide()
        })
    }

    // temporary => replace by dependency injection
    private fun createRepository(): CountriesRepository {
        val appContext = activity?.applicationContext
        val local = if (appContext != null) {
            val db = Room.databaseBuilder(appContext, AppDatabase::class.java, "flagorama-db").build()
            CountriesLocalDataSource(db.countriesDao())
        } else null

        val service = provideService(RestcountriesService::class.java, RestcountriesService.ENDPOINT)
        val remote = CountriesRemoteDataSource(service)
        return CountriesRepository(local, remote)
    }
}

