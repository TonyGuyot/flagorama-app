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
package io.github.tonyguyot.flagorama.ui.country

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import io.github.tonyguyot.flagorama.databinding.FragmentFlagBinding
import io.github.tonyguyot.flagorama.utils.*

/**
 * Country fragment: display details about a given country
 */
class FlagFragment : Fragment() {
    private val args: FlagFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // inflate UI
        val binding = FragmentFlagBinding.inflate(inflater, container, false)
        binding.flagUrl = args.flagUrl
        binding.lifecycleOwner = this
        context ?: return binding.root

        // set the title of this fragment
        args.countryName?.let { setTitle(it) }

        // setup the menu
        setHasOptionsMenu(true)

        // return the root element
        return binding.root
    }
}

