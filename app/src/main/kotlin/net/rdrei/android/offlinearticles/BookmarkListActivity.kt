package net.rdrei.android.offlinearticles

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.trello.rxlifecycle.components.RxActivity
import net.rdrei.android.offlinearticles.adapter.BookmarkAdapterImpl
import net.rdrei.android.offlinearticles.model.Bookmark
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.verbose
import org.jetbrains.anko.verticalLayout
import java.net.URI

public class BookmarkListActivity : RxActivity(), AnkoLogger {
    val adapter: BookmarkAdapterImpl by lazy(LazyThreadSafetyMode.NONE) {
        BookmarkAdapterImpl()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layoutManager = LinearLayoutManager(this)

        verticalLayout {
            refreshLayout {
                id = R.id.refresh
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
        adapter.items.add(Bookmark(URI("https://rdrei.net")))
        Bookmark.Queries.getAllObservable()
                .compose(bindToLifecycle<Bookmark>())
                .subscribe { bookmark ->
                    verbose("Retrieved bookmark: " + bookmark)
                    adapter.items.add(bookmark)
                    adapter.notifyDataSetChanged()
                }
    }
}
