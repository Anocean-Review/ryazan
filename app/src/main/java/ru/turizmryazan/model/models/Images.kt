package ru.turizmryazan.model.models


import com.google.gson.annotations.SerializedName

data class Images(
    @SerializedName("gallery")
    var gallery: List<Gallery>?,
    @SerializedName("main")
    var main: Main?
)