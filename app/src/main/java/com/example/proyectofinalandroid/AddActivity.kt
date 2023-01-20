package com.example.proyectofinalandroid

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.proyectofinalandroid.databinding.ActivityAddBinding
import com.example.proyectofinalandroid.model.Color
import com.example.proyectofinalandroid.model.DayOfWeek
import com.example.proyectofinalandroid.model.Numbers

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding
    private var key: String = ""
    private var id: Int = 0
    private val bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        if(intent.hasExtra(Intent.EXTRA_TEXT)) {
            val bundle = intent.getBundleExtra(Intent.EXTRA_TEXT)
            key = bundle?.getString("Key").toString()

        }

        binding.btnAdd.setOnClickListener {
            val spanishWord = binding.editTxtSpanishWordAdd.text.toString()
            val englishWord = binding.editTxtEnglishWordAdd.text.toString()

            if(!spanishWord.isNullOrEmpty() || !englishWord.isNullOrEmpty()) {
                when (key) {
                    "Color" -> {
                        bundle.putParcelable("Color", Color(null, spanishWord, englishWord))

                        val intent = Intent().apply {
                            putExtra(Intent.EXTRA_TEXT, bundle)
                        }

                        setResult(RESULT_OK, intent)

                        finish()
                    }
                    "DayOfWeek" -> {
                        bundle.putParcelable("DayOfWeek", DayOfWeek(null, spanishWord, englishWord))

                        val intent = Intent().apply {
                            putExtra(Intent.EXTRA_TEXT, bundle)
                        }

                        setResult(RESULT_OK, intent)

                        finish()
                    }
                    "Numbers" -> {
                        bundle.putParcelable("Numbers", Numbers(null, spanishWord, englishWord))

                        val intent = Intent().apply {
                            putExtra(Intent.EXTRA_TEXT, bundle)
                        }

                        setResult(RESULT_OK, intent)

                        finish()
                    }
                }

            } else {
                Toast.makeText(this, R.string.fields_empty, Toast.LENGTH_LONG).show()
            }
        }

        binding.btnCancel.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.cancel)
            builder.setMessage(R.string.confirm_cancel)
            builder.setPositiveButton(R.string.yes) { _, _ ->
                setResult(RESULT_CANCELED)

                finish()
            }
            builder.setNegativeButton(R.string.no, null)
            val dialog = builder.create()
            dialog.show()

        }
    }
}