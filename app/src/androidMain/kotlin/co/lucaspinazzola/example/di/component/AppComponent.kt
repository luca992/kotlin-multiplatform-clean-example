package co.lucaspinazzola.example.di.component

import co.lucaspinazzola.example.ExampleApplication
import co.lucaspinazzola.example.di.module.AppModule
import co.lucaspinazzola.example.di.module.DataModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    DataModule::class,
    AppModule::class
])
interface AppComponent : AppComponentExposes {

    fun inject(service: ExampleApplication)

    companion object Initializer {
        fun init(): AppComponent {
            return DaggerAppComponent.builder()
                    .appModule(AppModule())
                    .dataModule(DataModule())
                    .build()
        }
    }
}