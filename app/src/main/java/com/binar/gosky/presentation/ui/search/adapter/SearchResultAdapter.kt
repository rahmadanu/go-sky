package com.binar.gosky.presentation.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binar.gosky.data.network.model.tickets.TicketsItem
import com.binar.gosky.databinding.ItemTripBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class SearchResultAdapter(private val itemClick: (TicketsItem) -> Unit) : RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>() {
    private val diffCallback = object : DiffUtil.ItemCallback<TicketsItem>() {
        override fun areItemsTheSame(oldItem: TicketsItem, newItem: TicketsItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TicketsItem, newItem: TicketsItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(movie: List<TicketsItem>?) {
        differ.submitList(movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val binding = ItemTripBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchResultViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class SearchResultViewHolder(private val binding: ItemTripBinding, private val itemClick: (TicketsItem) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TicketsItem) {
            with(binding) {
                with(item) {
                    Glide.with(itemView)
                        .load(imageUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(ivAirlineLogo)
                    tvFrom.text = from
                    tvTo.text = to
                    tvTicketPrice.text = price.toString()

                    itemView.setOnClickListener {
                        itemClick(this)
                    }
                }
            }
        }
    }
}