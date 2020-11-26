package ru.turizmryazan.model.models


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("age")
    var age: String?,
    @SerializedName("created_at")
    var createdAt: String?,
    @SerializedName("email")
    var email: String?,
    @SerializedName("email_verified_at")
    var emailVerifiedAt: String?,
    @SerializedName("id")
    var id: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("permissions")
    var permissions: List<Permission>?,
    @SerializedName("region")
    var region: String?,
    @SerializedName("sex")
    var sex: String?,
    @SerializedName("updated_at")
    var updatedAt: String?
)