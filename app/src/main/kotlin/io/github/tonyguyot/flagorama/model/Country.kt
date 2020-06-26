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
package io.github.tonyguyot.flagorama.model

/**
 * Basic information about a country
 *
 * @property code       ISO-3166 3-letter code of the country, used as a unique identifier
 * @property name       non localized name
 * @property flagUrl    URL of the picture representing the flag of the country
 */
data class Country(val code: Alpha3Code, val name: String, val flagUrl: String)

/** Basic type for an alpha-3 country code, as defined by ISO-3166 */
typealias Alpha3Code = String
