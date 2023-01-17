package com.example.proyectofinalandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.proyectofinalandroid.databinding.ActivityUpdateDeleteBinding
import com.example.proyectofinalandroid.model.Color

class UpdateDeleteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateDeleteBinding
    private var key: String = ""
    private var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpdateDeleteBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        if(intent.hasExtra(Intent.EXTRA_TEXT)) {
            val bundle = intent.getBundleExtra(Intent.EXTRA_TEXT)
            key = bundle?.getString("Key").toString()

            if(key == "color") {
                val color: Color = bundle?.getParcelable("Color")!!

                id = color.id!!
                binding.editTxtSpanishWord.setText(color.spanishWord)
                binding.editTxtEnglishWord.setText(color.englishWord)

            }

        }

        binding.updateButton.setOnClickListener {
            val bundle = Bundle()

            if(!binding.editTxtSpanishWord.text.toString().isNullOrEmpty() || !binding.editTxtEnglishWord.text.toString().isNullOrEmpty()) {
                bundle.putParcelable("Color", Color(id, binding.editTxtSpanishWord.text.toString(), binding.editTxtEnglishWord.text.toString()))
                bundle.putBoolean("Delete", false)

                val intent = Intent().apply {
                    putExtra(Intent.EXTRA_TEXT, bundle)
                }

                setResult(RESULT_OK, intent)

                finish()

            } else {
                Toast.makeText(this, R.string.fields_empty, Toast.LENGTH_LONG).show()
            }
        }

        binding.deleteButton.setOnClickListener {
            val bundle = Bundle()

            bundle.putBoolean("Delete", true)
            bundle.putInt("id", id)

            val intent = Intent().apply {
                putExtra(Intent.EXTRA_TEXT, bundle)
            }

            setResult(RESULT_OK, intent)

            finish()
        }

    }
}