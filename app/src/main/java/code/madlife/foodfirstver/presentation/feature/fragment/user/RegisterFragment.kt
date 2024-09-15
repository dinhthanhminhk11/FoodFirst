package code.madlife.foodfirstver.presentation.feature.fragment.user

import android.view.View
import androidx.navigation.fragment.findNavController
import code.madlife.foodfirstver.databinding.FragmentRegisterBinding
import code.madlife.foodfirstver.presentation.NavigationManager
import code.madlife.foodfirstver.presentation.core.base.BaseFragment
import code.madlife.foodfirstver.presentation.feature.fragment.user.OtpFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    override fun initView() {
        binding.toolbar.setNavigationOnClickListener {
            NavigationManager.getInstance().popBackStack()
        }

        binding.btnContinue.setOnClickListener {
            NavigationManager.getInstance().openFragment(OtpFragment())
        }

        binding.login.setOnClickListener {
            NavigationManager.getInstance().popBackStack()
        }

    }

    override fun initObserver() {

    }

    override fun getData() {
    }

    override fun onClick(v: View?) {
    }
}

