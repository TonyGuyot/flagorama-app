package io.github.tonyguyot.flagorama.ui.region

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.github.tonyguyot.flagorama.data.api.CountriesRepository
import io.github.tonyguyot.flagorama.model.Country
import io.github.tonyguyot.flagorama.utils.Resource

/** ViewModel for the [RegionFragment] */
class RegionViewModel() : ViewModel() {

    lateinit var repository: CountriesRepository
    lateinit var regionId: String

    val list: LiveData<Resource<List<Country>>> by lazy {
        repository.observeCountries(regionId)
    }
}