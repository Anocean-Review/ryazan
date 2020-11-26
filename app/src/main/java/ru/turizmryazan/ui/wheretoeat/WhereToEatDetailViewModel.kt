package ru.turizmryazan.ui.wheretoeat

import android.app.Application
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.turizmryazan.TurizmApp
import ru.turizmryazan.base.BaseViewModel
import ru.turizmryazan.model.models.EatPlace

class WhereToEatDetailViewModel(app: Application) : BaseViewModel(app) {

    init {
        TurizmApp.daggerComponent?.inject(this)
    }

    var eatPlace: MutableLiveData<EatPlace> = MutableLiveData()

    fun loadData(eatPlaceId: String) {
        disposables.add(repository.getEatPlace(eatPlaceId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({value -> onGetEatPlace(value)},
                {e -> onGetErrorDetail(e)}))
    }

    private fun onGetEatPlace(value: EatPlace?) {
        value?.let {
            eatPlace. value = it
        }
    }
}