package code.madlife.foodfirstver.presentation.feature.fragment.user

import android.os.Bundle
import android.view.View
import android.widget.Toast
import code.madlife.foodfirstver.R
import code.madlife.foodfirstver.databinding.FragmentLoginBinding
import code.madlife.foodfirstver.presentation.NavigationManager
import code.madlife.foodfirstver.presentation.core.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private var isLoginByPass = true

    companion object {
        const val IS_GO_FROM_DETAIL = "IS_GO_FROM_DETAIL"
        const val NEW_ID = "NEW_ID"
        fun newInstance(goFromDetail: Boolean? = null, newsId: String? = null): LoginFragment {
            val fragment = LoginFragment()
            val args = Bundle()
            if (goFromDetail != null) {
                args.putBoolean(IS_GO_FROM_DETAIL, goFromDetail)
            }
            args.putString(NEW_ID, newsId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun initView() {

        binding.toolbar.setNavigationOnClickListener {
            NavigationManager.getInstance().popBackStack()
        }

        binding.loginByOTP.setOnClickListener {
            isLoginByPass = !isLoginByPass
            binding.loginByOTP.text =
                if (isLoginByPass) getString(R.string.login_by_otp) else getString(R.string.login_by_pass)
            binding.passwordContainer.visibility = if (isLoginByPass) View.VISIBLE else View.GONE
        }


        binding.login.setOnClickListener {
            Toast.makeText(
                requireContext(),
                if (isLoginByPass) "Login by pass" else "login otp",
                Toast.LENGTH_LONG
            )
                .show()

            NavigationManager.getInstance().openFragment(OtpFragment())
        }

        binding.tvSignUp.setOnClickListener {
            NavigationManager.getInstance().openFragment(RegisterFragment())
        }
    }

    override fun initObserver() {

    }

    override fun getData() {

    }

    override fun onClick(v: View?) {

    }

}