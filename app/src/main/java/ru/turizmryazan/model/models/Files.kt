package ru.turizmryazan.model.models


import com.google.gson.annotations.SerializedName

data class Files(
    @SerializedName("created_at")
    var createdAt: String?,
    @SerializedName("element_id")
    var elementId: String?,
    @SerializedName("file")
    var file: File?,
    @SerializedName("id")
    var id: String?,
    @SerializedName("main")
    var main: Int?,
    @SerializedName("mod")
    var mod: String?,
    @SerializedName("updated_at")
    var updatedAt: String?
)