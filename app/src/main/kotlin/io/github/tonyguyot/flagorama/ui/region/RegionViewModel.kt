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
package io.github.tonyguyot.flagorama.ui.region

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.github.tonyguyot.flagorama.data.RegionRepository
import io.github.tonyguyot.flagorama.model.Country
import io.github.tonyguyot.flagorama.data.utils.Resource

/** ViewModel for the [RegionFragment] */
class RegionViewModel(
    private val repository: RegionRepository,
    private val regionId: String
) : ViewModel() {

    val list: LiveData<Resource<List<Country>>> by lazy {
        repository.observeCountries(regionId)
    }
}