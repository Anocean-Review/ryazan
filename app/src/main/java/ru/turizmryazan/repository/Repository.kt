package ru.turizmryazan.repository

import io.reactivex.Single
import retrofit2.http.Path
import ru.turizmryazan.TurizmApp
import ru.turizmryazan.http.RetrofitApi
import ru.turizmryazan.model.models.Hotel
import ru.turizmryazan.model.models.HotelDeatil
import ru.turizmryazan.model.models.Location
import ru.turizmryazan.model.models.Type
import javax.inject.Inject

class Repository {

    init {
        TurizmApp.daggerComponent?.inject(this)
    }

    @Inject
    lateinit var retrofitApi: RetrofitApi

    fun getHotels(): Single<Array<Hotel>> {
        return retrofitApi.getHotels()
    }
    fun getHotel(@Path("id") id: String): Single<HotelDeatil>{
        return retrofitApi.getHotel(id)
    }

    fun getDictionaryLocations(): Single<Array<Location>> {
        return retrofitApi.getDictionaryLocations()
    }

    fun getDictionaryTypeHotels(): Single<Array<Type>> {
        return retrofitApi.getDictionaryTypeHotels()
    }
}