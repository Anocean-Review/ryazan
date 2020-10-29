package ru.turizmryazan.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.turizmryazan.TurizmApp
import ru.turizmryazan.model.models.Hotel
import ru.turizmryazan.model.models.HotelsFilter
import ru.turizmryazan.model.models.Location
import ru.turizmryazan.model.models.Type
import ru.turizmryazan.repository.Repository
import ru.turizmryazan.utils.Constants
import javax.inject.Inject

class MainViewModel(app: Application) : AndroidViewModel(app) {

    init {
        TurizmApp.daggerComponent?.inject(this)
    }

    @Inject
    lateinit var repository: Repository

    val hotels: MutableLiveData<MutableList<Hotel>> = MutableLiveData()

    //справочники
    val dictionaryLocations: MutableLiveData<MutableList<Location>> = MutableLiveData()
    val dictionaryTypeHotels: MutableLiveData<MutableList<Type>> = MutableLiveData()

    //параметр-фильтры
    var hotelsFilter: MutableLiveData<HotelsFilter> = MutableLiveData()

    val disposables: CompositeDisposable = CompositeDisposable()

    fun loadHotels() {
        disposables.add(
            repository.getHotels()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ arHotels -> onGetHotels(arHotels) },
                    { e -> onError(e) })
        )
    }

    private fun onGetHotels(arHotels: Array<Hotel>?) {
        arHotels?.let {
            hotels.value = it.toMutableList()
        }
    }

    private fun onError(e: Throwable?) {
        Log.v(Constants.LOGS_TAG, "Ошибка rx - ${e?.message}")
    }

    fun loadDictionary() {
        disposables.add(
            repository.getDictionaryLocations()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ arDictionaryLocations ->
                    onGetarDictionaryLocations(arDictionaryLocations)
                },
                    { e -> onError(e) })
        )

        disposables.add(
            repository.getDictionaryTypeHotels()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ arDictionaryTypeHotels ->
                    onGetarDictionaryTypeHotels(arDictionaryTypeHotels)
                },
                    { e -> onError(e) })
        )
    }

    private fun onGetarDictionaryTypeHotels(arDictionaryTypeHotels: Array<Type>?) {
        arDictionaryTypeHotels?.let {
            dictionaryTypeHotels.value = it.toMutableList()
        }
    }

    private fun onGetarDictionaryLocations(arDictionaryLocations: Array<Location>?) {
        arDictionaryLocations?.let {
            dictionaryLocations.value = it.toMutableList()
        }
    }

    override fun onCleared() {
        super.onCleared()
        if (disposables.size() != 0) {
            disposables.dispose()
        }
    }
}