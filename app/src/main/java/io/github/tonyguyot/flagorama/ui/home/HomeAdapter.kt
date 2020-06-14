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

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.tonyguyot.flagorama.databinding.ListItemHomeBinding
import io.github.tonyguyot.flagorama.model.Region
import timber.log.Timber

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
            Timber.d("click on item %s", name)
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
