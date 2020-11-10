package ru.turizmryazan.repository

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import ru.turizmryazan.TurizmApp
import ru.turizmryazan.http.RetrofitApi
import ru.turizmryazan.model.models.*
import javax.inject.Inject

class Repository {

    init {
        TurizmApp.daggerComponent?.inject(this)
    }

    @Inject
    lateinit var retrofitApi: RetrofitApi

    fun authorization(auth: Authorization): Single<AuthorizationAnswer> {
        return retrofitApi.authorization(auth)
    }

    fun getHotels(): Single<Array<Hotel>> {
        return retrofitApi.getHotels()
    }

    fun getHotel(@Path("id") id: String): Single<HotelDetail> {
        return retrofitApi.getHotel(id)
    }

    fun getDictionaryLocations(): Single<Array<Location>> {
        return retrofitApi.getDictionaryLocations()
    }

    fun getDictionaryTypeHotels(): Single<Array<Type>> {
        return retrofitApi.getDictionaryTypeHotels()
    }

    fun getDictionaryTypeSex(): Single<Array<Type>> {
        return retrofitApi.getDictionaryTypeSex()
    }

    fun getDictionaryTypeAge(): Single<Array<Age>> {
        return retrofitApi.getDictionaryTypeAge()
    }

    fun getDictionaryTypeKitchens(): Single<Array<Type>> {
        return retrofitApi.getDictionaryTypeKitchens()
    }

    fun getEatPlaces(): Single<Array<EatPlace>> {
        return retrofitApi.getEatPlaces()
    }

    fun getEatPlace(id: String): Single<EatPlace> {
        return retrofitApi.getEatPlace(id)
    }

    fun getAttractions(): Single<Array<Attraction>> {
        return retrofitApi.getAttractions()
    }

    fun getDictionaryAttractions(): Single<Array<Type>> {
        return retrofitApi.getDictionaryAttractions()
    }

    fun getAttraction(id: String): Single<Attraction> {
        return retrofitApi.getAttraction(id)
    }
}