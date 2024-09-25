package code.madlife.foodfirstver.data.model

import com.google.gson.annotations.SerializedName

data class Result(
    val id: Int,
    val title: String,
    val content: String,
    val restaurants: List<Shop>
)