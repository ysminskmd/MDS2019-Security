package com.yandex.academy.mobdev.client

import android.content.ComponentName
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle(R.string.long_app_name)

        window.decorView.postDelayed({
            startActivity(Intent().setComponent(ComponentName(
                "com.yandex.academy.mobdev.service",
                "com.yandex.academy.mobdev.service.MainActivity"
            )))
        }, 1000)
    }
}
