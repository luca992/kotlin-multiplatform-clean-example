package co.lucaspinazzola.example.ui.giphy

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import co.lucaspinazzola.example.R
import co.lucaspinazzola.example.domain.model.Gif
import co.lucaspinazzola.example.ui.base.ListClickAdapter
import kotlinx.android.synthetic.main.item_gif.view.*

class GiphyAdapter: ListClickAdapter<Gif, GiphyAdapter.ViewHolder>(ItemDiffUtil()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_gif, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val i = getItem(position)
        holder.itemView.apply {
            nameTv.text = i.url
        }
    }

    fun colorString(color: Int, text: String, vararg wordsToColor: String): SpannableString {
        val coloredString = SpannableString(text)

        for (word in wordsToColor) {
            val startColorIndex = text.toLowerCase().indexOf(word.toLowerCase())
            if (startColorIndex==-1) continue
            val endColorIndex = startColorIndex + word.length
            coloredString.setSpan(ForegroundColorSpan(color), startColorIndex, endColorIndex,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        return coloredString
    }

    private class ItemDiffUtil : DiffUtil.ItemCallback<Gif>() {
        override fun areItemsTheSame(oldItem: Gif, newItem: Gif): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Gif, newItem: Gif): Boolean {
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