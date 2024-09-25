package code.madlife.foodfirstver.presentation.feature.fragment.home


sealed class RestaurantState<in T : Any> private constructor() {
    class Success<T : Any>(val data: T) : RestaurantState<T>()
    object Loading : RestaurantState<Any>()
    class Fail(val msg: String = "Có lỗi xảy ra") : RestaurantState<Any>()
    object Init : RestaurantState<Any>()
}