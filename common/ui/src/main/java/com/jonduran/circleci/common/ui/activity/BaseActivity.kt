package com.jonduran.circleci.common.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.fragment.app.FragmentFactory

abstract class BaseActivity : AppCompatActivity() {
    abstract val fragmentFactory: FragmentFactory
    protected var showHomeAsUp: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = fragmentFactory
        super.onCreate(savedInstanceState)
        supportActionBar?.let { actionBar ->
            actionBar.setDisplayHomeAsUpEnabled(showHomeAsUp)
            actionBar.setDisplayShowHomeEnabled(showHomeAsUp)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            if (showHomeAsUp) {
                NavUtils.navigateUpFromSameTask(this)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}