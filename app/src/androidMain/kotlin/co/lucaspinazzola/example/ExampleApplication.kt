package co.lucaspinazzola.example

import android.app.Application
import android.content.Context
import android.os.Build
import co.lucaspinazzola.example.data.utils.initData
import co.lucaspinazzola.example.di.component.AppComponent
import co.lucaspinazzola.example.ui.utils.LoadingDrawable
import coil.Coil
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.util.CoilUtils
import okhttp3.OkHttpClient


class ExampleApplication: Application(), ImageLoaderFactory{

    val mainComponent by lazy {
        AppComponent.init(this)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        initData(this)
    }

    override fun newImageLoader(): ImageLoader =
        ImageLoader.Builder(applicationContext)
            .crossfade(true)
            .okHttpClient {
                OkHttpClient.Builder()
                    .cache(CoilUtils.createDefaultCache(this))
                    .build()
            }
            .componentRegistry {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    add(ImageDecoderDecoder())
                } else {
                    add(GifDecoder())
                }
            }
            .build()


}

