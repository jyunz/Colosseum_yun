package com.example.colosseum_yun.fcm

import android.os.Looper
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.logging.Handler

class MyFCM : FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)

        val myHandler = android.os.Handler(Looper.getMainLooper())

        myHandler.post {

            Toast.makeText(applicationContext, p0.notification!!.title , Toast.LENGTH_SHORT).show()
        }
    }

}