package ru.turizmryazan.model.models


import com.google.gson.annotations.SerializedName

data class Age(
    @SerializedName("created_at")
    var createdAt: String?,
    @SerializedName("id")
    var id: String?,
    @SerializedName("max")
    var max: Int?,
    @SerializedName("min")
    var min: Int?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("updated_at")
    var updatedAt: String?
)