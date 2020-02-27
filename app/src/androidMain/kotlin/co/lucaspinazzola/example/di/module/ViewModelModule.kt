package co.lucaspinazzola.example.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.lucaspinazzola.example.co.lucaspinazzola.example.ui.gifs.GiphyViewModel
import co.lucaspinazzola.example.di.ViewModelKey
import co.lucaspinazzola.example.di.scope.FragmentScope
import co.lucaspinazzola.example.ui.factory.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @FragmentScope
    @Binds
    abstract fun provideViewModelFactory( factory: ViewModelFactory):  ViewModelProvider.Factory

    @Module
    companion object {


        @Provides
        @IntoMap
        @ViewModelKey(GiphyViewModel::class)
        @JvmStatic
        fun mapConversationsViewModel() : ViewModel =
                GiphyViewModel()

    }

}