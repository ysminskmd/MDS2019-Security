package com.yandex.academy.mobdev.client

import android.content.ComponentName
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val providerUri = Uri.parse("content://com.yandex.academy.mobdev.service.provider/")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle(R.string.long_app_name)

        val adapter = MainAdapter(this, null)
        list.adapter = adapter

        activity.setOnClickListener {
            startActivity(Intent().setComponent(ComponentName(
                "com.yandex.academy.mobdev.service",
                "com.yandex.academy.mobdev.service.MainActivity"
            )))
        }

        query.setOnClickListener {
            val cursor = contentResolver.query(providerUri, null, null, null, null)
            cursor?.let { adapter.changeCursor(it) }
        }

        insert.setOnClickListener {
            contentResolver.insert(providerUri, ContentValues())
        }
    }
}
