package ru.turizmryazan.model.models


import com.google.gson.annotations.SerializedName

data class TypeEatPlace(
    @SerializedName("id")
    var id: String?,
    @SerializedName("kitchen_id")
    var kitchenId: String?,
    @SerializedName("kitchen_name")
    var kitchenName: String?
)