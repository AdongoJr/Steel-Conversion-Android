package com.app.steelconversion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var mAdView: AdView

    private lateinit var dl: DrawerLayout
    private lateinit var t: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Admob Ads
        MobileAds.initialize(this) {}
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

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
                R.id.exit -> {
                    finishAffinity()
                }

                R.id.about -> {
                    menuItem.isChecked = true
                    val intent = Intent(this@MainActivity, AboutActivity::class.java)
                    startActivity(intent)
                }

            }

            dl.closeDrawers()
            true
        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (t.onOptionsItemSelected(item))
            return true

        when(item.itemId) {
            R.id.menuExit -> {
                finishAffinity()
                return  true
            }

            R.id.menuAbout -> {
                val intent = Intent(this@MainActivity, AboutActivity::class.java)
                startActivity(intent)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }


}
