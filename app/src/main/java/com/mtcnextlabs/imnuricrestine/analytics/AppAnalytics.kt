package com.mtcnextlabs.imnuricrestine.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase



object AppAnalytics {
    private val firebaseAnalytics: FirebaseAnalytics
        get() = Firebase.analytics

    /**
     * Logs a screen view event.
     * @param screenName The name of the screen (e.g., "Index", "HymnDetails").
     */
    fun logScreenView(screenName: String) {
        val params = Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        }
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, params)
    }
}