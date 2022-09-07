package com.pet.picker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pet.picker.ui.SearchFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SearchFragment.newInstance(), "search fragment").commit()
        }
    }
}