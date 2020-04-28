package co.lucaspinazzola.example.di.component

import co.lucaspinazzola.example.di.module.FragmentModule
import co.lucaspinazzola.example.di.module.ViewModelModule
import co.lucaspinazzola.example.di.scope.FragmentScope
import co.lucaspinazzola.example.ui.main.InjectingNavHostFragment
import dagger.Component


@FragmentScope
@Component(dependencies = [AppComponent::class],
    modules = [FragmentModule::class, ViewModelModule::class])
interface FragmentComponent {


    fun inject(service: InjectingNavHostFragment)

    object Initializer {

        fun init(applicationComponent: AppComponent): FragmentComponent {
            return DaggerFragmentComponent.builder()
                    .appComponent(applicationComponent)
                    .build()
        }
    }
}