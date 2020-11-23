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

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.tonyguyot.flagorama.data.local.model.CountryDetailsEntity
import io.github.tonyguyot.flagorama.data.local.model.CountryEntity
import io.github.tonyguyot.flagorama.data.local.model.FavoriteEntity

@Database(
    entities = [CountryEntity::class, CountryDetailsEntity::class, FavoriteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun regionDao(): RegionDao
    abstract fun countryDao(): CountryDao
    abstract fun favoriteDao(): FavoriteDao
}
