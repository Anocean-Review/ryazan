package ru.turizmryazan.utils

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import ru.turizmryazan.R
import ru.turizmryazan.model.models.*

object Utils {
    fun getBaseUrl(): String = Constants.BASE_URL + Constants.VERSION_API

    fun getYandexMapUri(lat: String = "", lon: String = ""): String {
        return Constants.YANDEX_MAP_SERVER + lat + "," + lon
    }

    fun getTypesKitchen(eatPlaces: List<TypeEatPlace>?): String {
        var result = ""
        var position = 0
        eatPlaces?.let {
            it.forEach { type ->
                result += type.kitchenName + if (position == eatPlaces.size - 1) "" else ", "
                position++
            }
        }

        return result
    }

    fun getTypesAttraction(attractions: List<TypeAttraction>?): String {
        var result = ""
        var position = 0
        attractions?.let {
            it.forEach { type ->
                result += type.attractionsName + if (position == attractions.size - 1) "" else ", "
                position++
            }
        }

        return result
    }

    fun getImageUrlList(galleries: List<Gallery>?): MutableList<String> {
        val list: MutableList<String> = mutableListOf()
        galleries?.let {
            it.forEach { image ->
                val url = Constants.BASE_IMAGE_URL + image.path
                list.add(url)
            }
        }
        return list
    }

    fun getLocationAdapter(context: Context, locations: MutableList<Location>): SpinnerAdapter {
        val listLoaction: MutableList<String> = mutableListOf(context.getString(R.string.location))

        locations.forEach { location ->
            listLoaction.add(location.name ?: "")
        }

        return ArrayAdapter(
            context,
            R.layout.spinner_item,
            listLoaction
        )
    }

    fun getTypeKitchensAdapter(context: Context, kitchens: MutableList<Type>): SpinnerAdapter {
        val listTypeKitchens: MutableList<String> = mutableListOf(context.getString(R.string.type_kitchen))

        kitchens.forEach { typeKitchen ->
            listTypeKitchens.add(typeKitchen.name ?: "")
        }

        return ArrayAdapter(
            context,
            R.layout.spinner_item,
            listTypeKitchens
        )
    }

    fun getTypeAttractionAdapter(context: Context, attractions: MutableList<Type>): SpinnerAdapter {
        val listTypeAttractions: MutableList<String> = mutableListOf(context.getString(R.string.type_attraction))

        attractions.forEach { typeAttraction ->
            listTypeAttractions.add(typeAttraction.name ?: "")
        }

        return ArrayAdapter(
            context,
            R.layout.spinner_item,
            listTypeAttractions
        )
    }

    fun getTypeHotelsAdapter(context: Context, hotels: MutableList<Type>): SpinnerAdapter {
        val listTypeHotels: MutableList<String> = mutableListOf(context.getString(R.string.type_hotels))

        hotels.forEach { typeHotel ->
            listTypeHotels.add(typeHotel.name ?: "")
        }

        return ArrayAdapter(
            context,
            R.layout.spinner_item,
            listTypeHotels
        )
    }

    fun getTypeAgeAdapter(context: Context, ages: MutableList<Age>): SpinnerAdapter {
        val listTypeAge: MutableList<String> = mutableListOf(context.getString(R.string.type_age))

        val sortedAges = ages.sortedBy { c -> c.min }
        sortedAges.forEach { typeAge ->
            listTypeAge.add(typeAge.name ?: "")
        }

        return ArrayAdapter(
            context,
            R.layout.spinner_item,
            listTypeAge
        )
    }

    fun getTypeSexAdapter(context: Context, sexs: MutableList<Type>): SpinnerAdapter {
        val listTypeSex: MutableList<String> = mutableListOf(context.getString(R.string.type_sex))

        sexs.forEach { typeSex ->
            listTypeSex.add(typeSex.name ?: "")
        }

        return ArrayAdapter(
            context,
            R.layout.spinner_item,
            listTypeSex
        )
    }

}