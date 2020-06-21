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
import io.github.tonyguyot.flagorama.data.local.model.CountryEntity
import io.github.tonyguyot.flagorama.model.Country
import io.github.tonyguyot.flagorama.model.CountryDetails

/**
 * Provide a abstraction to the local cache.
 * Perform read/write operations to the local cache and conversions to business logic objects.
 */
class CountriesLocalDataSource(private val dao: CountriesDao) {
    companion object Mapper {
        /** map a country database entity to a country logic object */
        fun toCountry(source: CountryEntity) = Country(
            id = source.id, name = source.name, flagUrl = source.flagUrl
        )

        /** map a country details database entity to a country details logic object */
        fun toCountryDetails(source: CountryDetailsEntity) = CountryDetails(
            country = Country(id = source.id, name = source.name, flagUrl = source.flagUrl),
            capital = source.capital, population = source.population, area = source.area
        )

        /** map a country logic object to a country database entity */
        fun toCountryEntity(source: Country, regionId: String) = CountryEntity(
            id = source.id, regionId = regionId, name = source.name, flagUrl = source.flagUrl
        )

        /** map a country details logic object to a country details database entity */
        fun toCountryDetailsEntity(source: CountryDetails) = CountryDetailsEntity(
            id = source.country.id, name = source.country.name, flagUrl = source.country.flagUrl,
            capital = source.capital, population = source.population, area = source.area
        )
    }

    fun getCountriesByRegion(regionId: String): List<Country> =
        dao.selectCountriesByRegion(regionId).map { toCountry(it) }

    fun getCountryDetails(countryCode: String): CountryDetails? =
        dao.selectCountryDetailsByCountryCode(countryCode).getOrNull(1)?.let {
            toCountryDetails(it)
        }

    fun saveCountries(countries: List<Country>, regionId: String) {
        dao.insertAll(countries.map {
            toCountryEntity(
                it,
                regionId
            )
        })
    }

    fun saveCountryDetails(countryDetails: CountryDetails?) {
        if (countryDetails != null) {
            dao.insertCountryDetails(toCountryDetailsEntity(countryDetails))
        }
    }
}