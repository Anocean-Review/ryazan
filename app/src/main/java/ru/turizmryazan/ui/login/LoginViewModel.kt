package ru.turizmryazan.ui.login

import android.app.Application
import com.hadilq.liveevent.LiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.turizmryazan.TurizmApp
import ru.turizmryazan.base.BaseViewModel
import ru.turizmryazan.model.models.Authorization
import ru.turizmryazan.model.models.AuthorizationAnswer
import ru.turizmryazan.repository.PreferencesRepository
import javax.inject.Inject

class LoginViewModel(app: Application) : BaseViewModel(app) {

    init {
        TurizmApp.daggerComponent?.inject(this)
    }

    @Inject
    lateinit var prefRepository: PreferencesRepository

    val authorizationResult: LiveEvent<Boolean> = LiveEvent()

    fun authorization(email: String, password: String) {
        val auth = Authorization(email, password)
        disposables.add(
            repository.authorization(auth)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ answer -> onGetAuthorizationAnswer(answer) },
                    { e -> authorizationError(e) })
        )
    }

    private fun onGetAuthorizationAnswer(answer: AuthorizationAnswer?) {
        if (answer != null) {
            prefRepository.setAuthorizationData(answer)
            authorizationResult.value = true
        }
    }

    private fun authorizationError(e: Throwable?) {
        authorizationResult.value = false
    }

    fun rememberPassword(email: String) {

    }

}