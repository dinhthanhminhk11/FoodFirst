package code.madlife.foodfirstver.presentation.feature.fragment.user.register

import android.annotation.SuppressLint
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import code.madlife.foodfirstver.R
import code.madlife.foodfirstver.core.common.Constants
import code.madlife.foodfirstver.core.common.showToastError
import code.madlife.foodfirstver.data.model.request.auth.REQLogin
import code.madlife.foodfirstver.databinding.FragmentRegisterBinding
import code.madlife.foodfirstver.encryption.Login
import code.madlife.foodfirstver.presentation.NavigationManager
import code.madlife.foodfirstver.presentation.core.base.BaseFragment
import code.madlife.foodfirstver.presentation.feature.fragment.user.login.AuthState
import code.madlife.foodfirstver.presentation.feature.fragment.user.otp.OtpFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: RegisterViewModel by viewModels()

    @SuppressLint("UseRequireInsteadOfGet")
    override fun initView() {
        binding.toolbar.setNavigationOnClickListener {
            NavigationManager.getInstance().popBackStack()
        }

        binding.btnContinue.setOnClickListener {
            val email = binding.username.text.toString()
            if (validateEmail(email)) {
                binding.btnContinue.isEnabled = false
                binding.progressBar.visibility = View.VISIBLE
                val text =
                    "{\"email\" : \"${binding.username.text.toString()}\"}"
                val textEntryPoint = Login.encryptData(text)
                viewModel.register(REQLogin(textEntryPoint))
            } else {
                showToastError(
                    activity = activity!!,
                    content = getString(R.string.please_enter_mail)
                )
            }
        }

        binding.login.setOnClickListener {
            NavigationManager.getInstance().popBackStack()
        }

    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun initObserver() {
        viewModel.authState.observe(viewLifecycleOwner) {
            when (it) {
                is AuthState.Success -> {
                    binding.btnContinue.isEnabled = true
                    binding.progressBar.visibility = View.GONE
                    NavigationManager.getInstance().openFragment(
                        OtpFragment.newInstance(
                            otpType = Constants.TYPE_REGISTER,
                            email = binding.username.text.toString()
                        )
                    )
                }

                is AuthState.Fail -> {
                    binding.btnContinue.isEnabled = true
                    binding.progressBar.visibility = View.GONE
                    if (it.code == Constants.CodeError.EMAIL_ALREADY_EXISTS) {
                        showToastError(
                            activity = activity!!,
                            content = getString(R.string.email_already_exits)
                        )
                    }
                }

                AuthState.Loading -> {
                    binding.btnContinue.isEnabled = false
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun getData() {
    }

    override fun onClick(v: View?) {

    }


    private fun validateEmail(email: String): Boolean {
        if (email.isEmpty()) {
            return false
        }
        val emailPattern = android.util.Patterns.EMAIL_ADDRESS
        return emailPattern.matcher(email).matches()
    }
}

