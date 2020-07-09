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

import io.github.tonyguyot.flagorama.data.remote.model.RestCountryDetails
import io.github.tonyguyot.flagorama.model.Country
import io.github.tonyguyot.flagorama.model.CountryDetails
import io.github.tonyguyot.flagorama.data.utils.BaseRemoteDataSource

/**
 * Retrieve data about countries from the network.
 */
class CountryRemoteDataSource(private val service: RestCountriesService): BaseRemoteDataSource() {

    suspend fun fetchCountryDetails(countryCode: String) =
        fetchResource({ service.getCountryDetails(countryCode) }) { toCountryDetails(it) }

    companion object Mapper {
        /** map a country network object to a country logic object */
        fun toCountryDetails(source: RestCountryDetails) = CountryDetails(
            country = toCountry(source.code, source.name, source.flagUrl),
            subRegion = source.subRegion ?: "",
            capital = source.capital,
            population = source.population,
            area = source.area,
            nativeName = source.nativeName ?: source.name
        )

        /** create a country logic object from its parameters */
        private fun toCountry(id: String, name: String, flagUrl: String) = Country(
            code = id, name = name, flagUrl = flagUrl
        )
    }
}