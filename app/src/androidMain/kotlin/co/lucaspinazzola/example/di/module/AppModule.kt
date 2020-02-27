package co.lucaspinazzola.example.di.module

import android.app.Application
import dagger.Module
import dagger.Provides

@Module
class AppModule(val application: Application) {

    @Provides
    fun providesAppCacheDirectory() : Application  = application

    interface Exposes {

        val application: Application
    }

}
