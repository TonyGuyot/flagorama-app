package io.github.tonyguyot.flagorama.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
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
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        // inflate UI
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        context ?: return binding.root

        // setup the list of regions
        val adapter = HomeAdapter()
        binding.homeRegionList.adapter = adapter

        // subscribe to data changes
        subscribeToData(binding, adapter)

        // setup the menu
        setHasOptionsMenu(true)

        // return the root element
        return binding.root
    }

    private fun subscribeToData(binding: FragmentHomeBinding, adapter: HomeAdapter) {
        homeViewModel.list.observe(viewLifecycleOwner, Observer { result ->
            result?.let { adapter.submitList(it) }
        })
    }
}

