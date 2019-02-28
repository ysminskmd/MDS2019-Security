package com.yandex.academy.mobdev.client

import android.app.Activity
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.safetynet.SafeBrowsingThreat
import com.google.android.gms.safetynet.SafetyNet

class SafetyNetClient(private val activity: Activity) {

    private val client = SafetyNet.getClient(activity)

    init {
        client.initSafeBrowsing()
    }

    fun lookupUri(uri: String) {
        client.lookupUri(
            uri,
            "AIzaSyAFmAzb9ziRI0pQ4pmFF0LiGy4TxkppgZ0",
            SafeBrowsingThreat.TYPE_POTENTIALLY_HARMFUL_APPLICATION,
            SafeBrowsingThreat.TYPE_SOCIAL_ENGINEERING
        )
            .addOnSuccessListener(activity) { sbResponse ->
                if (sbResponse.detectedThreats.isEmpty()) {
                    Toast.makeText(activity, R.string.no_threats_found, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity, R.string.threats_found, Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener(activity) { e: Exception ->
                val message = if (e is ApiException) {
                    CommonStatusCodes.getStatusCodeString(e.statusCode)
                } else {
                    e.message
                }
                Toast.makeText(activity, "Error: $message", Toast.LENGTH_SHORT).show()
            }
    }
    
    fun isVerifyAppsEnabled() {
        client.isVerifyAppsEnabled
            .addOnCompleteListener { task ->
                val resId = if (task.isSuccessful) {
                    if (task.result?.isVerifyAppsEnabled == true) {
                        R.string.verify_apps_enabled
                    } else {
                        R.string.verify_apps_disabled
                    }
                } else {
                    R.string.general_error
                }
                Toast.makeText(activity, resId, Toast.LENGTH_SHORT).show()
            }
    }

    fun enableVerifyApps() {
        client.enableVerifyApps()
            .addOnCompleteListener { task ->
                val resId = if (task.isSuccessful) {
                    if (task.result?.isVerifyAppsEnabled == true) {
                        R.string.user_gave_consent
                    } else {
                        R.string.user_did_not_give_consent
                    }
                } else {
                    R.string.general_error
                }
                Toast.makeText(activity, resId, Toast.LENGTH_SHORT).show()
            }
    }

    fun listHarmfulApps() {
        client.listHarmfulApps()
            .addOnCompleteListener { task ->
                val resId = if (task.isSuccessful) {
                    if (task.result?.harmfulAppsList?.isNotEmpty() == true) {
                        R.string.harmful_apps
                    } else {
                        R.string.no_harmful_apps
                    }
                } else {
                    R.string.general_error
                }
                Toast.makeText(activity, resId, Toast.LENGTH_SHORT).show()
            }
    }
}