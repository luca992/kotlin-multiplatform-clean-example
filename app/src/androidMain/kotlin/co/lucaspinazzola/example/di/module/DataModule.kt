package co.lucaspinazzola.example.di.module


import co.lucaspinazzola.example.BuildConfig
import co.lucaspinazzola.example.data.api.giphy.GiphyApi
import co.lucaspinazzola.example.data.api.rickandmorty.RickAndMortyApi
import co.lucaspinazzola.example.data.db.helper.*
import co.lucaspinazzola.example.data.db.initSqldelightDatabase
import co.lucaspinazzola.example.data.mapper.ImgMapper
import co.lucaspinazzola.example.data.mapper.ImgMapperImpl
import co.lucaspinazzola.example.data.mapper.SessionMapper
import co.lucaspinazzola.example.data.mapper.SessionMapperImpl
import co.lucaspinazzola.example.data.model.sqldelight.Database
import co.lucaspinazzola.example.data.repo.GiphyRepositoryImpl
import co.lucaspinazzola.example.data.repo.RickAndMortyRepositoryImpl
import co.lucaspinazzola.example.data.repo.SessionRepositoryImpl
import co.lucaspinazzola.example.domain.interactor.gif.GetGifsAndListenForUpdatesUseCase
import co.lucaspinazzola.example.domain.interactor.gif.UpdateGifsUseCase
import co.lucaspinazzola.example.domain.interactor.rickandmorty.GetCharacterImgsAndListenForUpdatesUseCase
import co.lucaspinazzola.example.domain.interactor.rickandmorty.UpdateCharacterImgsUseCase
import co.lucaspinazzola.example.domain.interactor.session.GetLastGifQueryUseCase
import co.lucaspinazzola.example.domain.interactor.session.SetLastGifQueryUseCase
import co.lucaspinazzola.example.domain.repo.GiphyRepository
import co.lucaspinazzola.example.domain.repo.RickAndMortyRepository
import co.lucaspinazzola.example.domain.repo.SessionRepository
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {


    @Provides
    @Singleton
    fun provideGiphyApi() : GiphyApi =
        GiphyApi(
            BuildConfig.GIPHY_API_KEY,
            BuildConfig.DEBUG
        )

    @Provides
    @Singleton
    fun provideRickAndMortyApi() : RickAndMortyApi =
        RickAndMortyApi(
            BuildConfig.DEBUG
        )

    @Provides
    @Singleton
    fun provideSessionRepository(sessionDbHelper: SessionDbHelper,
                                 sessionMapper: SessionMapper
    ) : SessionRepository = SessionRepositoryImpl(
        sessionDbHelper = sessionDbHelper,
        sessionMapper = sessionMapper
    )

    @Provides
    @Singleton
    fun provideGiphyRepository(giphyApi: GiphyApi,
                               imgDbHelper: ImgDbHelper,
                               imgMapper: ImgMapper
    ) : GiphyRepository = GiphyRepositoryImpl(
        api = giphyApi,
        imgDbHelper = imgDbHelper,
        imgMapper = imgMapper
    )


    @Provides
    @Singleton
    fun provideRickAndMortyRepository(rickAndMortyApi: RickAndMortyApi,
                               imgDbHelper: ImgDbHelper,
                               imgMapper: ImgMapper
    ) : RickAndMortyRepository = RickAndMortyRepositoryImpl(
        api = rickAndMortyApi,
        imgDbHelper = imgDbHelper,
        imgMapper = imgMapper
    )


    @Provides
    @Singleton
    fun provideGifMapper() : ImgMapper = ImgMapperImpl()

    @Provides
    @Singleton
    fun provideSessionMapper() : SessionMapper = SessionMapperImpl()

    @Provides
    @Singleton
    fun provideDatabaseDriver() : SqlDriver = initSqldelightDatabase()

    @Provides
    @Singleton
    fun provideDatabase(driver: SqlDriver) =
        Database(driver)

    @Provides
    @Singleton
    fun provideSessionDbHelper(db: Database) : SessionDbHelper =
        SessionDbHelperImpl(db)

    @Provides
    @Singleton
    fun provideGifDbHelper(db: Database) : ImgDbHelper =
        ImgDbHelperImpl(db)

    @Provides
    @Singleton
    fun providesGetGifsAndListenForUpdatesUseCase(repository: GiphyRepository) : GetGifsAndListenForUpdatesUseCase =
        GetGifsAndListenForUpdatesUseCase(repository)

    @Provides
    @Singleton
    fun providesUpdateGifsUseCase(repository: GiphyRepository) : UpdateGifsUseCase =
        UpdateGifsUseCase(repository)

    @Provides
    @Singleton
    fun providesGetLastGifQueryUseCase(repository: SessionRepository) : GetLastGifQueryUseCase =
        GetLastGifQueryUseCase(repository)

    @Provides
    @Singleton
    fun providesSetLastGifQueryUseCase(repository: SessionRepository) : SetLastGifQueryUseCase =
        SetLastGifQueryUseCase(repository)

    @Provides
    @Singleton
    fun providesGetCharacterImgsAndListenForUpdatesUseCase(repository: RickAndMortyRepository) : GetCharacterImgsAndListenForUpdatesUseCase =
        GetCharacterImgsAndListenForUpdatesUseCase(repository)

    @Provides
    @Singleton
    fun providesUpdateCharacterImgsUseCase(repository: RickAndMortyRepository) : UpdateCharacterImgsUseCase =
        UpdateCharacterImgsUseCase(repository)

    interface Exposes {
        val getGifsAndListenForUpdatesUseCase: GetGifsAndListenForUpdatesUseCase
        val updateGifsUseCase: UpdateGifsUseCase
        val getLastGifQueryUseCase: GetLastGifQueryUseCase
        val setLastGifQueryUseCase: SetLastGifQueryUseCase
        val getCharacterImgsAndListenForUpdatesUseCase: GetCharacterImgsAndListenForUpdatesUseCase
        val updateCharacterImgsUseCase: UpdateCharacterImgsUseCase

    }

}
