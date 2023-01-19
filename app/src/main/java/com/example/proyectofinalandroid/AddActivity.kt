package com.example.proyectofinalandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.proyectofinalandroid.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)
    }
}