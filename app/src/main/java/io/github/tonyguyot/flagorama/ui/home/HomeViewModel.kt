package io.github.tonyguyot.flagorama.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.tonyguyot.flagorama.model.Region

/** ViewModel for the [HomeFragment] */
class HomeViewModel : ViewModel() {

    private val _list = MutableLiveData<List<Region>>().apply {
        value = listOf(
            Region("africa", "Africa"),
            Region("america", "America"),
            Region("asia", "Asia"),
            Region("europe", "Europe"),
            Region("oceania", "Oceania")
        )
    }
    val list: LiveData<List<Region>> = _list
}