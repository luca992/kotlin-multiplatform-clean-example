package co.lucaspinazzola.example.di
import co.lucaspinazzola.example.ui.base.ComposableView
import dagger.MapKey
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(value = AnnotationRetention.RUNTIME)
@MapKey
annotation class ComposableViewKey(
    val value: KClass<out ComposableView>
)