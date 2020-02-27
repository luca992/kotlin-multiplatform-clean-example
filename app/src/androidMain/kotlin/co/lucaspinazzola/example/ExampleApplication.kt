package co.lucaspinazzola.example

import android.app.Application
import android.content.Context
import co.lucaspinazzola.example.data.utils.initData
import co.lucaspinazzola.example.di.component.AppComponent


class ExampleApplication: Application(){

    val mainComponent by lazy {
        AppComponent.init(this)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        initData(this)
    }

}