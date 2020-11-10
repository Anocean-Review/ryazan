package ru.turizmryazan.di.module

import dagger.Module
import dagger.Provides
import ru.turizmryazan.utils.InternalLink
import javax.inject.Singleton

@Module
class InternalLinkModule {

    @Singleton
    @Provides
    fun provideInternalLink(): InternalLink{
        return InternalLink()
    }
}