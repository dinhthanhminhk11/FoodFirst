package code.madlife.foodfirstver.presentation.feature.fragment

import android.view.View
import code.madlife.foodfirstver.databinding.FragmentUserBinding
import code.madlife.foodfirstver.presentation.NavigationManager
import code.madlife.foodfirstver.presentation.core.base.BaseFragment
import code.madlife.foodfirstver.presentation.feature.fragment.user.LoginFragment

class UserFragment : BaseFragment<FragmentUserBinding>(FragmentUserBinding::inflate) {

    override fun initView() {
        binding.login.setOnClickListener {
            NavigationManager.getInstance().openFragment(LoginFragment.newInstance())
        }
    }

    override fun initObserver() {

    }

    override fun getData() {

    }

    override fun onClick(v: View?) {

    }
}