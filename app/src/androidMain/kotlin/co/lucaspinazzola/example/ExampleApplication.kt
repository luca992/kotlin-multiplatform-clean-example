package co.lucaspinazzola.example

import android.app.Application
import co.lucaspinazzola.example.di.AppComponent


class ExampleApplication: Application(){

    val mainComponent by lazy {
        AppComponent.init(this)
    }


}