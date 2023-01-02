package com.binar.gosky.presentation.ui.history.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binar.gosky.data.network.model.transactions.list.TransactionListData
import com.binar.gosky.databinding.ItemHistoryTicketBinding
import com.binar.gosky.util.ConvertUtil
import com.bumptech.glide.Glide

class HistoryTicketAdapter(private val itemClick: (TransactionListData) -> Unit) :
    RecyclerView.Adapter<HistoryTicketAdapter.HistoryTicketViewHolder>() {
    private val diffCallback = object : DiffUtil.ItemCallback<TransactionListData>() {
        override fun areItemsTheSame(oldItem: TransactionListData, newItem: TransactionListData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TransactionListData, newItem: TransactionListData): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<TransactionListData>?) {
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryTicketViewHolder {
        val binding = ItemHistoryTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryTicketViewHolder(binding, itemClick)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: HistoryTicketAdapter.HistoryTicketViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class HistoryTicketViewHolder(
        private val binding: ItemHistoryTicketBinding,
        private val itemClick: (TransactionListData) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(item: TransactionListData) {
            with(binding) {
                itemView.setOnClickListener {
                    itemClick(item)
                }
                item.ticket?.apply {
                    Glide.with(itemView)
                        .load(imageUrl)
                        .into(ivImage)
                    tvFrom.text = from
                    tvTo.text = to
                    tvCategory.text = category
                    tvFlightNumber.text = flightNumber
                    tvDepartureTimeDeparture.text = ConvertUtil.convertISOtoDateHoursMinute(departureTime)
                    tvArrivalTimeDeparture.text = duration?.let { ConvertUtil.convertISOtoDateHoursMinute(departureTime, duration) }
                    tvDurationDeparture.text = duration?.let {
                        ConvertUtil.convertMinutesToHourAndMinutes(
                            it
                        )
                    }
                    tvTicketPrice.text = ConvertUtil.convertRupiah((price?.times(item.amount!!)))
                    if (returnTime.isNullOrEmpty()) {
                        tvDepartureTimeReturn.isVisible = false
                        tvArrivalTimeReturn.isVisible = false
                        tvReturnLabel.isVisible = false
                    } else {
                        tvDepartureTimeReturn.text = ConvertUtil.convertISOtoDateHoursMinute(returnTime)
                        tvArrivalTimeReturn.text = duration?.let {
                            ConvertUtil.convertISOtoDateHoursMinute(
                                returnTime
                            )
                        }
                    }
                }
            }
        }
    }
}