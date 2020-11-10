package ru.turizmryazan.model.models


import com.google.gson.annotations.SerializedName

data class TypeAttraction(
    @SerializedName("attractions_id")
    var attractionsId: String?,
    @SerializedName("attractions_name")
    var attractionsName: String?,
    @SerializedName("id")
    var id: String?
)