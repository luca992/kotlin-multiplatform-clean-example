package co.lucaspinazzola.example.ui.factory

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import javax.inject.Inject
import javax.inject.Provider

class InjectingFragmentFactory @Inject constructor(
        private val creators: Map<Class<out Fragment>, @JvmSuppressWildcards Provider<Fragment>>) : FragmentFactory() {


    override fun instantiate(classLoader: ClassLoader,className: String): Fragment {
        val fragmentClass = loadFragmentClass(classLoader, className)
        val creator = creators[fragmentClass]
                ?: return createFragmentAsFallback(classLoader, className)

        try {
            return creator.get()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private fun createFragmentAsFallback(classLoader: ClassLoader, className: String): Fragment {
        Log.w("FragmentFactoryImpl","No creator found for class: $className. Using default constructor")
        return super.instantiate(classLoader, className)
    }
}