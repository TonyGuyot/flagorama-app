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

import io.github.tonyguyot.flagorama.data.local.model.CountryEntity
import io.github.tonyguyot.flagorama.model.Country

/**
 * Provide an abstraction to the local cache.
 * Perform read/write operations to the local cache and conversions to business logic objects.
 */
class RegionLocalDataSource(private val dao: RegionDao) {
    companion object Mapper {
        /** map a country database entity to a country logic object */
        fun toCountry(source: CountryEntity) = Country(
            code = source.code, name = source.name, flagUrl = source.flagUrl
        )

        /** map a country logic object to a country database entity */
        fun toCountryEntity(source: Country, regionCode: String) = CountryEntity(
            code = source.code, regionCode = regionCode, name = source.name, flagUrl = source.flagUrl
        )
    }

    fun getCountriesByRegion(regionId: String): List<Country> =
        dao.selectCountriesByRegion(regionId).map { toCountry(it) }

    fun saveCountries(countries: List<Country>, regionId: String) {
        dao.insertAll(countries.map {
            toCountryEntity(
                it,
                regionId
            )
        })
    }
}