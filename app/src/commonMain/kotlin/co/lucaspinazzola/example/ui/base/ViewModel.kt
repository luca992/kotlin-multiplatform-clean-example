package co.lucaspinazzola.example.ui.base
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.Job

abstract class ViewModel : ViewModel() {

    val viewModelSupervisorJob: Job?
        get() = viewModelScope.coroutineContext[Job]

}