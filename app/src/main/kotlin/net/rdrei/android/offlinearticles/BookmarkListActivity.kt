package net.rdrei.android.offlinearticles

import android.app.Activity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.ViewManager
import org.jetbrains.anko.*

public class BookmarkListActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                }
            }
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
