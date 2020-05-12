package co.lucaspinazzola.example.ui.composables

import android.graphics.drawable.Drawable
import androidx.compose.Composable
import androidx.compose.onCommit
import androidx.compose.state
import androidx.core.graphics.drawable.toBitmap
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Modifier
import androidx.ui.core.WithConstraints
import androidx.ui.foundation.Canvas
import androidx.ui.foundation.Image
import androidx.ui.graphics.ImageAsset
import androidx.ui.graphics.asImageAsset
import androidx.ui.layout.fillMaxSize
import androidx.ui.unit.IntPx
import coil.Coil
import coil.request.LoadRequest
import coil.request.LoadRequestBuilder
import coil.size.OriginalSize
import coil.size.Scale
import coil.size.Size
import coil.target.Target


@Composable
fun CoilImage(
    model: Any,
    modifier : Modifier = Modifier,
    customize: LoadRequestBuilder.() -> Unit = {}
) {
    WithConstraints { constraints, _ ->
        val image = state<ImageAsset?> { null }
        val drawable = state<Drawable?> { null }
        val context = ContextAmbient.current

        onCommit(model) {
            val target = object : Target {
                override fun onStart(placeholder: Drawable?) {
                    image.value = null
                    drawable.value = placeholder
                }

                override fun onSuccess(result: Drawable) {
                    image.value = result.toBitmap().asImageAsset()
                }

                override fun onError(error: Drawable?) {

                }
            }

            val width =
                if (constraints.maxWidth > IntPx.Zero && constraints.maxWidth < IntPx.Infinity) {
                    constraints.maxWidth.value
                } else {
                    1
                }

            val height =
                if (constraints.maxHeight > IntPx.Zero && constraints.maxHeight < IntPx.Infinity) {
                    constraints.maxHeight.value
                } else {
                    1
                }

            val request = LoadRequest.Builder(context)
                .data(model)
                .size(width, height)
                .scale(Scale.FILL)
                .apply(customize)
                .target(target)

            val requestDisposable = Coil.imageLoader(context).execute(request.build())

            onDispose {
                image.value = null
                drawable.value = null
                requestDisposable.dispose()
            }
        }

        val theImage = image.value
        val theDrawable = drawable.value
        if (theImage != null) {
            Image(modifier = modifier, asset = theImage)
        } else if (theDrawable != null) {
            Canvas(modifier = Modifier.fillMaxSize()) { theDrawable.draw(nativeCanvas) }
        }
    }
}