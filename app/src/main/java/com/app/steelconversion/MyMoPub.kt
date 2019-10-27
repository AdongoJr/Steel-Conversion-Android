package com.app.steelconversion

import android.content.Context
import android.util.Log
import androidx.annotation.NonNull
import com.mopub.common.MoPub
import com.mopub.common.SdkConfiguration
import com.mopub.common.SdkInitializationListener
import com.mopub.common.logging.MoPubLog
import com.mopub.common.privacy.ConsentDialogListener
import com.mopub.common.privacy.PersonalInfoManager
import com.mopub.mobileads.MoPubErrorCode

class MyMoPub {
    private lateinit var mPersonalInfoManager: PersonalInfoManager
    private lateinit var mContext: Context
    fun init(context: Context, adUnit: String) {
        mContext = context
        val sdkConfiguration = SdkConfiguration.Builder(adUnit)
            .withLogLevel(MoPubLog.LogLevel.DEBUG)
            .withLegitimateInterestAllowed(false)
            .build()
        MoPub.initializeSdk(mContext, sdkConfiguration, initSdkListener())
    }

    private fun initSdkListener(): SdkInitializationListener? {
        return SdkInitializationListener {
            Log.d("MoPub", "MoPub SDK Initialized")
            GDPRConsent()
        }
    }

    private fun GDPRConsent() {
        mPersonalInfoManager = MoPub.getPersonalInformationManager()!!
        Log.d("MoPub", "Is this Country Covered by GDPR? ${mPersonalInfoManager.gdprApplies()}")

        if (mPersonalInfoManager.shouldShowConsentDialog()) {
            mPersonalInfoManager.loadConsentDialog(initDialogLoadListener())
        }
    }

    private fun initDialogLoadListener(): ConsentDialogListener? {
        return object : ConsentDialogListener {
            override fun onConsentDialogLoaded() {
                mPersonalInfoManager.showConsentDialog()
            }

            override fun onConsentDialogLoadFailed(@NonNull moPubErrorCode: MoPubErrorCode) {
                Log.d("MoPub", "Consent Dialog failed to Load")
            }
        }
    }

}