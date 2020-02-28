package co.lucaspinazzola.example.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.lucaspinazzola.example.ui.giphy.GiphyViewModel
import co.lucaspinazzola.example.di.ViewModelKey
import co.lucaspinazzola.example.di.scope.FragmentScope
import co.lucaspinazzola.example.domain.interactor.gif.GetGifsAndListenForUpdatesUseCase
import co.lucaspinazzola.example.domain.interactor.gif.UpdateGifsUseCase
import co.lucaspinazzola.example.domain.interactor.session.GetLastGifQueryUseCase
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
        fun mapConversationsViewModel(getLastGifQueryUseCase: GetLastGifQueryUseCase,
                                      getGifsAndListenForUpdatesUseCase: GetGifsAndListenForUpdatesUseCase,
                                      updateGifsUseCase: UpdateGifsUseCase) : ViewModel =
            GiphyViewModel(
                getLastGifQueryUseCase = getLastGifQueryUseCase,
                getGifsAndListenForUpdatesUseCase = getGifsAndListenForUpdatesUseCase,
                updateGifsUseCase = updateGifsUseCase
            )

    }

}