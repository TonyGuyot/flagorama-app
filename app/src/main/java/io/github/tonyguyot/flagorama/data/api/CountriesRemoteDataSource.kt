package io.github.tonyguyot.flagorama.data.api

import io.github.tonyguyot.flagorama.utils.RemoteDataSource

/**
 * Retrieve data about countries from the network.
 */
class CountriesRemoteDataSource(private val service: RestcountriesService): RemoteDataSource() {

    suspend fun fetchCountries(region: String) =
        fetchResource({ service.getCountriesByRegion(region) }, { it.map { it.toCountry(region) } })
}