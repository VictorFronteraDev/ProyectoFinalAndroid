package com.example.proyectofinalandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.example.proyectofinalandroid.databinding.ActivityMainBinding
import com.example.proyectofinalandroid.fragments.ColorsFragment
import com.example.proyectofinalandroid.fragments.DaysOfWeekFragment
import com.example.proyectofinalandroid.fragments.NumbersFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)

        val nightMode = preferences.getBoolean("NightMode", true)


        supportActionBar?.setHomeAsUpIndicator(R.drawable.menu_navigation_icon)
        if(nightMode)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        drawerLayout = binding.drawerLayout

        binding.navigationMenu.setNavigationItemSelectedListener {
            var fragmentTransaction = false
            lateinit var fragment: Fragment

            when(it.itemId){
                R.id.menu_colors, R.id.menu_favorites -> {
                    if (it.itemId == R.id.menu_colors) {
                        fragment = ColorsFragment()
                        fragmentTransaction = true
                    }

                }
                R.id.menu_daysOfWeek,  -> {
                    fragment = DaysOfWeekFragment()
                    fragmentTransaction = true
                }
                R.id.menu_numbers -> {
                    fragment = NumbersFragment()
                    fragmentTransaction = true
                }
            }

            if(fragmentTransaction) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frame_layout, fragment)
                    .addToBackStack(null)
                    .commit()

                // Mostramos que la opción se ha pulsado.
                it.isChecked = true

                // Mostramos el título de la sección en el Toolbar.
                title = it.title
            }

            //cerramos el drawer

            drawerLayout.closeDrawer(GravityCompat.START)

            true
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            // Si está abierto lo cerramos sino lo abrimos.
            if(drawerLayout.isDrawerOpen(GravityCompat.START))
                drawerLayout.closeDrawer(GravityCompat.START)
            else
                drawerLayout.openDrawer(GravityCompat.START)

            return true
        } else if (item.itemId == R.id.preferences) {
            startActivity(Intent(this, ActivityPreferences::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        // Si está abierto al pulsar "Atrás" lo cerramos.
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()
    }

}