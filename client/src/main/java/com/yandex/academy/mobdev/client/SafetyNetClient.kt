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
                if (e is ApiException) {
                    val string = "Error: ${CommonStatusCodes.getStatusCodeString(e.statusCode)}"
                    Toast.makeText(activity, string, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}