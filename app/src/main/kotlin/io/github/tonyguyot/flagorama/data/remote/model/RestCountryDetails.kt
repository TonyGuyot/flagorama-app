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
package io.github.tonyguyot.flagorama.data.remote.model

import com.google.gson.annotations.SerializedName
import io.github.tonyguyot.flagorama.model.Alpha3Code

data class RestCountryDetails(
    @field:SerializedName("alpha3Code") val code: Alpha3Code,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("flag") val flagUrl: String,
    @field:SerializedName("subregion") val subRegion: String?,
    @field:SerializedName("capital") val capital: String,
    @field:SerializedName("population") val population: Long,
    @field:SerializedName("area") val area: Double,
    @field:SerializedName("nativeName") val nativeName: String?
)
