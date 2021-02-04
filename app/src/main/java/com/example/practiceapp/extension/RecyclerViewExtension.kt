package com.example.practiceapp.extension

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.loadMore(callback: () -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val lastPosition = layoutManager.findLastVisibleItemPosition()
            if (adapter != null && lastPosition == adapter!!.itemCount - 1) {
                callback.invoke()
            }
        }
    })
}
