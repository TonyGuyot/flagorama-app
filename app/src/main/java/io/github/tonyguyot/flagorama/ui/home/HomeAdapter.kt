package io.github.tonyguyot.flagorama.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.tonyguyot.flagorama.databinding.ListItemHomeBinding
import io.github.tonyguyot.flagorama.model.Region

/**
 * Adapter for the [RecyclerView] in [HomeFragment].
 */
class HomeAdapter : ListAdapter<Region, HomeAdapter.ViewHolder>(DiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val region = getItem(position)
        holder.apply {
            bind(createOnClickListener(region.id, region.name), region)
            itemView.tag = region
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemHomeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    private fun createOnClickListener(id: String, name: String): View.OnClickListener {
        return View.OnClickListener {
            Log.d("HomeAdapter", "click on item $name")
            val direction = HomeFragmentDirections.actionHomeFragmentToRegionFragment(id, name)
            it.findNavController().navigate(direction)
        }
    }

    class ViewHolder(private val binding: ListItemHomeBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: Region) {
            binding.apply {
                clickListener = listener
                region = item
                executePendingBindings()
            }
        }
    }
}

private class DiffCallback : DiffUtil.ItemCallback<Region>() {

    override fun areItemsTheSame(oldItem: Region, newItem: Region): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Region, newItem: Region): Boolean {
        return oldItem == newItem
    }
}
