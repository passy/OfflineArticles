package net.rdrei.android.offlinearticles.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import net.rdrei.android.offlinearticles.ArticleSaveActivity
import net.rdrei.android.offlinearticles.R
import net.rdrei.android.offlinearticles.model.Bookmark
import org.jetbrains.anko.*
import java.net.URI
import java.util.*

class BookmarkAdapterImpl : BookmarkAdapter, RecyclerView.Adapter<BookmarkAdapterImpl.ViewHolder>(), AnkoLogger {
    override val items = ArrayList<Bookmark>()

    override fun getItemCount(): Int {
        return items.size()
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        if (holder != null) {
            val item = items.get(position)
            holder.text.text = item.getURI(URI("https://example.com/")).toString()
        } else {
            verbose("holder empty. Problem? (trollface)")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder? {
        val ctx = parent!!.context
        val v = buildLayout(ctx)

        val text = v.find<TextView>(R.id.text)
        // Leak!
        v.setOnClickListener { ctx.startActivity(ctx.intentFor<ArticleSaveActivity>()) }

        return ViewHolder(v, text)
    }

    fun buildLayout(ctx: Context): ViewGroup {
        return with(ctx) {
            verticalLayout {
                textView("Hello World") {
                    id = R.id.text
                }
            }
        }
    }

    inner class ViewHolder(val view: View,
                           val text: TextView) : RecyclerView.ViewHolder(view) {}
}
