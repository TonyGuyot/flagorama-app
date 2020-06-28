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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.github.tonyguyot.flagorama.databinding.FragmentHomeBinding

/**
 * Home fragment: display the list of regions
 */
class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // retrieve associated view model
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        // inflate UI
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        context ?: return binding.root

        // setup the list of regions
        val adapter = HomeAdapter()
        binding.homeRegionList.adapter = adapter

        // subscribe to data changes
        subscribeToData(adapter)

        // setup the menu
        setHasOptionsMenu(true)

        // return the root element
        return binding.root
    }

    private fun subscribeToData(adapter: HomeAdapter) {
        homeViewModel.list.observe(viewLifecycleOwner, Observer { result ->
            result?.let { adapter.submitList(it) }
        })
    }
}

