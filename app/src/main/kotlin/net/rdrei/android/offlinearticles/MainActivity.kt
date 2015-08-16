package net.rdrei.android.offlinearticles

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import com.parse.ParseAnalytics
import net.rdrei.android.offlinearticles.model.Bookmark
import org.jetbrains.anko.*
import java.net.URI

public class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        verticalLayout {
            padding = dip(16)

            textView("Hello, World") {
                textSize = 16f
            }

            val newArticle = editText() {
                inputType = InputType.TYPE_TEXT_VARIATION_URI
            }

            button("Add Article") {
                onClick {  addArticle(newArticle.getText().toString()) }
            }

            // Everyone knows this is for testing only. Right? RIGHT!?
            button("Show List") {
                onClick { startActivity<BookmarkListActivity>() }
            }
        }
    }

    fun addArticle(uri: String) {
        val bookmark = Bookmark(URI(uri))
        bookmark.saveEventually()

        ParseAnalytics.trackEventInBackground(getLocalClassName() + ":addArticle")
        toast("New article to be added: " + uri)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.getItemId()

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
