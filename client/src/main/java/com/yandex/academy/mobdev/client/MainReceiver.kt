package com.yandex.academy.mobdev.client

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MainReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, R.string.client_receiver, Toast.LENGTH_SHORT).show()
    }
}