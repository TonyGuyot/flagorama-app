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
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.room.Room
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar
import io.github.tonyguyot.flagorama.R
import io.github.tonyguyot.flagorama.data.remote.CountryRemoteDataSource
import io.github.tonyguyot.flagorama.data.CountryRepository
import io.github.tonyguyot.flagorama.data.FavoriteRepository
import io.github.tonyguyot.flagorama.data.local.CountryLocalDataSource
import io.github.tonyguyot.flagorama.data.remote.RestCountriesService
import io.github.tonyguyot.flagorama.data.local.AppDatabase
import io.github.tonyguyot.flagorama.data.local.FavoriteLocalDataSource
import io.github.tonyguyot.flagorama.data.utils.Resource
import io.github.tonyguyot.flagorama.data.utils.provideService
import io.github.tonyguyot.flagorama.databinding.FragmentCountryBinding
import io.github.tonyguyot.flagorama.utils.*
import timber.log.Timber

/**
 * Country fragment: display details about a given country
 */
class CountryFragment : Fragment() {
    private lateinit var viewModel: CountryViewModel
    private lateinit var rootView: View
    private val args: CountryFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // retrieve associated view model
        viewModel = ViewModelProvider(this).get(CountryViewModel::class.java)
        viewModel.detailsRepository = createCountryRepository()
        viewModel.favoriteRepository = createFavoriteRepository()
        viewModel.countryCode = args.countryId

        // inflate UI
        val binding = FragmentCountryBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.clickListener = createOnClickListener()
        binding.lifecycleOwner = this
        rootView = binding.root
        context ?: return rootView

        // set the title of this fragment
        args.countryName?.let { setTitle(it) }

        // subscribe to data changes
        subscribeToData(binding)

        // setup the menu
        setHasOptionsMenu(true)

        // return the root element
        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Inflate the menu: this adds items to the action bar if it is present
        inflater.inflate(R.menu.country, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_add_favorite -> {
            Timber.d("add to favorite")
            viewModel.addToFavorite()
            confirmAddedToFavorites()
            true
        }
        R.id.action_remove_favorite -> {
            Timber.d("remove from favorite")
            viewModel.removeFromFavorite()
            confirmRemovedFromFavorites()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val isFavorite = viewModel.isFavorite.value?.data
        if (isFavorite != null) {
            // if some data available, enable the corresponding menu options
            menu.findItem(R.id.action_add_favorite).isVisible = !isFavorite
            menu.findItem(R.id.action_remove_favorite).isVisible = isFavorite
        }
    }

    /** Subscribe to all live data from view model */
    private fun subscribeToData(binding: FragmentCountryBinding) {
        viewModel.details.observe(viewLifecycleOwner, { result ->
            // if loading in progress, show the progress bar
            binding.countryProgressBar.showIf { result.status == Resource.Status.LOADING }

            // if loading failed, show the error message
            binding.countryErrorMessage.showIf { result.status == Resource.Status.ERROR }

            // if some data available, display it
            binding.countryContent.showIf { result.data != null }

            // if translated and native names are the same, display only one
            if (result.data != null) {
                binding.countryNativeName.showIf {
                    !result.data.country.name.contains(result.data.nativeName, ignoreCase = true)
                }
            }
        })

        viewModel.isFavorite.observe(viewLifecycleOwner) { activity?.invalidateOptionsMenu() }
    }

    /** Action to perform when user clicks on the flag */
    private fun createOnClickListener(): View.OnClickListener {
        return View.OnClickListener {
            Timber.d("click on the flag")
            val flagUrl = viewModel.details.value?.data?.country?.flagUrl
            if (flagUrl != null) {
                val name = viewModel.details.value?.data?.country?.name
                val direction =
                    CountryFragmentDirections.actionCountryFragmentToFlagFragment(flagUrl, name)
                it.findNavController().navigate(direction)
            }
        }
    }

    /** Provide feedback when country added to favorites */
    private fun confirmAddedToFavorites() {
        val text = resources.getString(R.string.info_added_to_favorite, args.countryName ?: "")
        Snackbar.make(rootView, text, LENGTH_LONG).show()
    }

    /** Provide feedback when country removed from favorites */
    private fun confirmRemovedFromFavorites() {
        val text = resources.getString(R.string.info_removed_from_favorite, args.countryName ?: "")
        Snackbar.make(rootView, text, LENGTH_LONG).show()
    }

    // temporary => replace by dependency injection
    private fun createCountryRepository(): CountryRepository {
        val appContext = activity?.applicationContext
        val local = if (appContext != null) {
            val db = Room.databaseBuilder(appContext, AppDatabase::class.java, "flagorama-db").build()
            CountryLocalDataSource(db.countryDao())
        } else null

        val service = provideService(
            RestCountriesService::class.java,
            RestCountriesService.ENDPOINT
        )
        val remote = CountryRemoteDataSource(service)
        return CountryRepository(local, remote)
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
