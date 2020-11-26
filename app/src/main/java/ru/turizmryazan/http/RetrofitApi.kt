package ru.turizmryazan.http

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.turizmryazan.model.models.*

interface RetrofitApi {

    @POST("auth/login")
    fun authorization(@Body auth: Authorization): Single<AuthorizationAnswer>

    @GET("directory/region")
    fun getDictionaryLocations(): Single<Array<Location>>

    @GET("directory/hotel")
    fun getDictionaryTypeHotels(): Single<Array<Type>>

    @GET("directory/kitchen")
    fun getDictionaryTypeKitchens(): Single<Array<Type>>

    @GET("directory/sex")
    fun getDictionaryTypeSex(): Single<Array<Type>>

    @GET("directory/age")
    fun getDictionaryTypeAge(): Single<Array<Age>>

    @GET("hotels")
    fun getHotels(): Single<Array<Hotel>>

    @GET("hotels/{id}")
    fun getHotel(@Path("id") id: String): Single<HotelDetail>

    @GET("restaurant")
    fun getEatPlaces(): Single<Array<EatPlace>>

    @GET("restaurant/{id}")
    fun getEatPlace(@Path("id") id: String): Single<EatPlace>

    @GET("attractions")
    fun getAttractions(): Single<Array<Attraction>>

    @GET("directory/poi")
    fun getDictionaryAttractions(): Single<Array<Type>>

    @GET("attractions/{id}")
    fun getAttraction(@Path("id") id: String): Single<Attraction>
}