package code.madlife.foodfirstver.data.model.response

import code.madlife.foodfirstver.data.model.ParentType

data class CategoryResponse(
    val id: Int,
    val name: String,
    val image: String,
    val imageUnselect: String,
    val parentType: List<ParentType>,
)