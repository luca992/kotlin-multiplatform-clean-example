package co.lucaspinazzola.example.ui.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import co.lucaspinazzola.example.databinding.ItemGifBinding
import co.lucaspinazzola.example.domain.model.Img
import co.lucaspinazzola.example.ui.base.ListClickAdapter
import coil.Coil
import coil.request.LoadRequest

class ImgAdapter: ListClickAdapter<Img, ImgAdapter.ViewHolder>(
    ItemDiffUtil()
) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemGifBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val i = getItem(position)
        val request = LoadRequest.Builder(holder.itemView.context)
            .data(i.url)
            .target(holder.binding.gifIv)
            .build()
        Coil.imageLoader(holder.binding.root.context).execute(request)
    }



    private class ItemDiffUtil : DiffUtil.ItemCallback<Img>() {
        override fun areItemsTheSame(oldItem: Img, newItem: Img): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Img, newItem: Img): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }



    inner class ViewHolder(val binding: ItemGifBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                val item = getItem(adapterPosition)!!
                onItemClick.offer(item)
            }
            binding.root.setOnLongClickListener{
                longClickSubject.offer(getItem(adapterPosition)!!)
                true
            }
        }
    }
}