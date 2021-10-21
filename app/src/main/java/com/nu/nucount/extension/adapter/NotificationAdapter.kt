package com.nu.nucount.extension.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nu.nucount.R
import com.nu.nucount.model.Notification

class NotificationAdapter(private val notificationList: List<Notification>) :
    RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val rowNotification = inflater.inflate(R.layout.row_notification, parent, false)

        return ViewHolder(rowNotification)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notification = this.notificationList.get(position)

        holder.bind(notification)
//        holder.itemView.setOnClickListener { listener(notification) }
    }

    override fun getItemCount(): Int {
        return notificationList.size
    }

    // view-holder
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var title = view.findViewById<TextView>(R.id.tv_title)

        fun bind(item: Notification) {
            title.text = item.id.toString()
        }
    }

}