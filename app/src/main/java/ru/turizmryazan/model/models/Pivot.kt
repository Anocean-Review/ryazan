package ru.turizmryazan.model.models


import com.google.gson.annotations.SerializedName

data class Pivot(
    @SerializedName("permission_id")
    var permissionId: Int?,
    @SerializedName("user_id")
    var userId: String?
)