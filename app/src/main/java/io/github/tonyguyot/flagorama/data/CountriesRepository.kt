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
        saveCallResult = { if (it != null) dao?.insertAll(it) }
    )
}