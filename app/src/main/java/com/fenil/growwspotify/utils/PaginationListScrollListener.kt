package com.fenil.growwspotify.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val RUNNING_LOW_ON_DATA_THRESHOLD = 3

open class PaginationListScrollListener(
    private val wrapper: RecyclerView.OnScrollListener? = null,
    private val dataThresholdCount: Int = RUNNING_LOW_ON_DATA_THRESHOLD,
    private val mayBeFetchNextData: (currentItemCount: Int) -> Unit
) : RecyclerView.OnScrollListener() {

    private var isScrollUp: Boolean = false
    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {

            val totalItemCount = recyclerView.layoutManager?.itemCount ?: 0
            val lastVisibleItem = recyclerView.layoutManager.let {
                if (it is LinearLayoutManager) {
                    it.findLastVisibleItemPosition()
                } else {
                    RecyclerView.NO_POSITION
                }
            }
            if (!isScrollUp && totalItemCount <= lastVisibleItem + dataThresholdCount) {
                mayBeFetchNextData(totalItemCount)
            }
        }
        wrapper?.onScrollStateChanged(recyclerView, newState)
        super.onScrollStateChanged(recyclerView, newState)
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        isScrollUp = dy < 0
        wrapper?.onScrolled(recyclerView, dx, dy)
        super.onScrolled(recyclerView, dx, dy)
    }
}
