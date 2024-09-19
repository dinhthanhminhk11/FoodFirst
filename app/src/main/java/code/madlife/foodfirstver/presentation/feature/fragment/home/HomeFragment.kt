package code.madlife.foodfirstver.presentation.feature.fragment.home

import android.view.View
import androidx.fragment.app.activityViewModels
import code.madlife.foodfirstver.MainViewModel
import code.madlife.foodfirstver.databinding.FragmentHomeBinding
import code.madlife.foodfirstver.presentation.NavigationManager
import code.madlife.foodfirstver.presentation.core.base.BaseFragment
import code.madlife.foodfirstver.presentation.feature.m00_main.KingMainFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: MainViewModel by activityViewModels()
    override fun initView() {
    }

    override fun initObserver() {
        viewModel.addressData.observe(this) {
            binding.nameLocationYourSelf.text = it.toString()
        }
    }

    override fun getData() {
    }

    override fun onClick(v: View?) {
    }
}