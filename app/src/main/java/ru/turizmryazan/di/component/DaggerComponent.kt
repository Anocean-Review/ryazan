package ru.turizmryazan.di.component

import dagger.Component
import ru.turizmryazan.adapters.wheretoeat.WhereToEatRecyclerAdapter
import ru.turizmryazan.adapters.wheretostay.WhereToStayRecyclerAdapter
import ru.turizmryazan.base.BaseViewModel
import ru.turizmryazan.di.module.AppModule
import ru.turizmryazan.di.module.HttpModule
import ru.turizmryazan.di.module.InternalLinkModule
import ru.turizmryazan.di.module.RepositoryModule
import ru.turizmryazan.repository.Repository
import ru.turizmryazan.ui.login.LoginViewModel
import ru.turizmryazan.ui.main.MainViewModel
import ru.turizmryazan.ui.wheretoeat.WhereToEatDetailFragment
import ru.turizmryazan.ui.wheretoeat.WhereToEatDetailViewModel
import ru.turizmryazan.ui.wheretogo.WhereToGoDetailFragmnet
import ru.turizmryazan.ui.wheretogo.WhereToGoDetailViewModel
import ru.turizmryazan.ui.wheretostay.WhereToStayDetailFragment
import ru.turizmryazan.ui.wheretostay.WhereToStayDetailViewModel
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class,
        RepositoryModule::class,
        HttpModule::class,
        InternalLinkModule::class]
)
interface DaggerComponent {

    fun inject(repository: Repository)
    fun inject(repository: MainViewModel)
    fun inject(whereToStayRecyclerAdapter: WhereToStayRecyclerAdapter)
    fun inject(whereToStayDetailViewModel: WhereToStayDetailViewModel)
    fun inject(whereToEatRecyclerAdapter: WhereToEatRecyclerAdapter)
    fun inject(whereToEatDetailViewModel: WhereToEatDetailViewModel)
    fun inject(whereToStayDetailFragment: WhereToStayDetailFragment)
    fun inject(whereToEatDetailFragment: WhereToEatDetailFragment)
    fun inject(whereToGoDetailViewModel: WhereToGoDetailViewModel)
    fun inject(whereToGoDetailFragmnet: WhereToGoDetailFragmnet)
    fun inject(baseViewModel: BaseViewModel)
    fun inject(loginViewModel: LoginViewModel)
}