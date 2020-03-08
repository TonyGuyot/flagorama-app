package io.github.tonyguyot.flagorama.data.api

import io.github.tonyguyot.flagorama.data.api.model.RestCountry
import io.github.tonyguyot.flagorama.model.Country
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Restcountries API access points
 */
interface RestcountriesService {

    companion object {
        const val ENDPOINT = "https://restcountries.eu/rest/v2/"
    }

    @GET("region/{id}?fields=alpha2Code;name")
    suspend fun getCountriesByRegion(@Path("id") regionId: String): Response<List<RestCountry>>
}