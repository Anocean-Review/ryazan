package ru.turizmryazan.di.module

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
    fun providePreferencesRepository(): PreferencesRepository {
        return PreferencesRepository()
    }
}