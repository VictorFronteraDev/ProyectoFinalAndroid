package com.example.proyectofinalandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ActivityPreferences : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, PreferencesFragment())
            .commit()
    }
}