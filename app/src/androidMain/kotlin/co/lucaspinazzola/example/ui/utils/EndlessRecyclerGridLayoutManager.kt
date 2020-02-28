package co.lucaspinazzola.example.ui.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE


class EndlessRecyclerGridLayoutManager : GridLayoutManager, EndlessRecyclerLayoutManager {

    private var onLastItemVisible: OnLastItemVisible? = null
    private var onFirstItemVisible: OnFirstItemVisible? = null

    private var lastDy: Int = 0
    /**
     * Number of items remaining before invoking OnLastItemVisible
     * default value is 5
     */
    private var threshold = 5
    /**
     * Number of items per load, default value is 20
     */
    private var itemCountPerLoad = 15
    /**
     * If all of the remaining items has been displayed
     */
    private var hasNoRemainingItems = false
    private var isLoadingMore = false
    var page = 1
        private set

    var smoothScrollingRecyclerView: RecyclerView? = null
    var smoothScrollingTo : Int? = null

    private val handler = Handler(Looper.getMainLooper())

    override fun setOnLastItemVisible(onLastItemVisible: OnLastItemVisible) {
        this.onLastItemVisible = onLastItemVisible
    }

    fun setOnFirstItemVisible(onFirstItemVisible: OnFirstItemVisible) {
        this.onFirstItemVisible = onFirstItemVisible
    }

    fun setThreshold(threshold: Int) {
        this.threshold = threshold
    }

    override fun reset() {
        hasNoRemainingItems = false
        isLoadingMore = false
        page = 1
    }

    override fun onAllContentsLoaded() {
        hasNoRemainingItems = true
    }


    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        when (state){
            SCROLL_STATE_IDLE -> {
                if (smoothScrollingTo!=null) {
                    if (smoothScrollingRecyclerView!!.canScrollVertically(-1)) {
                        //todo: figure out why smooth scroll doesn't always scroll all the way to the requested position
                        scrollToPosition(smoothScrollingTo!!)
                    }
                    if (onFirstItemVisible != null) {
                        onFirstItemVisible!!.onFirstItemVisible()
                    }
                    smoothScrollingRecyclerView = null
                    smoothScrollingTo = null
                }
            }
        }
    }


    override fun smoothScrollToPosition(recyclerView: RecyclerView?, state: RecyclerView.State?, position: Int) {
        super.smoothScrollToPosition(recyclerView, state, position)
        smoothScrollingRecyclerView = recyclerView
        smoothScrollingTo = position
    }

    override fun isLoadingMore(): Boolean {
        return isLoadingMore
    }

    /**
     * Call this once you've received the response from server
     */
    fun resetLoadMoreState() {
        isLoadingMore = false
    }

    fun setItemCountPerLoad(itemCountPerLoad: Int) {
        this.itemCountPerLoad = itemCountPerLoad
    }


    constructor(context: Context, spanCount: Int) : super(context,spanCount) {}

    constructor(context: Context?, orientation: Int, reverseLayout: Boolean, spanCount: Int) : super(context, spanCount, orientation, reverseLayout) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {}


    private fun isScrollingDown(currentDy: Int): Boolean {
        return currentDy > lastDy
    }

    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {

        if (shouldInvokeLastItemVisible() && !isLoadingMore && !hasNoRemainingItems && isScrollingDown(dy)) {
            invokeOnLastItemVisible()
        } else if (findFirstCompletelyVisibleItemPosition() <= 1) {
            if (onFirstItemVisible != null) {
                onFirstItemVisible!!.onFirstItemVisible()
            }
        }
        try {
            return super.scrollVerticallyBy(dy, recycler, state)
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
            return dy
        } finally {
            lastDy = dy
        }

    }

    override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        if (shouldInvokeLastItemVisible() && !isLoadingMore) {
            invokeOnLastItemVisible()
        }
        return super.scrollHorizontallyBy(dx, recycler, state)
    }

    override fun invokeOnLastItemVisible() {
        if (!isLoadingMore && onLastItemVisible != null && !hasNoRemainingItems && shouldInvokeLastItemVisible()) {
            Log.d(TAG, "Invoking onLastItemVisible(), first item visible  " + findFirstVisibleItemPosition()
                    + " itemCount - findFirstItemVisible == " + (itemCount - findFirstVisibleItemPosition()))
            isLoadingMore = true
            page++
            onLastItemVisible!!.onLastItemVisible()
        }
    }

    override fun onLoadMoreComplete() {
        isLoadingMore = false
        if (onLastItemVisible != null) {
            onLastItemVisible!!.onLoadMoreComplete()
        }
    }

    override fun onRefreshComplete() {
        reset()
    }

    private fun internalShouldInvokeLastItem(): Boolean {
        return if (itemCount >= itemCountPerLoad * 2) {
            findLastVisibleItemPosition() % itemCountPerLoad == 0
        } else {
            findLastVisibleItemPosition() + 1 >= itemCount
        }
    }

    override fun shouldInvokeLastItemVisible(): Boolean {
        return itemCount - findLastVisibleItemPosition() < threshold
    }

    companion object {
        private val TAG = EndlessRecyclerGridLayoutManager::class.java.simpleName
    }


}
