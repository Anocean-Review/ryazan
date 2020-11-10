package ru.turizmryazan.model.models


import com.google.gson.annotations.SerializedName

data class AuthorizationAnswer(
    @SerializedName("access_token")
    var accessToken: String?,
    @SerializedName("expires_in")
    var expiresIn: Int?,
    @SerializedName("permission")
    var permission: Boolean?,
    @SerializedName("refresh_token")
    var refreshToken: String?,
    @SerializedName("token_type")
    var tokenType: String?,
    @SerializedName("user")
    var user: User?
)