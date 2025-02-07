package com.botaoap.appnews.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.botaoap.appnews.R
import com.botaoap.appnews.ui.feature.newslist.view.NewsListFragment

interface MainActivityListener {
    fun startFragment(fragment: Fragment)
    fun startFragment(fragment: Fragment, nameFragment: String)
}

class MainActivity : AppCompatActivity(), MainActivityListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.fl_main_container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        startFragment(NewsListFragment.newInstance())
    }

    override fun startFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_main_container, fragment)
        }.commit()
    }

    override fun startFragment(fragment: Fragment, nameFragment: String) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_main_container, fragment)
            addToBackStack(nameFragment)
        }.commit()
    }
}