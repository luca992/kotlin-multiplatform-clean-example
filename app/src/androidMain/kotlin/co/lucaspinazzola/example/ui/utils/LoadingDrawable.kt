package co.lucaspinazzola.example.ui.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import co.lucaspinazzola.example.R
import kotlin.math.min

class LoadingDrawable(context: Context) : ColorDrawable(ContextCompat.getColor(context, android.R.color.white)), Drawable.Callback {

    private val animatedDrawable = CircularProgressDrawable(context)

    init {
        animatedDrawable.setStyle(CircularProgressDrawable.LARGE)
        animatedDrawable.setColorSchemeColors(ContextCompat.getColor(context, R.color.colorPrimary))
        animatedDrawable.callback = this
        animatedDrawable.start()
    }

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)

        val width = min(bounds.width(), animatedDrawable.intrinsicWidth)
        val height = min(bounds.height(), animatedDrawable.intrinsicHeight)
        val left = (bounds.width() - width) / 2
        val top = (bounds.height() - height) / 2
        animatedDrawable.setBounds(left, top, left + width, top + height)
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        animatedDrawable.draw(canvas)
    }

    override fun unscheduleDrawable(who: Drawable, what: Runnable) {
        unscheduleSelf(what)
    }

    override fun invalidateDrawable(who: Drawable) {
        invalidateSelf()
    }

    override fun scheduleDrawable(who: Drawable, what: Runnable, `when`: Long) {
        scheduleSelf(what, `when`)
    }
}