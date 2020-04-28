package co.lucaspinazzola.example.ui.utils
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

@set:BindingAdapter("visibility")
var View.appVisibility : Visibility?
    get() = when (visibility) {
        View.VISIBLE -> Visibility.VISIBLE
        View.INVISIBLE -> Visibility.INVISIBLE
        View.GONE -> Visibility.GONE
        else -> Visibility.GONE
    }
    set(value) {
        visibility = when (value) {
            Visibility.VISIBLE -> View.VISIBLE
            Visibility.INVISIBLE -> View.INVISIBLE
            Visibility.GONE -> View.GONE
            else -> View.GONE
        }
    }


@BindingAdapter("data")
fun <T> setRecyclerViewProperties(recyclerView: RecyclerView, data: List<T>) {
    if (recyclerView.adapter is ListAdapter<*,*>) {
        (recyclerView.adapter as ListAdapter<T,*>).submitList(data)
    }
}

