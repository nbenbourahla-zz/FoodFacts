package com.benbourahla.foodfacts

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.BottomNavigationView
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.benbourahla.foodfacts.producthistory.ProductHistoryActivity
import com.benbourahla.foodfacts.searchproduct.SearchProductActivity

open class BottomBarActivity: AppCompatActivity() {

    val toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }
    val bottomBar by lazy { findViewById<BottomNavigationView>(R.id.navigation_view) }
    val container by lazy { findViewById<AppBarLayout>(R.id.app_bar_layout) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)
    }

    override fun onStart() {
        super.onStart()
        bottomBar.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.navigation_scan -> goToScanScreen()
                R.id.navigation_history -> goToHistoryScreen()
                else -> goToScanScreen()
            }
        }
    }

    private fun goToHistoryScreen(): Boolean {
        val intent = Intent(this, ProductHistoryActivity::class.java)
        intent.flags = FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)
        return true
    }

    private fun goToScanScreen(): Boolean {
        val intent = Intent(this, SearchProductActivity::class.java)
        intent.flags = FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)
        return true
    }
}