package co.lucaspinazzola.example.di.module

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import co.lucaspinazzola.example.di.FragmentKey
import co.lucaspinazzola.example.di.scope.FragmentScope
import co.lucaspinazzola.example.ui.factory.InjectingFragmentFactory
import co.lucaspinazzola.example.ui.giphy.GiphyFragment
import co.lucaspinazzola.example.ui.rickandmorty.RickAndMortyCharactersFragment
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
abstract class FragmentModule {

    @FragmentScope
    @Binds
    abstract fun bindFragmentFactory(factory: InjectingFragmentFactory): FragmentFactory

    @Module
    companion object {

        @Provides
        @IntoMap
        @FragmentKey(GiphyFragment::class)
        @JvmStatic
        fun mapFragmentIntoMap(factory: ViewModelProvider.Factory) : Fragment =
            GiphyFragment(factory)

        @Provides
        @IntoMap
        @FragmentKey(RickAndMortyCharactersFragment::class)
        @JvmStatic
        fun mapRickAndMortyCharactersFragmentIntoMap(factory: ViewModelProvider.Factory) : Fragment =
            RickAndMortyCharactersFragment(factory)
    }
}