package com.app.steelconversion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menuHelp -> {
                val mIntent = Intent(this@SecondActivity, HelpActivity::class.java)
                startActivity(mIntent)
                return  true
            }
            R.id.menuExit -> {
                finishAffinity()
                return  true
            }
        }

        return super.onOptionsItemSelected(item)
    }

}
