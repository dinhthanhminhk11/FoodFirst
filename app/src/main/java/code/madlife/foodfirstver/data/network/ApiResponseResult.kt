package code.madlife.foodfirstver.data.network

data class ApiResponseResult<T>(
    var code: Int,
    var message: String,
    var status: Boolean,
    var result: T,
)