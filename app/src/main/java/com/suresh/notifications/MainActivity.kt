package com.suresh.notifications

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.suresh.notifications.CustomNotificationsManager.showNotification
import com.suresh.notifications.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            btnOne.setOnClickListener {
                val channel = CustomNotificationsManager.getChannels()["ch1"]
                channel?.let { it1 -> showNotification(it1) } //Low
            }

            btnTwo.setOnClickListener {
                val channel = CustomNotificationsManager.getChannels()["ch2"]
                channel?.let { it1 -> showNotification(it1) } // High
            }

            btnThree.setOnClickListener {
                val channel = CustomNotificationsManager.getChannels()["ch3"]
                channel?.let { it1 -> showNotification(it1) } // High
            }

            btnFour.setOnClickListener {
                val channel = CustomNotificationsManager.getChannels()["ch4"]
                channel?.let { it1 -> showNotification(it1) } // High
            }

            btnFive.setOnClickListener {
                val channel = CustomNotificationsManager.getChannels()["ch5"]
                channel?.let { it1 -> showNotification(it1) } // High
            }

            btnSix.setOnClickListener {
                val channel = CustomNotificationsManager.getChannels()["ch6"]
                channel?.let { it1 -> showNotification(it1) } // High
            }

            btnSeven.setOnClickListener {
                val channel = CustomNotificationsManager.getChannels()["ch7"]
                channel?.let { it1 -> showNotification(it1) } // High
            }

            btnEight.setOnClickListener {
                val channel = CustomNotificationsManager.getChannels()["ch8"]
                channel?.let { it1 -> showNotification(it1) } // High
            }

            btnNine.setOnClickListener {
                val channel = CustomNotificationsManager.getChannels()["ch9"]
                channel?.let { it1 -> showNotification(it1) } // High
            }

            btnTen.setOnClickListener {
                val channel = CustomNotificationsManager.getChannels()["ch10"]
                channel?.let { it1 -> showNotification(it1) } // High
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        // it will be called when we click on notification and Main activity is already exist in stack
        Toast.makeText(this, "onNewIntent called", Toast.LENGTH_SHORT).show()
    }
}