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
package io.github.tonyguyot.flagorama.data.api

import io.github.tonyguyot.flagorama.data.api.model.RestCountry
import io.github.tonyguyot.flagorama.model.Country
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Restcountries API access points
 */
interface RestcountriesService {

    companion object {
        const val ENDPOINT = "https://restcountries.eu/rest/v2/"
    }

    @GET("region/{id}?fields=alpha2Code;name")
    suspend fun getCountriesByRegion(@Path("id") regionId: String): Response<List<RestCountry>>
}