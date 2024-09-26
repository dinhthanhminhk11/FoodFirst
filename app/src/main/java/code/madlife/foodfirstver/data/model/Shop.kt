package code.madlife.foodfirstver.data.model

import com.google.gson.annotations.SerializedName

data class Shop(
    val id: Long,
    val name: String,
    val avatar: String,
    val title: String,
    val address: String,
    val points: Long,
    @SerializedName("time_set_up")
    val timeSetUp: Long,
    val typeRestaurant: List<ParentType>,
    @SerializedName("distance_km")
    val distanceKM: Double,

    val status: Boolean
)
