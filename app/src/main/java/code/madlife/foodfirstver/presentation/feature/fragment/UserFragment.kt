package code.madlife.foodfirstver.presentation.feature.fragment

import android.view.View
import code.madlife.foodfirstver.core.common.Constants
import code.madlife.foodfirstver.core.common.MySharedPreferences
import code.madlife.foodfirstver.data.model.user.UserClient
import code.madlife.foodfirstver.databinding.FragmentUserBinding
import code.madlife.foodfirstver.presentation.NavigationManager
import code.madlife.foodfirstver.presentation.core.base.BaseFragment
import code.madlife.foodfirstver.presentation.feature.fragment.user.login.LoginFragment

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

    override fun onResume() {
        super.onResume()
        val token =
            MySharedPreferences.getInstance(requireActivity()).getString(Constants.TOKEN_USER, "")
        if (!token.isNullOrEmpty()) {
            binding.text.text = UserClient.email
        }
    }
}