package ru.turizmryazan.base

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.hadilq.liveevent.LiveEvent
import io.reactivex.disposables.CompositeDisposable
import ru.turizmryazan.TurizmApp
import ru.turizmryazan.repository.Repository
import ru.turizmryazan.utils.Constants
import javax.inject.Inject

abstract class BaseViewModel(app: Application) : AndroidViewModel(app) {

    init {
        TurizmApp.daggerComponent?.inject(this)
    }

    @Inject
    lateinit var repository: Repository

    val disposables: CompositeDisposable = CompositeDisposable()
    val errorDetailData: LiveEvent<Boolean> = LiveEvent()

    fun onGetErrorDetail(e: Throwable?){
        Log.d(Constants.LOGS_TAG, "Ошибка Rx - ${e?.message}")
        errorDetailData.value = true
    }

    override fun onCleared() {
        Log.d(Constants.LOGS_TAG, "BaseViewModel onCleared")
        super.onCleared()
        if (disposables.size() != 0) {
            disposables.dispose()
        }
    }
}