package com.example.task_029

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.FragmentTransaction
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentDetails.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentDetails : Fragment(), OnFragmentDataListener {
    private lateinit var onFragmentDataListener: OnFragmentDataListener
    private lateinit var notificationContentET: EditText
    private lateinit var saveBTN: Button
    private var notification: NotificationDetails? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        onFragmentDataListener = requireActivity() as OnFragmentDataListener
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        notification = arguments?.getSerializable("notification") as NotificationDetails?
        notificationContentET = view.findViewById(R.id.notificationContentET)
        saveBTN = view.findViewById(R.id.saveBTN)
        notificationContentET.setText(notification?.content)

        saveBTN.setOnClickListener{
            val value = notificationContentET.text
            onData(NotificationDetails(
                notification?.id,
                value.toString(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy, hh:mm:ss")).toString()
            ))
        }
        return  view
    }

    override fun onData(data: NotificationDetails?) {
        val bundle = Bundle()
        bundle.putSerializable("newNotification", data)

        val transaction = this.fragmentManager?.beginTransaction()
        val notificationsFragment = NotificationsFragment()
        notificationsFragment.arguments = bundle

        transaction?.replace(R.id.mainLLID, notificationsFragment)
        transaction?.addToBackStack(null)
        transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction?.commit()
    }
}