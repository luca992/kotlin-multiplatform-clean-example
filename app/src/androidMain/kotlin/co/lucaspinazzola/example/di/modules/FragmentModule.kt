package co.lucaspinazzola.example.di.modules

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import co.lucaspinazzola.example.di.FragmentKey
import co.lucaspinazzola.example.di.scope.FragmentScope
import co.lucaspinazzola.example.ui.factory.InjectingFragmentFactory
import co.lucaspinazzola.example.ui.main.MainFragment
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
        @FragmentKey(MainFragment::class)
        @JvmStatic
        fun mapFragmentIntoMap() : Fragment =
                MainFragment()
    }
}