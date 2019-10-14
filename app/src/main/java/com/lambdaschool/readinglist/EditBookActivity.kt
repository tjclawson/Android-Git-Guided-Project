package com.lambdaschool.readinglist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_edit_book.*

/*
// todo: We are going to add a bug fix to this project, and update the Material palette, and
   todo: we will use git-flow to achieve both

   todo 1: Fork this repo and clone it locally

   todo 2: Switch to the `development` branch

   todo 3: The first bug we will fix is checking if the book title has been filled out before
   todo 3: we save the book data. If it has not, display a Toast and do not save.
   todo 3: Let's create a new branch for our bug fix called `bug-fix-book-input`
 */

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

        save_button.setOnClickListener { saveBook() }
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

    private fun saveBook() {
        // todo 4: Check for a valid bookName and display a Toast if one does not exist
        // todo 5: Commit our changes with a good commit message
        // todo 6: Merge with development and open a pull-request
        val bookName = book_name_text.text.toString()
        if(bookName.isEmpty()) {
            Toast.makeText(this, "Please enter a book title", Toast.LENGTH_LONG).show()
            return
        }
        val bookReason = book_reason_text.text.toString()
        val hasBeenRead = read_switch.isChecked
        val book = Book(id!!, bookName, bookReason, hasBeenRead)
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
        saveBook()
    }
}
