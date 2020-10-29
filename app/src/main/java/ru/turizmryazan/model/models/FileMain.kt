package ru.turizmryazan.model.models


import com.google.gson.annotations.SerializedName

data class FileMain(
    @SerializedName("created_at")
    var createdAt: String?,
    @SerializedName("element")
    var element: String?,
    @SerializedName("id")
    var id: String?,
    @SerializedName("is_main")
    var isMain: Int?,
    @SerializedName("path")
    var path: String?,
    @SerializedName("updated_at")
    var updatedAt: String?
)