package ru.turizmryazan.model.models


import com.google.gson.annotations.SerializedName

data class Attraction(
    @SerializedName("active")
    var active: Int?,
    @SerializedName("address")
    var address: String?,
    @SerializedName("advantages")
    var advantages: Int?,
    @SerializedName("contact_person")
    var contactPerson: String?,
    @SerializedName("created_at")
    var createdAt: String?,
    @SerializedName("description")
    var description: String?,
    @SerializedName("email")
    var email: String?,
    @SerializedName("id")
    var id: String?,
    @SerializedName("images")
    var images: Images?,
    @SerializedName("lat")
    var lat: String?,
    @SerializedName("location")
    var location: Location?,
    @SerializedName("lon")
    var lon: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("payment")
    var payment: Int?,
    @SerializedName("phone")
    var phone: Long?,
    @SerializedName("site")
    var site: String?,
    @SerializedName("sort")
    var sort: Int?,
    @SerializedName("type")
    var type: List<TypeAttraction>?,
    @SerializedName("updated_at")
    var updatedAt: String?,
    @SerializedName("work_time")
    var workTime: String?
)