package ru.turizmryazan.http

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import ru.turizmryazan.model.models.Hotel
import ru.turizmryazan.model.models.HotelDeatil
import ru.turizmryazan.model.models.Location
import ru.turizmryazan.model.models.Type

interface RetrofitApi {

    @GET("hotels")
    fun getHotels(): Single<Array<Hotel>>

    @GET("directory/region")
    fun getDictionaryLocations(): Single<Array<Location>>

    @GET("directory/hotel")
    fun getDictionaryTypeHotels(): Single<Array<Type>>

    @GET("hotels/{id}")
    fun getHotel(@Path("id") id: String): Single<HotelDeatil>
}