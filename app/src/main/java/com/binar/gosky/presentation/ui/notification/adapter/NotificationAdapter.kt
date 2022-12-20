package com.binar.gosky.presentation.ui.notification.adapter

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binar.gosky.data.network.model.notification.NotificationData
import com.binar.gosky.data.network.model.tickets.TicketsItem
import com.binar.gosky.databinding.ItemNotificationBinding
import com.binar.gosky.databinding.ItemTripBinding
import com.binar.gosky.util.ConvertUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class NotificationAdapter(private val itemClick: () -> Unit) :
    RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {
    private val diffCallback = object : DiffUtil.ItemCallback<NotificationData>() {
        override fun areItemsTheSame(oldItem: NotificationData, newItem: NotificationData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NotificationData, newItem: NotificationData): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<NotificationData?>?) {
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class NotificationViewHolder(
        private val binding: ItemNotificationBinding,
        private val itemClick: () -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NotificationData) {
            with(binding) {
                with(item) {
                    tvNotificationDate.text = ConvertUtil.convertISOtoDay(createdAt)
                    tvNotificationMessage.text = message
                }
            }
        }
    }
}