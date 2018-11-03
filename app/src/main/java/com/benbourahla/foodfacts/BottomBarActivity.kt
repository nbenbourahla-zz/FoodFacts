package com.benbourahla.foodfacts

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

open class BottomBarActivity: AppCompatActivity() {

    val toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }
    val bottomBar by lazy { findViewById<BottomNavigationView>(R.id.navigation_view) }
    val container by lazy { findViewById<AppBarLayout>(R.id.app_bar_layout) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)
    }
}