package co.lucaspinazzola.example.di.component

import co.lucaspinazzola.example.di.module.ComposableModule
import co.lucaspinazzola.example.di.module.FragmentModule
import co.lucaspinazzola.example.di.module.ViewModelModule
import co.lucaspinazzola.example.di.scope.FragmentScope
import co.lucaspinazzola.example.ui.base.ComposableView
import co.lucaspinazzola.example.ui.main.InjectingNavHostFragment
import dagger.Component
import javax.inject.Provider


@FragmentScope
@Component(dependencies = [AppComponent::class],
    modules = [FragmentModule::class, ViewModelModule::class, ComposableModule::class])
interface ViewComponent : ViewModelModule.Exposes {


    fun inject(service: InjectingNavHostFragment)
    fun composableViewByClass(): Map<Class<out ComposableView>,  @JvmSuppressWildcards ComposableView>

    object Initializer {

        fun init(applicationComponent: AppComponent): ViewComponent {
            return DaggerViewComponent.builder()
                    .appComponent(applicationComponent)
                    .build()
        }
    }
}