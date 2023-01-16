package com.example.proyectofinalandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.proyectofinalandroid.fragments.PreferencesFragment

class ActivityPreferences : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, PreferencesFragment())
            .commit()
    }
}