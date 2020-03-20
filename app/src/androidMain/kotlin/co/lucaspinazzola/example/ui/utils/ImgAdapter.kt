package co.lucaspinazzola.example.ui.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import co.lucaspinazzola.example.R
import co.lucaspinazzola.example.domain.model.Img
import co.lucaspinazzola.example.ui.base.ListClickAdapter
import coil.api.load
import kotlinx.android.synthetic.main.item_gif.view.*

class ImgAdapter: ListClickAdapter<Img, ImgAdapter.ViewHolder>(
    ItemDiffUtil()
) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_gif, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val i = getItem(position)
        holder.itemView.apply {

            gifIv.load(i.url)
        }
    }



    private class ItemDiffUtil : DiffUtil.ItemCallback<Img>() {
        override fun areItemsTheSame(oldItem: Img, newItem: Img): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Img, newItem: Img): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }



    inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener {
                val item = getItem(adapterPosition)!!
                onItemClick.offer(item)
            }
            itemView.setOnLongClickListener{
                longClickSubject.offer(getItem(adapterPosition)!!)
                true
            }
        }
    }
}