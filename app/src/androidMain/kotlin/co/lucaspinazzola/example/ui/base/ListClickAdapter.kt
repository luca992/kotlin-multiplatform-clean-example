package co.lucaspinazzola.example.ui.base

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.channels.BroadcastChannel


abstract class ListClickAdapter<T, VH : RecyclerView.ViewHolder>(diffCallback: DiffUtil.ItemCallback<T>)
    : ListAdapter<T, VH>(diffCallback) {

    val onItemClick = BroadcastChannel<T>(1)
    val longClickSubject = BroadcastChannel<T>(1)

}
