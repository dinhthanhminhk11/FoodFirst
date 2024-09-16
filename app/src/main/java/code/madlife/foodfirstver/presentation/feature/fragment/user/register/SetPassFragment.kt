package code.madlife.foodfirstver.presentation.feature.fragment.user.register

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import code.madlife.foodfirstver.data.model.request.auth.REQLogin
import code.madlife.foodfirstver.databinding.FragmentSetPassBinding
import code.madlife.foodfirstver.encryption.Login
import code.madlife.foodfirstver.presentation.NavigationManager
import code.madlife.foodfirstver.presentation.core.base.BaseFragment
import code.madlife.foodfirstver.presentation.feature.fragment.user.login.AuthState
import code.madlife.foodfirstver.presentation.feature.fragment.user.login.LoginFragment
import code.madlife.foodfirstver.presentation.feature.fragment.user.otp.OtpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetPassFragment : BaseFragment<FragmentSetPassBinding>(FragmentSetPassBinding::inflate) {
    private var email: String? = null;
    private val viewModel: SetPassViewModel by viewModels()

    companion object {
        const val EMAIL = "EMAIL"
        fun newInstance(email: String? = null): SetPassFragment {
            val fragment = SetPassFragment()
            val args = Bundle()
            args.putString(EMAIL, email)
            fragment.arguments = args
            return fragment
        }
    }


    override fun initView() {
        binding.toolbar.setNavigationOnClickListener {
            NavigationManager.getInstance().popBackToFragment(LoginFragment::class.java)
        }

        binding.btnContinue.setOnClickListener {
            if (validateInput(binding.password.text.toString())) {
                val text =
                    "{\"email\" : \"${email}\" , \"password\" : \"${binding.password.text.toString()}\"}"
                val textEntryPoint = Login.encryptData(text)
                viewModel.setPassword(REQLogin(textEntryPoint))
            }
        }

    }

    override fun initObserver() {
        viewModel.setPasswordMutableLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is AuthState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), it.data.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }

                is AuthState.Fail -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }

                AuthState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun getData() {

    }

    override fun onClick(v: View?) {
    }

    override fun initArgs() {
        super.initArgs()
        email = arguments?.getString(EMAIL)
    }

    fun validateInput(password: String?): Boolean {
        if (password.isNullOrEmpty()) {
            showError("Mật khẩu không được để trống")
            return false
        }

        if (password.length < 8) {
            showError("Mật khẩu phải có ít nhất 8 ký tự")
            return false
        }
        return true
    }

    fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}