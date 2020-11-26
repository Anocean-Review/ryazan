package ru.turizmryazan.ui.wheretogo

import android.app.Application
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.turizmryazan.TurizmApp
import ru.turizmryazan.base.BaseViewModel
import ru.turizmryazan.model.models.Attraction

class WhereToGoDetailViewModel(app: Application) : BaseViewModel(app) {

    init {
        TurizmApp.daggerComponent?.inject(this)
    }

    val attraction: MutableLiveData<Attraction> = MutableLiveData()

    fun loadData(attractionId: String) {
        disposables.add(
            repository.getAttraction(attractionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ value -> onGetAttraction(value) },
                    { e -> onGetErrorDetail(e) })
        )
    }

    private fun onGetAttraction(value: Attraction?) {
        value?.let {
            attraction.value = it
        }
    }
}