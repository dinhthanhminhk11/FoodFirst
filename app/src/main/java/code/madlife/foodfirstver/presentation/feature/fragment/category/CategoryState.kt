package code.madlife.foodfirstver.presentation.feature.fragment.category

import code.madlife.foodfirstver.data.model.response.CategoryResponse

sealed class CategoryState<in T : Any> private constructor() {
    class Success<T : Any>(val data: T) : CategoryState<T>()
    object Loading : CategoryState<Any>()
    class Fail(val msg: String = "Có lỗi xảy ra") : CategoryState<Any>()
    object Init : CategoryState<Any>()
}
