package net.rdrei.android.offlinearticles

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.ViewManager
import org.jetbrains.anko.custom.ankoView

public inline fun ViewManager.refreshLayout(init: SwipeRefreshLayout.() -> Unit): SwipeRefreshLayout =
    ankoView({ SwipeRefreshLayout(it) }, init)

public inline fun ViewManager.recyclerView(init: RecyclerView.() -> Unit): RecyclerView =
    ankoView({ RecyclerView(it) }, init)
