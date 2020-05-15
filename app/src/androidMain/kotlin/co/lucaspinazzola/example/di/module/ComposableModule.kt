package co.lucaspinazzola.example.di.module

import co.lucaspinazzola.example.di.ComposableViewKey
import co.lucaspinazzola.example.di.scope.FragmentScope
import co.lucaspinazzola.example.ui.factory.ViewModelFactory
import co.lucaspinazzola.example.ui.rickandmorty.RickAndMortyCharacters
import co.lucaspinazzola.example.ui.rickandmorty.RickAndMortyCharactersViewModel
import dagger.Module
import dagger.Provides

@Module
abstract class ComposableModule {

    companion object {
        @FragmentScope
        @ComposableViewKey(RickAndMortyCharacters::class)
        @Provides
        @JvmStatic
        fun provideRickAndMortyCharacters(vmf: ViewModelFactory) =
            RickAndMortyCharacters(vmf.create(RickAndMortyCharactersViewModel::class.java))
    }

}