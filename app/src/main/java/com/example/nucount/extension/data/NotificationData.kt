package com.example.nucount.extension.data

import com.example.nucount.model.Notification

class NotificationData {

    companion object {
        fun dummy() : ArrayList<Notification> {
            val list = ArrayList<Notification>()

            list.add(Notification(1))
            list.add(Notification(2))
            list.add(Notification(3))
            list.add(Notification(4))
            list.add(Notification(5))

            return list
        }
    }
}