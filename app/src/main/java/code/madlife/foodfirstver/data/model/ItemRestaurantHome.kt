package code.madlife.foodfirstver.data.model


data class ItemRestaurantHome(
    val id: Int,
    val title: String,
    val content: String,
    val restaurants: List<Shop>
)