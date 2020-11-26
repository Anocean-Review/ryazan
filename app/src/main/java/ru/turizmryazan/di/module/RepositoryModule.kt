package ru.turizmryazan.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import ru.turizmryazan.repository.PreferencesRepository
import ru.turizmryazan.repository.Repository
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(): Repository {
        return Repository()
    }

    @Singleton
    @Provides
    fun providePreferencesRepository(app: Application): PreferencesRepository {
        return PreferencesRepository(app)
    }
}