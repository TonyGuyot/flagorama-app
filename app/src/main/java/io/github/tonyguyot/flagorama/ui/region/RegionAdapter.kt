package io.github.tonyguyot.flagorama.ui.region

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.tonyguyot.flagorama.databinding.ListItemCountryBinding
import io.github.tonyguyot.flagorama.model.Country

/**
 * Adapter for the [RecyclerView] in [RegionFragment].
 */
class RegionAdapter : ListAdapter<Country, RegionAdapter.ViewHolder>(DiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country = getItem(position)
        holder.apply {
            bind(createOnClickListener(country.id, country.name), country)
            itemView.tag = country
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemCountryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    private fun createOnClickListener(id: String, name: String): View.OnClickListener {
        return View.OnClickListener {
            //val direction = HomeFragmentDirections.actionThemeFragmentToSetsFragment(id, name)
            //it.findNavController().navigate(direction)
            Log.d("RegionAdapter", "click on item $name")
        }
    }

    class ViewHolder(private val binding: ListItemCountryBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: Country) {
            binding.apply {
                clickListener = listener
                country = item
                executePendingBindings()
            }
        }
    }
}

private class DiffCallback : DiffUtil.ItemCallback<Country>() {

    override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
        return oldItem == newItem
    }
}
