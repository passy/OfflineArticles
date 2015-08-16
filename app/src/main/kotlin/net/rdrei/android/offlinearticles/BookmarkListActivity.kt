package net.rdrei.android.offlinearticles

import android.app.Activity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewManager
import com.parse.ParseQuery
import net.rdrei.android.offlinearticles.model.Bookmark
import org.jetbrains.anko.*
import rx.parse.ParseObservable
import java.net.URI
import kotlin.properties.Delegates

public class BookmarkListActivity : Activity(), AnkoLogger {
    val adapter: BookmarkAdapter by Delegates.lazy {
        BookmarkAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super<Activity>.onCreate(savedInstanceState)

        val layoutManager = LinearLayoutManager(this)

        verticalLayout {
            refreshLayout {
                id = R.id.refresh
                enabled = false
                setColorSchemeResources(R.color.main, R.color.main_accent)

                setOnRefreshListener {
                    refresh()
                }

                recyclerView {
                    id = R.id.recycler
                    setAdapter(adapter)
                    setLayoutManager(layoutManager)
                }
            }
        }

        adapter.items.add(Bookmark(URI("http://rdrei.net/")))
        adapter.notifyDataSetChanged()

        ParseObservable.find(ParseQuery.getQuery(javaClass<Bookmark>())).subscribe { bookmark ->
            verbose("Retrieved bookmark: " + bookmark)
            adapter.items.add(bookmark)
            adapter.notifyDataSetChanged()
        }
    }

    fun refresh() {
        toast("Refresh!")
    }

    fun ViewManager.refreshLayout(init: SwipeRefreshLayout.() -> Unit = {}) =
            __dslAddView({ SwipeRefreshLayout(it) }, init, this)

    fun ViewManager.recyclerView(init: RecyclerView.() -> Unit = {}) =
            __dslAddView({ RecyclerView(it) }, init, this)
}
