package ru.turizmryazan.model.models


import com.google.gson.annotations.SerializedName

data class File(
    @SerializedName("content_type")
    var contentType: String?,
    @SerializedName("created_at")
    var createdAt: String?,
    @SerializedName("height")
    var height: Any?,
    @SerializedName("id")
    var id: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("original_name")
    var originalName: String?,
    @SerializedName("real_path")
    var realPath: String?,
    @SerializedName("size")
    var size: String?,
    @SerializedName("sub_dir")
    var subDir: String?,
    @SerializedName("updated_at")
    var updatedAt: String?,
    @SerializedName("web_path")
    var webPath: String?,
    @SerializedName("width")
    var width: Any?
)