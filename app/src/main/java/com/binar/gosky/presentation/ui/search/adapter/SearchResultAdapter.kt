package com.binar.gosky.presentation.ui.search.adapter

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binar.gosky.data.network.model.tickets.TicketsItem
import com.binar.gosky.databinding.ItemTripBinding
import com.binar.gosky.util.ConvertUtil.convertISOtoDateHoursMinute
import com.binar.gosky.util.ConvertUtil.convertMinutesToHourAndMinutes
import com.binar.gosky.util.ConvertUtil.convertRupiah
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class SearchResultAdapter(private val itemClick: (TicketsItem) -> Unit, private val updateClick: (TicketsItem) -> Unit) :
    RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>() {
    private val diffCallback = object : DiffUtil.ItemCallback<TicketsItem>() {
        override fun areItemsTheSame(oldItem: TicketsItem, newItem: TicketsItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TicketsItem, newItem: TicketsItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    private var isAdmin = false

    fun submitList(list: List<TicketsItem>?) {
        differ.submitList(list)
    }

    fun checkIfUserIsAdmin(isAdmin: Boolean) {
         this.isAdmin =  isAdmin
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val binding = ItemTripBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchResultViewHolder(binding, itemClick, updateClick, isAdmin)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class SearchResultViewHolder(
        private val binding: ItemTripBinding,
        private val itemClick: (TicketsItem) -> Unit,
        private val updateClick: (TicketsItem) -> Unit,
        private val isAdmin: Boolean
    ) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(item: TicketsItem) {
            with(binding) {
                with(item) {
                    Glide.with(itemView)
                        .load(imageUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(ivAirlineLogo)
                    tvFrom.text = from
                    tvTo.text = to
                    tvTicketPrice.text = convertRupiah(price)
                    tvDepartureTime.text = convertISOtoDateHoursMinute(departureTime)
                    tvArrivalTime.text = duration?.let { convertISOtoDateHoursMinute(departureTime, it) }
                    tvDurationDeparture.text = duration?.let { convertMinutesToHourAndMinutes(it) }
                    tvDurationReturn.text = duration?.let { convertMinutesToHourAndMinutes(it) }
                    Log.d("duration", duration.toString())
                    if (returnTime.isNullOrEmpty()) {
                        txtReturn.isVisible = false
                        tvReturnTime.isVisible = false
                        ivArrowReturn.isVisible = false
                        tvArrivalTimeReturn.isVisible = false
                        tvDurationReturn.isVisible = false
                    } else {
                        tvReturnTime.text = convertISOtoDateHoursMinute(returnTime)
                        tvArrivalTimeReturn.text = duration?.let {
                            convertISOtoDateHoursMinute(returnTime,
                                it
                            )
                        }
                        tvFromReturn.text = to
                        tvToReturn.text = from
                    }
                    btnUpdate.isVisible = isAdmin
                    btnDelete.isVisible = isAdmin

                    btnUpdate.setOnClickListener {
                        updateClick(this)
                    }

                    itemView.setOnClickListener {
                        itemClick(this)
                    }
                }
            }
        }
    }
}