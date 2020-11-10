package ru.turizmryazan.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.turizmryazan.TurizmApp
import ru.turizmryazan.base.BaseViewModel
import ru.turizmryazan.model.models.*
import ru.turizmryazan.utils.Constants

class MainViewModel(app: Application) : BaseViewModel(app) {

    init {
        TurizmApp.daggerComponent?.inject(this)
    }

    val hotels: MutableLiveData<MutableList<Hotel>> = MutableLiveData()
    val eatPlaces: MutableLiveData<MutableList<EatPlace>> = MutableLiveData()
    val attractions: MutableLiveData<MutableList<Attraction>> = MutableLiveData()

    //справочники
    val dictionaryLocations: MutableLiveData<MutableList<Location>> = MutableLiveData()
    val dictionaryTypeHotels: MutableLiveData<MutableList<Type>> = MutableLiveData()
    val dictionaryTypeKitchens: MutableLiveData<MutableList<Type>> = MutableLiveData()
    val dictionaryTypeAttractions: MutableLiveData<MutableList<Type>> = MutableLiveData()
    val dictionaryTypeSex: MutableLiveData<MutableList<Type>> = MutableLiveData()
    val dictionaryTypeAge: MutableLiveData<MutableList<Age>> = MutableLiveData()

    //параметр-фильтры
    var hotelsFilter: MutableLiveData<HotelsFilter> = MutableLiveData()
    var kitchensFilter: MutableLiveData<KitchensFilter> = MutableLiveData()
    var attractionFilter: MutableLiveData<AttractionFilter> = MutableLiveData()

    fun loadDictionary() {
        if (dictionaryLocations.value == null) {
            disposables.add(
                repository.getDictionaryLocations()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ arDictionaryLocations ->
                        onGetarDictionaryLocations(arDictionaryLocations)
                    },
                        { e -> onErrorDictionary(e) })
            )
        }

        if (dictionaryTypeHotels.value == null) {
            disposables.add(
                repository.getDictionaryTypeHotels()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ arDictionaryTypeHotels ->
                        onGetarDictionaryTypeHotels(arDictionaryTypeHotels)
                    },
                        { e -> onErrorDictionary(e) })
            )
        }

        if (dictionaryTypeKitchens.value == null) {
            disposables.add(
                repository.getDictionaryTypeKitchens()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ arDictionaryTypeKitchens ->
                        onGetarDictionaryTypeKitchens(arDictionaryTypeKitchens)
                    },
                        { e -> onErrorDictionary(e) })
            )
        }

        if (dictionaryTypeAttractions.value == null) {
            disposables.add(
                repository.getDictionaryAttractions()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ arDictionaryTypeAttractions ->
                        onGetarDictionaryTypeAttractions(arDictionaryTypeAttractions)
                    },
                        { e -> onErrorDictionary(e) })
            )
        }

        if (dictionaryTypeSex.value == null) {
            disposables.add(
                repository.getDictionaryTypeSex()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ arDictionaryTypeSex ->
                        onGetarDictionaryTypeSex(arDictionaryTypeSex)
                    },
                        { e -> onErrorDictionary(e) })
            )
        }

        if (dictionaryTypeAge.value == null) {
            disposables.add(
                repository.getDictionaryTypeAge()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ arDictionaryTypeAge ->
                        onGetarDictionaryTypeAge(arDictionaryTypeAge)
                    },
                        { e -> onErrorDictionary(e) })
            )
        }
    }

    private fun onGetarDictionaryTypeAge(arDictionaryTypeAge: Array<Age>?) {
        arDictionaryTypeAge?.let {
            dictionaryTypeAge.value = it.toMutableList()
        }
    }

    private fun onGetarDictionaryTypeSex(arDictionaryTypeSex: Array<Type>?) {
        arDictionaryTypeSex?.let {
            dictionaryTypeSex.value = it.toMutableList()
        }
    }

    private fun onErrorDictionary(e: Throwable?) {
        Log.d(Constants.LOGS_TAG, "Ошибка rx - ${e?.message}")
    }

    private fun onGetarDictionaryTypeKitchens(arDictionaryTypeKitchens: Array<Type>?) {
        arDictionaryTypeKitchens?.let {
            dictionaryTypeKitchens.value = it.toMutableList()
        }
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

    private fun onGetarDictionaryTypeAttractions(arDictionaryTypeAttractions: Array<Type>?) {
        arDictionaryTypeAttractions?.let {
            dictionaryTypeAttractions.value = it.toMutableList()
        }
    }

    fun loadHotels() {
        disposables.add(
            repository.getHotels()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ arHotels -> onGetHotels(arHotels) },
                    { e -> onGetErrorDetail(e) })
        )
    }

    private fun onGetHotels(arHotels: Array<Hotel>?) {
        arHotels?.let {
            hotels.value = it.toMutableList()
        }
    }

    fun loadEatPlaces() {
        disposables.add(
            repository.getEatPlaces()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ arEatPlaces -> onGetEatPlaces(arEatPlaces) },
                    { e -> onGetErrorDetail(e) })
        )
    }

    private fun onGetEatPlaces(arEatPlaces: Array<EatPlace>?) {
        arEatPlaces?.let {
            eatPlaces.value = it.toMutableList()
        }
    }

    fun loadAttractions() {
        disposables.add(
            repository.getAttractions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ arAttractions -> onGetAttractions(arAttractions) },
                    { e -> onGetErrorDetail(e) })
        )
    }

    private fun onGetAttractions(arAttractions: Array<Attraction>?) {
        arAttractions?.let {
            attractions.value = it.toMutableList()
        }
    }
}