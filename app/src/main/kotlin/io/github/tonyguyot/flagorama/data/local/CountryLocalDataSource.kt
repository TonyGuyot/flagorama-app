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

import io.github.tonyguyot.flagorama.data.local.model.CountryDetailsEntity
import io.github.tonyguyot.flagorama.model.Country
import io.github.tonyguyot.flagorama.model.CountryDetails

/**
 * Provide a abstraction to the local cache.
 * Perform read/write operations to the local cache and conversions to business logic objects.
 */
class CountryLocalDataSource(private val dao: CountryDao) {
    companion object Mapper {
        /** map a country details database entity to a country details logic object */
        fun toCountryDetails(source: CountryDetailsEntity) = CountryDetails(
            country = Country(code = source.code, name = source.name, flagUrl = source.flagUrl),
            capital = source.capital, population = source.population, area = source.area,
            nativeName = source.nativeName
        )

        /** map a country details logic object to a country details database entity */
        fun toCountryDetailsEntity(source: CountryDetails) = CountryDetailsEntity(
            code = source.country.code, name = source.country.name, flagUrl = source.country.flagUrl,
            capital = source.capital, population = source.population, area = source.area,
            nativeName = source.nativeName
        )
    }

    fun getCountryDetails(countryCode: String): CountryDetails? =
        dao.selectCountryDetailsByCountryCode(countryCode).getOrNull(0)?.let {
            toCountryDetails(it)
        }

    fun saveCountryDetails(countryDetails: CountryDetails?) {
        if (countryDetails != null) {
            dao.insertCountryDetails(toCountryDetailsEntity(countryDetails))
        }
    }
}