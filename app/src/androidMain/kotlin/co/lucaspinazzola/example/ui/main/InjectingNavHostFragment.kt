package co.lucaspinazzola.example.ui.main

import android.content.Context
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import co.lucaspinazzola.example.ExampleApplication
import co.lucaspinazzola.example.di.FragmentComponent
import co.lucaspinazzola.example.ui.factory.InjectingFragmentFactory
import javax.inject.Inject

class InjectingNavHostFragment : NavHostFragment() {

    @Inject
    lateinit var daggerFragmentInjectionFactory: InjectingFragmentFactory

    override fun onAttach(context: Context) {
        val mainComponent = (context.applicationContext as ExampleApplication).mainComponent
        FragmentComponent.Initializer.init(mainComponent).inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        childFragmentManager.fragmentFactory = daggerFragmentInjectionFactory
        super.onCreate(savedInstanceState)
    }
}