package io.github.tonyguyot.flagorama.ui.region

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.tonyguyot.flagorama.model.Country
import io.github.tonyguyot.flagorama.model.Region

/** ViewModel for the [RegionFragment] */
class RegionViewModel : ViewModel() {

    var regionId: String? = null

    private val _list = MutableLiveData<List<Country>>().apply {
        value = listOf(
            Country("fra", "France", null),
            Country("deu", "Germany", null),
            Country("esp", "Spain", null),
            Country("ita", "Italy", null),
            Country("bel", "Belgium", null)
        )
    }
    val list: LiveData<List<Country>> = _list
}