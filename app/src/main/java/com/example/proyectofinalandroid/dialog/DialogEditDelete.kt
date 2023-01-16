package com.example.proyectofinalandroid.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.proyectofinalandroid.R

class DialogEditDelete : DialogFragment() {

    private lateinit var listener: NoticeDialogListener

    interface NoticeDialogListener {
        fun onDialogEditClick(dialog: DialogEditDelete)
        fun onDialogDeleteClick(dialog: DialogEditDelete)
    }

    companion object {
        const val TAG = "Dialog"
    }

    private var spanishWord: String ?= null
    private var englishWord: String ?= null

    fun getSpanishWord() : String? {
        return spanishWord
    }

    fun getEnglishWord() : String ? {
        return englishWord
    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//
//        try {
//            listener = context as NoticeDialogListener
//        } catch (e: ClassCastException) {
//            throw ClassCastException(("$context IMPLEMENT!!! --> NoticeDialogListener"))
//        }
//    }

    fun setListener(_listener : NoticeDialogListener) {
        listener = _listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater;
            val view = inflater.inflate(R.layout.dialog_fragment, null)

            builder.setView(view)
                .setNeutralButton(R.string.accept) { _, _ ->
                    dismiss()
                }
                .setPositiveButton(R.string.edit) { _, _ ->
                    val edTxtSpanish = view.findViewById<EditText>(R.id.edTxtSpanishWord)
                    val edTxtEnglish = view.findViewById<EditText>(R.id.edTxtEnglishWord)

                    spanishWord = edTxtSpanish?.text.toString()
                    englishWord = edTxtEnglish?.text.toString()

                    listener.onDialogEditClick(this)
                }
                .setNegativeButton(R.string.delete) { _, _ ->
                    listener.onDialogDeleteClick(this)
                }
            builder.create()
        } ?: throw IllegalStateException("The activity can't be null")
    }
}
