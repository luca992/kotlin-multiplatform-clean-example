package co.lucaspinazzola.example.domain.interactor.rickandmorty

import co.lucaspinazzola.example.domain.model.Img
import co.lucaspinazzola.example.domain.repo.GiphyRepository
import co.lucaspinazzola.example.domain.repo.RickAndMortyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect

class GetCharacterImgsAndListenForUpdatesUseCase(private val rickAndMortyRepository: RickAndMortyRepository) {

    fun execute() : Flow<List<Img>> = channelFlow {
        send(rickAndMortyRepository.getCharacterImages())
        rickAndMortyRepository.listenForCharacterImageUpdates {
            rickAndMortyRepository.updateCharacterImages(0)
        }.collect { gifs ->
            send(gifs)
        }
    }

}