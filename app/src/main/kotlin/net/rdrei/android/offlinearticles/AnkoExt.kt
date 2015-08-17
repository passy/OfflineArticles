package net.rdrei.android.offlinearticles

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.ViewManager
import org.jetbrains.anko.__dslAddView

fun ViewManager.refreshLayout(init: SwipeRefreshLayout.() -> Unit = {}) =
        __dslAddView({ SwipeRefreshLayout(it) }, init, this)

fun ViewManager.recyclerView(init: RecyclerView.() -> Unit = {}) =
        __dslAddView({ RecyclerView(it) }, init, this)
