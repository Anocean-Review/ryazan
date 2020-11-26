package ru.turizmryazan.model.models


import com.google.gson.annotations.SerializedName

data class Permission(
    @SerializedName("created_at")
    var createdAt: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("pivot")
    var pivot: Pivot?,
    @SerializedName("slug")
    var slug: String?,
    @SerializedName("updated_at")
    var updatedAt: String?
)