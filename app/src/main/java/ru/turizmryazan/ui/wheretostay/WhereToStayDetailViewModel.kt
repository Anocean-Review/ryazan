package ru.turizmryazan.ui.wheretostay

import android.app.Application
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.turizmryazan.TurizmApp
import ru.turizmryazan.base.BaseViewModel
import ru.turizmryazan.model.models.HotelDetail

class WhereToStayDetailViewModel(app: Application) : BaseViewModel(app) {

    init {
        TurizmApp.daggerComponent?.inject(this)
    }

    var hotel: MutableLiveData<HotelDetail> = MutableLiveData()

    fun loadData(id: String) {
        disposables.add(repository.getHotel(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ hotel -> onGetHotel(hotel)}, { e-> onGetErrorDetail(e)}))
    }

    private fun onGetHotel(hotel: HotelDetail?) {
        hotel?.let {
            this.hotel.value = it
        }
    }
}