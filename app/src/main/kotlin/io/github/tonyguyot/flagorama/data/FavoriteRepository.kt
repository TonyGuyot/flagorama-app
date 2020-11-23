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

import io.github.tonyguyot.flagorama.data.local.FavoriteLocalDataSource
import io.github.tonyguyot.flagorama.data.utils.DatabaseOnlyStrategy
import io.github.tonyguyot.flagorama.model.Alpha3Code

/**
 * Repository to retrieve information about favorite countries.
 */
class FavoriteRepository(private val local: FavoriteLocalDataSource?) {
    fun observeFavoriteCountries() = DatabaseOnlyStrategy.getResultAsLiveData(
        databaseQuery = { local?.getFavoriteCountries() ?: emptyList() }
    )

    fun addFavorite(countryCode: Alpha3Code) {
        local?.saveFavorite(countryCode)
    }
}