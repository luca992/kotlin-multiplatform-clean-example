package co.lucaspinazzola.example.di.module


import co.lucaspinazzola.example.BuildConfig
import co.lucaspinazzola.example.data.api.GiphyApi
import co.lucaspinazzola.example.data.db.helper.*
import co.lucaspinazzola.example.data.db.initSqldelightDatabase
import co.lucaspinazzola.example.data.mapper.GifMapper
import co.lucaspinazzola.example.data.mapper.GifMapperImpl
import co.lucaspinazzola.example.data.mapper.SessionMapper
import co.lucaspinazzola.example.data.mapper.SessionMapperImpl
import co.lucaspinazzola.example.data.model.SessionData
import co.lucaspinazzola.example.data.model.sqldelight.Database
import co.lucaspinazzola.example.data.repo.GiphyRepositoryImpl
import co.lucaspinazzola.example.data.repo.SessionRepositoryImpl
import co.lucaspinazzola.example.domain.interactor.gif.GetGifsAndListenForUpdatesUseCase
import co.lucaspinazzola.example.domain.interactor.gif.UpdateGifsUseCase
import co.lucaspinazzola.example.domain.interactor.session.GetLastGifQueryUseCase
import co.lucaspinazzola.example.domain.repo.GiphyRepository
import co.lucaspinazzola.example.domain.repo.SessionRepository
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {


    @Provides
    @Singleton
    fun provideGiphyApi() : GiphyApi = GiphyApi(BuildConfig.GIPHY_API_KEY, BuildConfig.DEBUG)

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
                               gifDbHelper: GifDbHelper,
                               gifMapper: GifMapper
    ) : GiphyRepository = GiphyRepositoryImpl(
        api = giphyApi,
        gifDbHelper = gifDbHelper,
        gifMapper = gifMapper
    )

    @Provides
    @Singleton
    fun provideGifMapper() : GifMapper = GifMapperImpl()

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
    fun provideGifDbHelper(db: Database) : GifDbHelper =
        GifDbHelperImpl(db)

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

    interface Exposes {
        val getGifsAndListenForUpdatesUseCase: GetGifsAndListenForUpdatesUseCase
        val updateGifsUseCase: UpdateGifsUseCase
        val getLastGifQueryUseCase: GetLastGifQueryUseCase

    }

}
