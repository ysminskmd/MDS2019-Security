package com.yandex.academy.mobdev.client

import android.content.Context
import android.content.DialogInterface
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.CancellationSignal
import android.support.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.P)
class BiometricPrompt(
    private val context: Context,
    private val onSucceeded: () -> Unit
) : BiometricPrompt.AuthenticationCallback() {

    fun authenticate() {
        BiometricPrompt.Builder(context)
            .setTitle("Prompt")
            .setNegativeButton(
                "Cancel",
                context.mainExecutor,
                DialogInterface.OnClickListener { _, _ -> }
            )
            .build()
            .authenticate(CancellationSignal(), context.mainExecutor, this)
    }

    override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
        super.onAuthenticationError(errorCode, errString)

        Toast.makeText(context, "Authentication error: $errString", Toast.LENGTH_SHORT).show()
    }

    override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
        super.onAuthenticationHelp(helpCode, helpString)

        Toast.makeText(context, "Authentication help: $helpString", Toast.LENGTH_SHORT).show()
    }

    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
        super.onAuthenticationSucceeded(result)

        onSucceeded()
    }

    override fun onAuthenticationFailed() {
        super.onAuthenticationFailed()

        Toast.makeText(context, "Authentication failed", Toast.LENGTH_SHORT).show()
    }
}