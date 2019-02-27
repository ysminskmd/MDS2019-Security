package com.yandex.academy.mobdev.client

import android.content.ContentValues
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.FileProvider
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.security.ProviderInstaller
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

private const val SERVICE_ID = "com.yandex.academy.mobdev.service"
private const val SERVICE_PACKAGE = "com.yandex.academy.mobdev.service"

private const val RECEIVER_ACTION = "com.yandex.academy.mobdev.client.receiver"

private const val ERROR_DIALOG_REQUEST_CODE = 100

class MainActivity : AppCompatActivity() {

    private val providerUri = Uri.parse("content://com.yandex.academy.mobdev.service.provider/")

    private val providerInstallListener = ProviderInstallListener(this, ERROR_DIALOG_REQUEST_CODE)
    private var retryProviderInstall = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setTitle(R.string.long_app_name)

        val adapter = MainAdapter(this, null)
        list.adapter = adapter

        val manager = LocalBroadcastManager.getInstance(this)
        manager.registerReceiver(MainReceiver(), IntentFilter(RECEIVER_ACTION))

        activity.setOnClickListener {
            val message = getMessage()
            startActivity(Intent()
                .setClassName(SERVICE_ID, "$SERVICE_PACKAGE.MainActivity")
                .setDataAndType(message, contentResolver.getType(message))
                .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            )
        }

        query.setOnClickListener {
            val cursor = contentResolver.query(providerUri, null, null, null, null)
            cursor?.let { adapter.changeCursor(it) }
        }

        insert.setOnClickListener {
            contentResolver.insert(providerUri, ContentValues())
        }

        send.setOnClickListener {
            val intent = Intent().setClassName(SERVICE_ID, "$SERVICE_PACKAGE.MainReceiver")
            sendBroadcast(intent, "com.yandex.academy.mobdev.client.receiver")
        }

        local.setOnClickListener {
            manager.sendBroadcast(Intent(RECEIVER_ACTION))
        }

        install.setOnClickListener {
            ProviderInstaller.installIfNeededAsync(this, providerInstallListener)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ERROR_DIALOG_REQUEST_CODE) {
            retryProviderInstall = true
        }
    }

    override fun onPostResume() {
        super.onPostResume()

        if (retryProviderInstall) {
            ProviderInstaller.installIfNeededAsync(this, providerInstallListener)
        }
        retryProviderInstall = false
    }

    private fun getMessage(): Uri {
        val messagesDir = File(filesDir, "messages")
        messagesDir.mkdirs()
        val messageFile = File(messagesDir, "message.txt")
        messageFile.writeText("This is secret message from client activity")
        val authority = "com.yandex.academy.mobdev.client.provider"
        return FileProvider.getUriForFile(this, authority, messageFile)
    }
}
