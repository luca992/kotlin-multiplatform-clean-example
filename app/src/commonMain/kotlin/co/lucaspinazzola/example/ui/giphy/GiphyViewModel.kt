package co.lucaspinazzola.example.ui.giphy

import co.lucaspinazzola.example.ui.base.ViewModel
import co.lucaspinazzola.example.data.utils.printStackTrace
import co.lucaspinazzola.example.domain.interactor.gif.GetGifsAndListenForUpdatesUseCase
import co.lucaspinazzola.example.domain.interactor.gif.UpdateGifsUseCase
import co.lucaspinazzola.example.domain.interactor.session.GetLastGifQueryUseCase
import co.lucaspinazzola.example.domain.interactor.session.SetLastGifQueryUseCase
import co.lucaspinazzola.example.domain.model.Img
import co.lucaspinazzola.example.ui.utils.Visibility
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.asFlow
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.flowOn

class GiphyViewModel(
    private val getLastGifQueryUseCase: GetLastGifQueryUseCase,
    private val setLastGifQueryUseCase: SetLastGifQueryUseCase,
    private val getGifsAndListenForUpdatesUseCase: GetGifsAndListenForUpdatesUseCase,
    private val updateGifsUseCase: UpdateGifsUseCase): ViewModel() {


    val error = MutableLiveData<String?>(null)
    val query  by lazy {
        MutableLiveData<String>( "").apply {
            viewModelScope.launch {
                value = getLastGifQueryUseCase.execute()  ?: ""
            }
        }
    }
    var lastQuery : String? = null
    val loadingIndicatorVisibility = MutableLiveData<Visibility>(Visibility.GONE)
    val gifs by lazy {
        MutableLiveData<List<Img>>(listOf()).apply {
            viewModelScope.launch {
                query.asFlow().collect { query ->
                    if (lastQuery != query) {
                        viewModelScope.launch {
                            lastQuery = query
                            setLastGifQueryUseCase.execute(query)
                            getGifsAndListenForUpdatesJob?.cancelAndJoin()
                            getConversationsAndListenForUpdates()
                        }
                    }
                }
            }
        }
    }

    var getGifsAndListenForUpdatesJob : Job? = null

    fun loadNextPage() {
        loadingIndicatorVisibility.value = Visibility.VISIBLE
        viewModelScope.launch {
            try {
                withContext(Dispatchers.Default) {
                    updateGifsUseCase.execute(query.value,gifs.value.size.toLong())
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
        getGifsAndListenForUpdatesJob = viewModelScope.launch(Dispatchers.Default) {
            getGifsAndListenForUpdatesUseCase.execute(query = query.value)
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
                                this@GiphyViewModel.gifs.value = value
                                if (index == 1) {
                                    loadingIndicatorVisibility.value = Visibility.GONE
                                }
                            }
                        }
        }
    }


}