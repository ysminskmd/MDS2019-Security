package com.yandex.academy.mobdev.client

import android.app.Activity
import android.content.Intent
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.security.ProviderInstaller

class ProviderInstallListener(
    private val activity: Activity,
    private val requestCode: Int
) : ProviderInstaller.ProviderInstallListener {

    override fun onProviderInstalled() {
        Toast.makeText(activity, R.string.provider_installed, Toast.LENGTH_SHORT).show()
    }

    override fun onProviderInstallFailed(errorCode: Int, recoveryIntent: Intent?) {
        val availability = GoogleApiAvailability.getInstance()
        if (availability.isUserResolvableError(errorCode)) {
            availability.showErrorDialogFragment(activity, errorCode, requestCode) {
                onProviderInstallerNotAvailable()
            }
        } else {
            onProviderInstallerNotAvailable()
        }
    }

    private fun onProviderInstallerNotAvailable() {
        Toast.makeText(activity, R.string.provider_installer_not_available, Toast.LENGTH_SHORT).show()
    }
}