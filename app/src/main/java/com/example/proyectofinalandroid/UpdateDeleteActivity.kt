package com.example.proyectofinalandroid

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.proyectofinalandroid.databinding.ActivityUpdateDeleteBinding
import com.example.proyectofinalandroid.model.Color
import com.example.proyectofinalandroid.model.DayOfWeek
import com.example.proyectofinalandroid.model.Numbers

class UpdateDeleteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateDeleteBinding
    private var key: String = ""
    private var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpdateDeleteBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            val bundle = intent.getBundleExtra(Intent.EXTRA_TEXT)
            key = bundle?.getString("Key").toString()

            when (key) {
                "Color" -> {
                    val color: Color = bundle?.getParcelable("Color")!!

                    id = color.id!!
                    binding.editTxtSpanishWord.setText(color.spanishWord)
                    binding.editTxtEnglishWord.setText(color.englishWord)
                }
                "DayOfWeek" -> {
                    val dayOfWeek: DayOfWeek? = bundle?.getParcelable("DayOfWeek")

                    id = dayOfWeek?.id!!
                    binding.editTxtSpanishWord.setText(dayOfWeek.spanishWord)
                    binding.editTxtEnglishWord.setText(dayOfWeek.spanishWord)
                }
                "Numbers" -> {
                    val number: Numbers? = bundle?.getParcelable("Numbers")

                    id = number!!.id!!
                    binding.editTxtSpanishWord.setText(number.spanishWord)
                    binding.editTxtEnglishWord.setText(number.englishWord)

                }
            }
        }

        when (key) {
            "Color" -> {
                binding.updateButton.setOnClickListener {
                    val bundle = Bundle()

                    if (!binding.editTxtSpanishWord.text.toString().isNullOrEmpty() || !binding.editTxtEnglishWord.text.toString().isNullOrEmpty()) {
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

            }
            "DayOfWeek" -> {
                binding.updateButton.setOnClickListener {
                    val bundle = Bundle()

                    if (!binding.editTxtSpanishWord.text.toString().isNullOrEmpty() || !binding.editTxtEnglishWord.text.toString().isNullOrEmpty()) {
                        bundle.putParcelable("DayOfWeek", DayOfWeek(id, binding.editTxtSpanishWord.text.toString(), binding.editTxtEnglishWord.text.toString()))
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

            }
            "Number" -> {
                binding.updateButton.setOnClickListener {
                    val bundle = Bundle()

                    if (!binding.editTxtSpanishWord.text.toString().isNullOrEmpty() || !binding.editTxtEnglishWord.text.toString().isNullOrEmpty()) {
                        bundle.putParcelable("Numbers", Numbers(id, binding.editTxtSpanishWord.text.toString(), binding.editTxtEnglishWord.text.toString()))
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
            }
        }

        binding.deleteButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.delete)
            builder.setMessage(R.string.confirm_delete)
            builder.setPositiveButton(R.string.accept) { _,_ ->
                val bundle = Bundle()

                bundle.putBoolean("Delete", true)
                bundle.putInt("id", id)

                val intent = Intent().apply {
                    putExtra(Intent.EXTRA_TEXT, bundle)
                }

                setResult(RESULT_OK, intent)

                finish()
            }
            builder.setNegativeButton(R.string.cancel, null)
            val dialog = builder.create()
            dialog.show()
        }
    }
}