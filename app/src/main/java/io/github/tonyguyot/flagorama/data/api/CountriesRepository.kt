package io.github.tonyguyot.flagorama.data.api

import androidx.lifecycle.liveData
import io.github.tonyguyot.flagorama.model.Country
import io.github.tonyguyot.flagorama.utils.Resource
import kotlinx.coroutines.Dispatchers

/**
 * Repository to retrieve information about countries.
 */
class CountriesRepository(
    private val localDataSource: CountriesLocalDataSource,
    private val remoteDataSource: CountriesRemoteDataSource) {

    fun observeCountries(region: String) =
        liveData(Dispatchers.IO) {
            emit(Resource.Loading<List<Country>>())
            emit(remoteDataSource.fetchCountries(region))
        }
}