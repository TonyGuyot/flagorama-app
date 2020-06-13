package io.github.tonyguyot.flagorama.data

import io.github.tonyguyot.flagorama.data.api.CountriesRemoteDataSource
import io.github.tonyguyot.flagorama.data.db.CountriesDao
import io.github.tonyguyot.flagorama.utils.DatabaseFirstStrategy

/**
 * Repository to retrieve information about countries.
 */
class CountriesRepository(
    private val dao: CountriesDao?,
    private val remoteDataSource: CountriesRemoteDataSource
) {

    fun observeCountries(region: String) = DatabaseFirstStrategy.getResultAsLiveData(
        databaseQuery = { dao?.getCountriesByRegion(region) },
        shouldFetch = { it.isNullOrEmpty() },
        networkCall = { remoteDataSource.fetchCountries(region) },
        saveCallResult = { }
    )
}