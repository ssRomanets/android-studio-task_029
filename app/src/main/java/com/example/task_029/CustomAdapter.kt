package com.example.task_029

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val notifications: MutableList<NotificationDetails?>):
    RecyclerView.Adapter<CustomAdapter.NotificationDetailsViewHolder>(){

    private var onNotificationDetailsClickListener: OnNotificationDetailsClickListener? = null

    interface OnNotificationDetailsClickListener {
        fun onNotificationDetailsClick(city: NotificationDetails?, position: Int)
    }

    class NotificationDetailsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val notificationIDTV: TextView = itemView.findViewById(R.id.notificationIDTV)
        val notificationContentTV: TextView = itemView.findViewById(R.id.notificationContentTV)
        val notificationDataTV: TextView = itemView.findViewById(R.id.notificationDataTV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationDetailsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return NotificationDetailsViewHolder(itemView)
    }

    override fun getItemCount() = notifications.size

    override fun onBindViewHolder(holder: NotificationDetailsViewHolder, position: Int) {
        val id = notifications[position]?.id
        val content = notifications[position]?.content
        val data = notifications[position]?.data

        holder.notificationIDTV.text = id.toString()
        holder.notificationContentTV.text = content
        holder.notificationDataTV.text = data

        holder.itemView.setOnClickListener{
            if (onNotificationDetailsClickListener != null) {
                onNotificationDetailsClickListener!!.onNotificationDetailsClick(notifications[position], position)
            }
        }
    }

    fun setOnCityesClickListener(onNotificationDetailsClickListener: OnNotificationDetailsClickListener) {
        this.onNotificationDetailsClickListener = onNotificationDetailsClickListener
    }
}