package com.example.steelconversion

import android.content.Intent
import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.mopub.mobileads.MoPubErrorCode
import com.mopub.mobileads.MoPubView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var moPubBanner: MoPubView
    private lateinit var moPubViewbannerAdListener: MoPubView.BannerAdListener

    private lateinit var dl: DrawerLayout
    private lateinit var t: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* MoPub Banner test Ad */
        MyMoPub().init(this, getString(R.string.mBannerId))

        Handler().postDelayed({
            initBannerAds()
        }, 200)


        /* Going to SecondActivity */
        val btn = findViewById<Button>(R.id.button)
        btn.setOnClickListener {
            val mIntent = Intent(this@MainActivity, SecondActivity::class.java)
            startActivity(mIntent)
        }


        val diameter = findViewById<EditText>(R.id.diameter_entry)
        val length = findViewById<EditText>(R.id.length_entry)
        val resultTextView = findViewById<TextView>(R.id.results)


        /* to change unit weight dynamically */
        diameter_entry.addTextChangedListener( object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val D: Double = if (TextUtils.isEmpty(p0.toString())) {
                    0.0
                } else {
                    p0.toString().toDouble()
                }

                val unitWeight = (D * D * 1 / 162)
                unit_weight.text = "${getString(R.string.unit_weight_kgs)} " + "%.3f".format(unitWeight)

            }

        })

        /* To display results */
        calculate.setOnClickListener {

            val D: Double = if (TextUtils.isEmpty(diameter.text.toString())) {
                0.0
            } else {
                diameter.text.toString().toDouble()
            }
            val L: Double = if (TextUtils.isEmpty(length.text.toString())) {
                0.0
            } else {
                length.text.toString().toDouble()
            }

            val res =  (D * D * L / 162)
            resultTextView.text = "${getString(R.string.total_weight_kgs)} " + "%.2f".format(res)

            if (D == 0.0) {
                unit_weight.text = "${getString(R.string.unit_weight_kgs)} " + "0.000"
            }
        }


        /* To reset EditTexts and resultTextView*/
        reset.setOnClickListener {
            diameter.setText("")
            diameter.requestFocus()
            length.setText("")
            resultTextView.text = getString(R.string.total_weight_kgs)
            unit_weight.text = getString(R.string.unit_weight_kgs)
        }


        /* MainActivity navigation view */
        dl = findViewById(R.id.activity_main)
        t = ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close)

        t.isDrawerIndicatorEnabled = true
        dl.addDrawerListener(t)
        t.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val nv:NavigationView = findViewById(R.id.nv)
        nv.setNavigationItemSelectedListener { menuItem ->

            when(menuItem.itemId) {
                R.id.settings -> {
                    val mIntent = Intent(this@MainActivity, SettingsActivity::class.java)
                    startActivity(mIntent)
                }
                R.id.exit -> {
                    finishAffinity()
                }
                R.id.help -> {
                    val mIntent = Intent(this@MainActivity, HelpActivity::class.java)
                    startActivity(mIntent)
                }
                R.id.change_theme -> {
                    val mIntent = Intent(this@MainActivity, ThemesActivity::class.java)
                    startActivity(mIntent)
                }
                R.id.share -> {
                    Toast.makeText(this, "Share", Toast.LENGTH_LONG).show()
                }
                R.id.about -> {
                    val mIntent = Intent(this@MainActivity, AboutActivity::class.java)
                    startActivity(mIntent)
                }
            }

            menuItem.isChecked = true
            dl.closeDrawers()
            true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (t.onOptionsItemSelected(item))
            return true

        return super.onOptionsItemSelected(item)
    }

    private fun initBannerAds() {
        moPubBanner = findViewById(R.id.banner_view)
        moPubBanner.adUnitId = getString(R.string.mBannerId)
        moPubBanner.loadAd()

        Log.d("MoPub", "Banner ad is Loading")

        moPubViewbannerAdListener = object : MoPubView.BannerAdListener {
            override fun onBannerExpanded(banner: MoPubView?) {
                Log.d("MoPub", "Banner ad has expanded")
            }

            override fun onBannerLoaded(banner: MoPubView?) {
                Log.d("MoPub", "Banner ad has loaded")
            }

            override fun onBannerCollapsed(banner: MoPubView?) {
                Log.d("MoPub", "Banner ad has collapsed")
            }

            override fun onBannerClicked(banner: MoPubView?) {
                Log.d("MoPub", "Banner ad was clicked")
            }

            override fun onBannerFailed(banner: MoPubView?, errorCode: MoPubErrorCode?) {
                Log.d("MoPub", "Banner ad has failed to load with error: $errorCode")
            }
        }

        moPubBanner.bannerAdListener = moPubViewbannerAdListener
    }

    override fun onDestroy() {
        moPubBanner.destroy()

        super.onDestroy()
    }




}
