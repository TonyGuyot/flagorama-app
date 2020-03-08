package io.github.tonyguyot.flagorama.ui.region

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import io.github.tonyguyot.flagorama.data.api.CountriesLocalDataSource
import io.github.tonyguyot.flagorama.data.api.CountriesRemoteDataSource
import io.github.tonyguyot.flagorama.data.api.CountriesRepository
import io.github.tonyguyot.flagorama.data.api.RestcountriesService
import io.github.tonyguyot.flagorama.databinding.FragmentRegionBinding
import io.github.tonyguyot.flagorama.utils.hide
import io.github.tonyguyot.flagorama.utils.provideService
import io.github.tonyguyot.flagorama.utils.setTitle

/**
 * Region fragment: display the list of countries belonging to that region
 */
class RegionFragment : Fragment() {
    companion object {
        const val SPAN_COUNT = 3
    }

    private lateinit var viewModel: RegionViewModel
    private val args: RegionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // retrieve associated view model
        viewModel = ViewModelProvider(this).get(RegionViewModel::class.java)
        viewModel.repository = createRepository()
        viewModel.regionId = args.regionId

        // inflate UI
        val binding = FragmentRegionBinding.inflate(inflater, container, false)
        context ?: return binding.root

        // set the title of this fragment
        args.regionName?.let { setTitle(it) }

        // setup the list of countries
        binding.regionCountryList.layoutManager = GridLayoutManager(activity, SPAN_COUNT)
        val adapter = RegionAdapter()
        binding.regionCountryList.adapter = adapter

        // subscribe to data changes
        subscribeToData(binding, adapter)

        // setup the menu
        setHasOptionsMenu(true)

        // return the root element
        return binding.root
    }

    private fun subscribeToData(binding: FragmentRegionBinding, adapter: RegionAdapter) {
        viewModel.list.observe(viewLifecycleOwner, Observer { result ->
            binding.regionProgressBar.hide()
            result?.let { adapter.submitList(it.data) }
        })
    }

    // temporary
    private fun createRepository(): CountriesRepository {
        val local = CountriesLocalDataSource()
        val service = provideService(RestcountriesService::class.java, RestcountriesService.ENDPOINT)
        val remote = CountriesRemoteDataSource(service)
        return CountriesRepository(local, remote)
    }
}

