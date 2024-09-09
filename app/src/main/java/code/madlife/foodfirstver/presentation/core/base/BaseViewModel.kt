package code.madlife.foodfirstver.presentation.core.base

import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    companion object {
        const val PER_PAGE = 10
    }
    var currentPage = 1
}