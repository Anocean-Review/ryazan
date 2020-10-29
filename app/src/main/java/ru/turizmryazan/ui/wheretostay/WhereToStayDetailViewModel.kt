package ru.turizmryazan.ui.wheretostay

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.turizmryazan.TurizmApp
import ru.turizmryazan.model.models.Hotel
import ru.turizmryazan.model.models.HotelDeatil
import ru.turizmryazan.repository.Repository
import ru.turizmryazan.utils.Constants
import javax.inject.Inject

class WhereToStayDetailViewModel(app: Application) : AndroidViewModel(app) {

    init {
        TurizmApp.daggerComponent?.inject(this)
    }

    @Inject
    lateinit var repository: Repository

    val disposables: CompositeDisposable = CompositeDisposable()

    var hotel: MutableLiveData<HotelDeatil> = MutableLiveData()

    fun loadData(id: String) {
        disposables.add(repository.getHotel(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ hotel -> onGetHotel(hotel)}, { e-> onError(e)}))
    }

    private fun onGetHotel(hotel: HotelDeatil?) {
        hotel?.let {
            this.hotel.value = it
        }
    }

    private fun onError(e: Throwable?) {
        Log.v(Constants.LOGS_TAG, "Ошибка получения отеля +${e?.message}")
    }

    override fun onCleared() {
        super.onCleared()
        if (disposables.size() != 0) {
            disposables.dispose()
        }
    }

}