package co.lucaspinazzola.example.ui.utils
interface EndlessRecyclerLayoutManager {
    fun reset()
    fun setOnLastItemVisible(onLastItemVisible: OnLastItemVisible)
    fun onAllContentsLoaded()
    fun onLoadMoreComplete()
    fun onRefreshComplete()
    fun invokeOnLastItemVisible()
    fun shouldInvokeLastItemVisible() : Boolean
    fun isLoadingMore() : Boolean
}