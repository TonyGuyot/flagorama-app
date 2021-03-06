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
package io.github.tonyguyot.flagorama.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import io.github.tonyguyot.flagorama.data.local.RegionLocalDataSource.Mapper.toCountry
import io.github.tonyguyot.flagorama.data.local.model.FavoriteEntity
import io.github.tonyguyot.flagorama.model.Alpha3Code
import io.github.tonyguyot.flagorama.model.Country

/**
 * Provide an abstraction to the local cache.
 * Perform read/write operations to the local cache and conversions to business logic objects.
 */
class FavoriteLocalDataSource(private val dao: FavoriteDao) {

    /** Retrieve all countries that are marked as favorite */
    fun getFavoriteCountries(): LiveData<List<Country>> =
        dao.selectAllFavorites().map { favorites -> favorites.map { toCountry(it) } }

    /** Check if the country identified by [countryCode] is marked as favorite */
    fun isFavorite(countryCode: Alpha3Code): LiveData<Boolean> =
        dao.selectFavorite(countryCode).map { it != null }

    /** Mark the country identified by [countryCode] as favorite */
    fun saveFavorite(countryCode: Alpha3Code) {
        dao.insert(FavoriteEntity(countryCode))
    }

    /** Remove the country identified by [countryCode] from the favorites */
    fun deleteFavorite(countryCode: Alpha3Code) {
        dao.delete(FavoriteEntity(countryCode))
    }
}