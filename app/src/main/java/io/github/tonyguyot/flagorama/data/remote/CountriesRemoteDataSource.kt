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
package io.github.tonyguyot.flagorama.data.remote

import io.github.tonyguyot.flagorama.data.remote.model.RestCountry
import io.github.tonyguyot.flagorama.data.remote.model.RestCountryDetails
import io.github.tonyguyot.flagorama.model.Country
import io.github.tonyguyot.flagorama.model.CountryDetails
import io.github.tonyguyot.flagorama.utils.RemoteDataSource

/**
 * Retrieve data about countries from the network.
 */
class CountriesRemoteDataSource(private val service: RestcountriesService): RemoteDataSource() {
    companion object Mapper {
        /** map a country network object to a country logic object */
        fun toCountry(source: RestCountry) = toCountry(source.id, source.name)

        /** map a country network object to a country logic object */
        fun toCountryDetails(source: RestCountryDetails) = CountryDetails(
            country = toCountry(source.id, source.name),
            capital = source.capital,
            population = source.population,
            area = source.area
        )

        /** create a country logic object from its parameters */
        private fun toCountry(id: String, name: String) = Country(
            id = id, name = name, flagUrl = "https://www.countryflags.io/$id/shiny/64.png"
        )
    }

    suspend fun fetchCountries(region: String) =
        fetchResource({ service.getCountriesByRegion(region) }) { restCountries ->
            restCountries.map { toCountry(it) }
        }

    suspend fun fetchCountryDetails(countryCode: String) =
        fetchResource({ service.getCountryDetails(countryCode) }) { toCountryDetails(it) }
}