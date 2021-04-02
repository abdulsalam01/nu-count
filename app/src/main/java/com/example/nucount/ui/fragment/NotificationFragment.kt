package com.example.nucount.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nucount.R
import com.example.nucount.extension.adapter.NotificationAdapter
import com.example.nucount.extension.data.NotificationData
import com.example.nucount.model.Notification

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

    private lateinit var notificationList: List<Notification>
    private lateinit var rvNotification: RecyclerView
    private lateinit var adapterNotification: NotificationAdapter

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
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.notificationList = ArrayList()
        this.notificationList = NotificationData.dummy()
        this.adapterNotification = NotificationAdapter(this.notificationList)
        this.rvNotification = view.findViewById(R.id.rv_notification) as RecyclerView

        this.rvNotification.adapter = adapterNotification
        this.rvNotification.layoutManager = LinearLayoutManager(context)
    }
}