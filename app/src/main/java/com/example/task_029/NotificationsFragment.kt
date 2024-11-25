package com.example.task_029

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import androidx.recyclerview.widget.LinearLayoutManager

/**
 * A simple [Fragment] subclass.
 * Use the [NotificationsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

var notifications = mutableListOf<NotificationDetails?>()

class NotificationsFragment : Fragment() {

    private lateinit var onFragmentDataListener: OnFragmentDataListener
    private var adapter: CustomAdapter? = null

    private lateinit var notificationET: EditText
    private lateinit var saveBTN: Button
    private lateinit var recyclerViewRV: RecyclerView

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        onFragmentDataListener = requireActivity() as OnFragmentDataListener
        val view = inflater.inflate(R.layout.fragment_notifications, container, false)

        recyclerViewRV = view.findViewById(R.id.recyclerViewRV)
        recyclerViewRV.layoutManager = LinearLayoutManager(context)

        notificationET = view.findViewById(R.id.notificationET)
        saveBTN = view.findViewById(R.id.saveBTN)

        saveBTN.setOnClickListener{
            if (notificationET.text.toString() != "")
            {
                val size = notifications.size + 1
                notifications.add(
                    NotificationDetails(
                        size,
                        notificationET.text.toString(),
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy, hh:mm:ss")).toString()
                    )
                )

                fillNotificationsRV()
                notificationET.text.clear()
            }
        }

        return  view
    }

    override fun onResume() {
        super.onResume()
        val notification = arguments?.getSerializable("newNotification") as NotificationDetails?
        if (notification != null) {
            var index = search(notifications, notification?.id)
            swap(notifications, index, notification)
            fillNotificationsRV()
        }
    }

    fun fillNotificationsRV() {
        adapter = CustomAdapter(notifications)
        recyclerViewRV.adapter = adapter
        recyclerViewRV.setHasFixedSize(true)
        adapter?.setOnCityesClickListener( object :
            CustomAdapter.OnNotificationDetailsClickListener {
            override fun onNotificationDetailsClick(notification: NotificationDetails?, position: Int) {
                onFragmentDataListener.onData(notification)
            }
        })
    }

    fun swap(notifications: MutableList<NotificationDetails?>, index: Int, notification: NotificationDetails?) {
        notifications.add(index + 1, notification)
        notifications.removeAt(index)
    }

    fun search(notifications: MutableList<NotificationDetails?>, notificationId: Int?): Int {
        var result = -1
        for (i in notifications.indices) {
            if (notificationId == notifications[i]?.id) result = i
        }
        return  result
    }
}