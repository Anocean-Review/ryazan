package ru.turizmryazan.di.component

import dagger.Component
import ru.turizmryazan.adapters.WhereToStayRecyclerAdapter
import ru.turizmryazan.di.module.AppModule
import ru.turizmryazan.di.module.HttpModule
import ru.turizmryazan.di.module.RepositoryModule
import ru.turizmryazan.repository.Repository
import ru.turizmryazan.ui.main.MainViewModel
import ru.turizmryazan.ui.wheretostay.WhereToStayDetailViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RepositoryModule::class, HttpModule::class])
interface DaggerComponent {

    fun inject(repository: Repository)
    fun inject(repository: MainViewModel)
    fun inject(whereToStayRecyclerAdapter: WhereToStayRecyclerAdapter)
    fun inject(whereToStayDetailViewModel: WhereToStayDetailViewModel)
}