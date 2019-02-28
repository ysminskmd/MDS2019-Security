package com.yandex.academy.mobdev.client

import android.content.Context
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat

class FingerprintManager(
    private val context: Context,
    private val onSucceeded: () -> Unit
) : FingerprintManagerCompat.AuthenticationCallback() {

    private val manager = FingerprintManagerCompat.from(context)

    fun authenticate() {
        if (manager.hasEnrolledFingerprints() && manager.isHardwareDetected) {
            manager.authenticate(null, 0, null, this, null)
        } else {
            Toast.makeText(context, "Authentication requested", Toast.LENGTH_SHORT).show()

            onSucceeded()
        }
    }

    override fun onAuthenticationError(errMsgId: Int, errString: CharSequence) {
        super.onAuthenticationError(errMsgId, errString)

        Toast.makeText(context, "Authentication error: $errString", Toast.LENGTH_SHORT).show()
    }

    override fun onAuthenticationHelp(helpMsgId: Int, helpString: CharSequence) {
        super.onAuthenticationHelp(helpMsgId, helpString)

        Toast.makeText(context, "Authentication help: $helpString", Toast.LENGTH_SHORT).show()
    }

    override fun onAuthenticationSucceeded(result: FingerprintManagerCompat.AuthenticationResult?) {
        super.onAuthenticationSucceeded(result)

        onSucceeded()
    }

    override fun onAuthenticationFailed() {
        super.onAuthenticationFailed()

        Toast.makeText(context, "Authentication failed", Toast.LENGTH_SHORT).show()
    }
}