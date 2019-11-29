package com.app.steelconversion

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity: AppCompatActivity() {

    lateinit var mAdView: AdView
    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        // Admob Ads
        MobileAds.initialize(this) {}

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        // Admob interstitial Ads
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = getString(R.string.testIntestitialAd)
        mInterstitialAd.loadAd(AdRequest.Builder().build())

        // Scrolling movement in textView
        aboutTextView.movementMethod = ScrollingMovementMethod()

    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(mInterstitialAd.isLoaded) {
            mInterstitialAd.show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }






}