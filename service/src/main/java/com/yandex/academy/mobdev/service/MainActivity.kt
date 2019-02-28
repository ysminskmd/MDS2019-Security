package com.yandex.academy.mobdev.service

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle(R.string.long_app_name)

        intent.data?.let { data ->
            contentResolver.openInputStream(data)?.use { inputStream ->
                Toast.makeText(this, String(inputStream.readBytes()), Toast.LENGTH_SHORT).show()
            }
        }
    }
}
