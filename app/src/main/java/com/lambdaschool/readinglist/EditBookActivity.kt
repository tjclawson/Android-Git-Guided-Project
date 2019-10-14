package com.lambdaschool.readinglist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_edit_book.*

class EditBookActivity : AppCompatActivity() {
    private var context: Context? = null
    private var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_book)
        context = this

    }

    override fun onResume() {
        super.onResume()

        save_button.setOnClickListener { returnData() }

        cancel_button.setOnClickListener { cancel() }

        val intent = intent
        id = intent.getStringExtra(Constants.NEW_BOOK_TAG)
        val bookCsv = intent.getStringExtra(Constants.EDIT_BOOK_TAG)
        if (bookCsv != null) {
            val book = Book(bookCsv)
            book_name_text.setText(book.title)
            book_reason_text.setText(book.reasonToRead)
            read_switch.isChecked = book.isHasBeenRead
            id = book.id
        }
    }

    private fun returnData() {
        val bookName = book_name_text.text.toString()
        val bookReason = book_reason_text.text.toString()
        val switchStatus = read_switch.isChecked
        val book = Book(id!!, bookName, bookReason, read_switch.isChecked)
        val bookCsv = book.toCsvString()
        val intent = Intent()
        intent.putExtra(Constants.EDIT_BOOK_TAG, bookCsv)
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun cancel() {
        val intent = Intent()
        setResult(RESULT_CANCELED, intent)
        finish()
    }

    override fun onBackPressed() {
        returnData()
    }
}
