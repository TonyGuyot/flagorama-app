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
package io.github.tonyguyot.flagorama.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.tonyguyot.flagorama.model.Region

/** ViewModel for the [HomeFragment] */
class HomeViewModel : ViewModel() {

    private val _list = MutableLiveData<List<Region>>().apply {
        value = listOf(
            Region("africa", "Africa"),
            Region("americas", "Americas"),
            Region("asia", "Asia"),
            Region("europe", "Europe"),
            Region("oceania", "Oceania")
        )
    }
    val list: LiveData<List<Region>> = _list
}