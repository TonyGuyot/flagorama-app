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
package io.github.tonyguyot.flagorama.data

import io.github.tonyguyot.flagorama.data.local.CountriesLocalDataSource
import io.github.tonyguyot.flagorama.data.remote.CountriesRemoteDataSource
import io.github.tonyguyot.flagorama.utils.DatabaseFirstStrategy

/**
 * Repository to retrieve information about countries.
 */
class CountriesRepository(
    private val local: CountriesLocalDataSource?,
    private val remote: CountriesRemoteDataSource
) {

    fun observeCountries(region: String) = DatabaseFirstStrategy.getResultAsLiveData(
        databaseQuery = { local?.getCountriesByRegion(region) ?: emptyList() },
        shouldFetch = { it.isEmpty() },
        networkCall = { remote.fetchCountries(region) },
        saveCallResult = { local?.saveCountries(it, region) }
    )

    fun observeCountryDetails(countryCode: String) = DatabaseFirstStrategy.getResultAsLiveData(
        databaseQuery = { local?.getCountryDetails(countryCode) },
        shouldFetch = { it == null },
        networkCall = { remote.fetchCountryDetails(countryCode) },
        saveCallResult = { local?.saveCountryDetails(it) }
    )
}