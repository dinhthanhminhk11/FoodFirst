package code.madlife.foodfirstver.presentation.feature.m01

import code.madlife.foodfirstver.data.DemoRepository
import code.madlife.foodfirstver.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class M01ViewModel @Inject constructor(private val demoRepository: DemoRepository): BaseViewModel() {
}