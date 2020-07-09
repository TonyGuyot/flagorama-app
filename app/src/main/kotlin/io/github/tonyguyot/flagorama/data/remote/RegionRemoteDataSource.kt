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
import io.github.tonyguyot.flagorama.model.Country
import io.github.tonyguyot.flagorama.data.utils.BaseRemoteDataSource

/**
 * Retrieve data about countries from the network.
 */
class RegionRemoteDataSource(private val service: RestCountriesService): BaseRemoteDataSource() {

    suspend fun fetchCountries(region: String) =
        fetchResource({ service.getCountriesByRegion(region) }) { restCountries ->
            restCountries.map { toCountry(it) }
        }
    
    companion object Mapper {
        /** map a country network object to a country logic object */
        fun toCountry(source: RestCountry) = toCountry(source.code, source.name, source.flagUrl)

        /** create a country logic object from its parameters */
        private fun toCountry(id: String, name: String, flagUrl: String) = Country(
            code = id, name = name, flagUrl = flagUrl
        )
    }
}