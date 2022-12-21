package com.binar.gosky.presentation.ui.notification.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binar.gosky.R
import com.binar.gosky.data.network.model.notification.NotificationData
import com.binar.gosky.databinding.ItemNotificationBinding
import com.binar.gosky.util.ConvertUtil

class NotificationAdapter(private val itemClick: (Int) -> Unit) :
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
        private val itemClick: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NotificationData) {
            with(binding) {
                with(item) {
                    tvNotificationDate.text = ConvertUtil.convertISOtoDay(createdAt)
                    tvNotificationMessage.text = message

                    isRead?.let {
                        if (it) {
                            clNotificationItem.setBackgroundResource(R.color.tropical_blue)
                        }
                    }

                    itemView.setOnClickListener {
                        id?.let {
                            clNotificationItem.setBackgroundResource(R.color.tropical_blue)
                            itemClick(it)
                        }
                    }
                }
            }
        }
    }
}