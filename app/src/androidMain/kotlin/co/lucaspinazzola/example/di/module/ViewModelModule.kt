package co.lucaspinazzola.example.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.lucaspinazzola.example.ui.giphy.GiphyViewModel
import co.lucaspinazzola.example.di.ViewModelKey
import co.lucaspinazzola.example.di.scope.FragmentScope
import co.lucaspinazzola.example.domain.interactor.gif.GetGifsAndListenForUpdatesUseCase
import co.lucaspinazzola.example.domain.interactor.gif.UpdateGifsUseCase
import co.lucaspinazzola.example.domain.interactor.rickandmorty.GetCharacterImgsAndListenForUpdatesUseCase
import co.lucaspinazzola.example.domain.interactor.rickandmorty.UpdateCharacterImgsUseCase
import co.lucaspinazzola.example.domain.interactor.session.GetLastGifQueryUseCase
import co.lucaspinazzola.example.domain.interactor.session.SetLastGifQueryUseCase
import co.lucaspinazzola.example.ui.factory.ViewModelFactory
import co.lucaspinazzola.example.ui.rickandmorty.RickAndMortyCharactersViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @FragmentScope
    @Binds
    abstract fun provideViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Module
    companion object {


        @Provides
        @IntoMap
        @ViewModelKey(GiphyViewModel::class)
        @JvmStatic
        fun mapConversationsViewModel(
            getLastGifQueryUseCase: GetLastGifQueryUseCase,
            setLastGifQueryUseCase: SetLastGifQueryUseCase,
            getGifsAndListenForUpdatesUseCase: GetGifsAndListenForUpdatesUseCase,
            updateGifsUseCase: UpdateGifsUseCase
        ): ViewModel =
            GiphyViewModel(
                getLastGifQueryUseCase = getLastGifQueryUseCase,
                setLastGifQueryUseCase = setLastGifQueryUseCase,
                getGifsAndListenForUpdatesUseCase = getGifsAndListenForUpdatesUseCase,
                updateGifsUseCase = updateGifsUseCase
            )


        @Provides
        @IntoMap
        @ViewModelKey(RickAndMortyCharactersViewModel::class)
        @JvmStatic
        fun mapRickAndMortyCharactersViewModel(
            getCharacterImgsAndListenForUpdatesUseCase: GetCharacterImgsAndListenForUpdatesUseCase,
            updateCharacterImgsUseCase: UpdateCharacterImgsUseCase
        ): ViewModel =
            RickAndMortyCharactersViewModel(
                getCharacterImgsAndListenForUpdatesUseCase = getCharacterImgsAndListenForUpdatesUseCase,
                updateCharacterImgsUseCase = updateCharacterImgsUseCase
            )

    }

    interface Exposes {
        val factory: ViewModelFactory
    }


}