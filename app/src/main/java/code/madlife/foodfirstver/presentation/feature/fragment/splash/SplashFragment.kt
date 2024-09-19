package code.madlife.foodfirstver.presentation.feature.fragment.splash

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import code.madlife.foodfirstver.MainViewModel
import code.madlife.foodfirstver.databinding.FragmentSplashBinding
import code.madlife.foodfirstver.presentation.NavigationManager
import code.madlife.foodfirstver.presentation.core.base.BaseFragment
import code.madlife.foodfirstver.presentation.feature.m00_main.KingMainFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {
    private val viewModel: MainViewModel by activityViewModels()
    override fun initView() {

    }

    override fun initObserver() {
        viewModel.addressData.observe(this) {
            binding.textLocation.text = it.toString()
            NavigationManager.getInstance().openFragmentNotAddBackStack(
                KingMainFragment(),
                isReplace = false,
                addToBackStack = false
            )
            NavigationManager.getInstance().removeFragment(this)
        }
    }

    override fun getData() {

    }

    override fun onClick(v: View?) {

    }

}