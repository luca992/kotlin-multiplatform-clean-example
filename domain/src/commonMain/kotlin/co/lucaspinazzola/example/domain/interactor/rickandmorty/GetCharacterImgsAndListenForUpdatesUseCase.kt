package co.lucaspinazzola.example.domain.interactor.rickandmorty

import co.lucaspinazzola.example.domain.model.Img
import co.lucaspinazzola.example.domain.repo.RickAndMortyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart

class GetCharacterImgsAndListenForUpdatesUseCase(private val rickAndMortyRepository: RickAndMortyRepository) {

    fun execute() : Flow<List<Img>> = channelFlow {
        send(rickAndMortyRepository.getCharacterImages())
        rickAndMortyRepository.listenForCharacterImageUpdates()
                .onStart {
                    rickAndMortyRepository.updateCharacterImages(1)
                }.collect { gifs ->
                    send(gifs)
                }
    }

}