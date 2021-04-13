package com.example.nucount.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nucount.R
import com.example.nucount.core.constant.API
import com.example.nucount.extension.adapter.NotificationAdapter
import com.example.nucount.extension.data.NotificationData
import com.example.nucount.model.Notification
import com.onesignal.OneSignal

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NotificationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NotificationFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var notifString: String = "Tidak ada notifikasi"
    private lateinit var tvNotif: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_notification, container, false)

        val bundle = arguments
        if (bundle != null) notifString = bundle.getString("data", "Tidak ada notifikasi")

        this.tvNotif = v.findViewById(R.id.tv_notif)
        this.tvNotif.text = notifString

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        this.notificationList = ArrayList()
//        this.notificationList = NotificationData.dummy()
//        this.adapterNotification = NotificationAdapter(this.notificationList)
//        this.rvNotification = view.findViewById(R.id.rv_notification) as RecyclerView
//
//        this.rvNotification.adapter = adapterNotification
//        this.rvNotification.layoutManager = LinearLayoutManager(context)
    }
}