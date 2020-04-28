package co.lucaspinazzola.example.domain.interactor.gif

import co.lucaspinazzola.example.domain.model.Img
import co.lucaspinazzola.example.domain.repo.GiphyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart

class GetGifsAndListenForUpdatesUseCase(private val giphyRepository: GiphyRepository) {

    fun execute(query: String) : Flow<List<Img>> = channelFlow {
        send(giphyRepository.getGifs())
        giphyRepository.listenForGifUpdates()
                .onStart {
                    giphyRepository.updateGifs(query, 0)
                }.collect { gifs ->
                    send(gifs)
                }
    }

}