package co.lucaspinazzola.example.di.module


import co.lucaspinazzola.example.BuildConfig
import co.lucaspinazzola.example.data.api.GiphyApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {


    @Provides
    @Singleton
    fun provideGiphyApi() : GiphyApi = GiphyApi(BuildConfig.GIPHY_API_KEY, BuildConfig.DEBUG)


    interface Exposes {
    }

}
