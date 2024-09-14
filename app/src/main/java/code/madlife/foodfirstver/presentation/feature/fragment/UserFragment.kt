package code.madlife.foodfirstver.presentation.feature.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import code.madlife.foodfirstver.R
import code.madlife.foodfirstver.databinding.FragmentUserBinding
import code.madlife.foodfirstver.presentation.NavigationManager
import code.madlife.foodfirstver.presentation.core.base.BaseFragment

class UserFragment : BaseFragment<FragmentUserBinding>(FragmentUserBinding::inflate) {

    override fun initView() {
        binding.login.setOnClickListener {
            findNavController().navigate(R.id.action_kingMainFragment_to_loginFragment)
        }
    }

    override fun initObserver() {

    }

    override fun getData() {

    }

    override fun onClick(v: View?) {

    }
}