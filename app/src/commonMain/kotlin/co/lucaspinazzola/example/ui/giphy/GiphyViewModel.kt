package co.lucaspinazzola.example.ui.giphy

import co.lucaspinazzola.example.ui.base.ViewModel
import co.lucaspinazzola.example.data.utils.printStackTrace
import co.lucaspinazzola.example.domain.interactor.gif.GetGifsAndListenForUpdatesUseCase
import co.lucaspinazzola.example.domain.interactor.gif.UpdateGifsUseCase
import co.lucaspinazzola.example.domain.interactor.session.GetLastGifQueryUseCase
import co.lucaspinazzola.example.domain.interactor.session.SetLastGifQueryUseCase
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
    val query  = MutableLiveData<String>("")
    var lastQuery : String? = null
    val loadingIndicatorEnabled = MutableLiveData<Boolean>(false)

    /*val conversationListItems by lazy {
        MutableLiveData<List<ConversationListItem>>(listOf()).apply {
            viewModelScope.launch {
                query.asFlow().collect { query ->
                    if (lastQuery != query) {
                        viewModelScope.launch {
                            lastQuery = query
                            getConversationsAndListenForUpdatesJob?.cancelAndJoin()
                            getConversationsAndListenForUpdates()
                        }
                    }
                }
            }
        }
    }

    var getConversationsAndListenForUpdatesJob : Job? = null

    fun onRefresh() {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.Default) {
                    updateConversationsUseCase.execute()
                }
            } catch (t: Throwable) {
                t.printStackTrace()
                error.value = t.message ?: ""
            }
            finally {
                loadingIndicatorEnabled.value = false
            }
        }
    }



    private fun getConversationsAndListenForUpdates() {
        loadingIndicatorEnabled.value = true
        getConversationsAndListenForUpdatesJob = viewModelScope.launch(Dispatchers.Default) {
                getConversationsAndListenForUpdatesUseCase.execute(query = query.value, archived = false)
                        .catch { t ->
                            when (t) {
                                is CancellationException -> {
                                    return@catch
                                }
                                is GetConversationsAndListenForUpdatesUseCase.AlreadyUpToDateException -> {
                                }
                                else -> {
                                    t.printStackTrace()
                                    error.value = t.message ?: ""
                                }
                            }
                            loadingIndicatorEnabled.value = false
                        }
                        .flowOn(Dispatchers.Main)
                        .collectIndexed { index, value ->
                            withContext(Dispatchers.Main) {
                                this@GifsViewModel.conversationListItems.value = value
                                if (index == 1) {
                                    loadingIndicatorEnabled.value = false
                                }
                            }
                        }
        }
    }
*/

}