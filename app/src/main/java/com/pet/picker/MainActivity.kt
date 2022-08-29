package com.pet.picker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.pet.picker.ui.SearchFragment

class MainActivity : AppCompatActivity() {

    private val manager: FragmentManager = supportFragmentManager
    private val fragment = SearchFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        manager.beginTransaction().replace(R.id.container, fragment).commit()
    }

}