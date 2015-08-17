package net.rdrei.android.offlinearticles

import android.app.Activity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewManager
import com.parse.ParseQuery
import com.trello.rxlifecycle.components.RxActivity
import net.rdrei.android.offlinearticles.model.Bookmark
import org.jetbrains.anko.*
import rx.parse.ParseObservable
import java.net.URI
import kotlin.properties.Delegates

public class BookmarkListActivity : RxActivity(), AnkoLogger {
    val adapter: BookmarkAdapter by Delegates.lazy {
        BookmarkAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super<RxActivity>.onCreate(savedInstanceState)

        val layoutManager = LinearLayoutManager(this)

        verticalLayout {
            refreshLayout {
                id = R.id.refresh
                enabled = true
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

        refresh()
    }

    private fun refresh() {
        Bookmark.Queries.getAllObservable()
                .compose(bindToLifecycle<Bookmark>())
                .subscribe { bookmark ->
                    verbose("Retrieved bookmark: " + bookmark)
                    adapter.items.add(bookmark)
                    adapter.notifyDataSetChanged()
                }
    }
}
