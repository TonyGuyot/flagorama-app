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

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.tonyguyot.flagorama.data.local.model.CountryEntity
import io.github.tonyguyot.flagorama.data.local.model.FavoriteEntity

/**
 * The Data Access Object for the [FavoriteEntity] class.
 */
@Dao
interface FavoriteDao {

    @Query("SELECT * FROM CountryEntity WHERE code IN (SELECT code FROM FavoriteEntity) ORDER BY name ASC")
    fun selectAllFavorites(): List<CountryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorite: FavoriteEntity)
}