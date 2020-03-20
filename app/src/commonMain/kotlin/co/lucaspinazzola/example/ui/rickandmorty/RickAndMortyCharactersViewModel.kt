package co.lucaspinazzola.example.ui.rickandmorty

import co.lucaspinazzola.example.ui.base.ViewModel
import co.lucaspinazzola.example.data.utils.printStackTrace
import co.lucaspinazzola.example.domain.interactor.rickandmorty.GetCharacterImgsAndListenForUpdatesUseCase
import co.lucaspinazzola.example.domain.interactor.rickandmorty.UpdateCharacterImgsUseCase
import co.lucaspinazzola.example.domain.model.Img
import co.lucaspinazzola.example.ui.utils.Visibility
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.flowOn

class RickAndMortyCharactersViewModel(
    private val getCharacterImgsAndListenForUpdatesUseCase: GetCharacterImgsAndListenForUpdatesUseCase,
    private val updateCharacterImgsUseCase: UpdateCharacterImgsUseCase): ViewModel() {


    val error = MutableLiveData<String?>(null)

    var lastQuery : String? = null
    val loadingIndicatorVisibility = MutableLiveData<Visibility>(Visibility.GONE)
    val imgs by lazy {
        MutableLiveData<List<Img>>(listOf()).apply {
            viewModelScope.launch {
                getConversationsAndListenForUpdates()
            }
        }
    }

    var getImgsAndListenForUpdatesJob : Job? = null

    fun loadNextPage() {
        loadingIndicatorVisibility.value = Visibility.VISIBLE
        viewModelScope.launch {
            try {
                withContext(Dispatchers.Default) {
                    updateCharacterImgsUseCase.execute(imgs.value.size.toLong()/20 + 1)
                }
            } catch (t: Throwable) {
                t.printStackTrace()
                error.value = t.message ?: ""
            }
            finally {
                loadingIndicatorVisibility.value = Visibility.GONE
            }
        }
    }



    private fun getConversationsAndListenForUpdates() {
        loadingIndicatorVisibility.value = Visibility.VISIBLE
        getImgsAndListenForUpdatesJob = viewModelScope.launch(Dispatchers.Default) {
            getCharacterImgsAndListenForUpdatesUseCase.execute()
                        .catch { t ->
                            when (t) {
                                is CancellationException -> {
                                    return@catch
                                }
                                else -> {
                                    t.printStackTrace()
                                    error.value = t.message ?: ""
                                }
                            }
                            loadingIndicatorVisibility.value = Visibility.GONE
                        }
                        .flowOn(Dispatchers.Main)
                        .collectIndexed { index, value ->
                            withContext(Dispatchers.Main) {
                                this@RickAndMortyCharactersViewModel.imgs.value = value
                                if (index == 1) {
                                    loadingIndicatorVisibility.value = Visibility.GONE
                                }
                            }
                        }
        }
    }


}