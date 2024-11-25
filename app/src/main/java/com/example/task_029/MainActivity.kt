package com.example.task_029

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.appcompat.widget.Toolbar
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity(), OnFragmentDataListener {

    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        setSupportActionBar(toolbar)
        title = "Мои заметки."

        supportFragmentManager.beginTransaction().replace(R.id.mainLLID, NotificationsFragment()).commit()
    }

    fun init() {
        toolbar = findViewById(R.id.toolbar)
    }

    override fun onData(data: NotificationDetails?) {
        val bundle = Bundle()
        bundle.putSerializable("notification", data)

        val transaction = this.supportFragmentManager.beginTransaction()
        val fragmentDetails = FragmentDetails()
        fragmentDetails.arguments = bundle

        transaction.replace(R.id.mainLLID, fragmentDetails)
        transaction.addToBackStack(null)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return  true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.exitMenuMain->{
                moveTaskToBack(true);
                exitProcess(-1)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

