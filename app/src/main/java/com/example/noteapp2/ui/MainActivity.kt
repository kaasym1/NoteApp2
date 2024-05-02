package com.example.noteapp2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.example.noteapp2.data.Pref
import com.example.noteapp2.R
import com.example.noteapp2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val pref by lazy {
        Pref(this)
    }
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController = navHostFragment.navController

        if (!pref.isShow()) {
            navController.navigate(R.id.on_boarding)
        }
    }
}