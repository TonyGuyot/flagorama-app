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
import androidx.room.*
import io.github.tonyguyot.flagorama.data.local.model.CountryEntity
import io.github.tonyguyot.flagorama.data.local.model.FavoriteEntity
import io.github.tonyguyot.flagorama.model.Alpha3Code

/**
 * The Data Access Object for the [FavoriteEntity] class.
 */
@Dao
interface FavoriteDao {

    @Query("SELECT * FROM CountryEntity WHERE code IN (SELECT code FROM FavoriteEntity) ORDER BY name ASC")
    fun selectAllFavorites(): LiveData<List<CountryEntity>>

    @Query("SELECT * FROM FavoriteEntity WHERE code = :code")
    fun selectFavorite(code: Alpha3Code): LiveData<FavoriteEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorite: FavoriteEntity)

    @Delete
    fun delete(favorite: FavoriteEntity)
}