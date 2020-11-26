package ru.turizmryazan.model.models


import com.google.gson.annotations.SerializedName

data class Gallery(
    @SerializedName("file_id")
    var fileId: String?,
    @SerializedName("id")
    var id: String?,
    @SerializedName("path")
    var path: String?
)