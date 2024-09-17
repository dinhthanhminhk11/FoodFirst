package code.madlife.foodfirstver.presentation.feature.fragment.user.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.compose.runtime.snapshots.Snapshot.Companion.observe
import androidx.fragment.app.viewModels
import code.madlife.foodfirstver.R
import code.madlife.foodfirstver.core.common.Constants
import code.madlife.foodfirstver.core.common.MySharedPreferences
import code.madlife.foodfirstver.core.common.showToastError
import code.madlife.foodfirstver.core.common.showToastSuccess
import code.madlife.foodfirstver.data.model.request.auth.REQLogin
import code.madlife.foodfirstver.data.model.user.User
import code.madlife.foodfirstver.data.model.user.UserClient
import code.madlife.foodfirstver.databinding.FragmentLoginBinding
import code.madlife.foodfirstver.encryption.Login
import code.madlife.foodfirstver.presentation.NavigationManager
import code.madlife.foodfirstver.presentation.core.base.BaseFragment
import code.madlife.foodfirstver.presentation.feature.fragment.user.otp.OtpFragment
import code.madlife.foodfirstver.presentation.feature.fragment.user.register.RegisterFragment
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private var isLoginByPass = true
    private val viewModel: LoginViewModel by viewModels()

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

    @SuppressLint("SetTextI18n")
    override fun initView() {
        binding.username.setText("minhk11642002@gmail.com")
        binding.password.setText("minhdinhk11")

        binding.toolbar.setNavigationOnClickListener {
            NavigationManager.getInstance().popBackStack()
        }

        binding.loginByOTP.setOnClickListener {
            isLoginByPass = !isLoginByPass
            binding.loginByOTP.text =
                if (isLoginByPass) getString(R.string.login_by_otp) else getString(R.string.login_by_pass)
            binding.contentLogin.text =
                if (isLoginByPass) getString(R.string.enter_form) else getString(R.string.enter_form_login_by_otp)
            binding.passwordContainer.visibility = if (isLoginByPass) View.VISIBLE else View.GONE
        }


        binding.login.setOnClickListener {
            if (validateInput(binding.username.text.toString(), binding.password.text.toString())) {
                binding.progressBar.visibility = View.VISIBLE
                binding.login.isEnabled = false
                if (isLoginByPass) {
                    val text =
                        "{\"email\" : \"${binding.username.text.toString()}\" , \"password\" : \"${binding.password.text.toString()}\"}"
                    val textEntryPoint = Login.encryptData(text)
                    viewModel.login(REQLogin(textEntryPoint))
                } else {
                    val text =
                        "{\"email\" : \"${binding.username.text.toString()}\"}"
                    val textEntryPoint = Login.encryptData(text)
                    viewModel.checkAccount(REQLogin(textEntryPoint))
                }
            }
        }

        binding.tvSignUp.setOnClickListener {
            NavigationManager.getInstance().openFragment(RegisterFragment())
        }
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun initObserver() {
        viewModel.authState.observe(viewLifecycleOwner) {
            when (it) {
                is AuthState.Success -> {
                    binding.login.isEnabled = true
                    binding.progressBar.visibility = View.GONE
                    val gson = Gson()
                    val user: User =
                        gson.fromJson(Login.decryptData(it.data.data.toString()), User::class.java)
                    UserClient.setUserFromUser(user)
                    MySharedPreferences.getInstance(requireActivity())
                        .putString(Constants.TOKEN_USER, user.token.toString())
                    showToastSuccess(
                        activity = activity!!,
                        content = getString(R.string.login_success)
                    )
                    NavigationManager.getInstance().popToHome()
                }

                is AuthState.Fail -> {
                    binding.login.isEnabled = true
                    binding.progressBar.visibility = View.GONE
                    when (it.code) {
                        Constants.CodeError.email_not_exits -> {
                            showToastError(
                                activity = activity!!,
                                content = getString(R.string.email_not_exits)
                            )
                        }

                        Constants.CodeError.email_and_pass_not_exits -> {
                            showToastError(
                                activity = activity!!,
                                content = getString(R.string.email_and_pass_not_exits)
                            )
                        }

                        Constants.CodeError.ACCOUNT_LOCKED -> {
                            NavigationManager.getInstance().popBackStack()
                            showToastError(
                                activity = activity!!,
                                content = getString(R.string.account_locked)
                            )
                        }
                    }
                }

                AuthState.Loading -> {
                    binding.login.isEnabled = false
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
        viewModel.checkAccountMutableLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is AuthState.Success -> {
                    binding.login.isEnabled = true
                    binding.progressBar.visibility = View.GONE
                    NavigationManager.getInstance().openFragment(
                        OtpFragment.newInstance(
                            otpType = Constants.TYPE_LOGIN,
                            email = binding.username.text.toString()
                        )
                    )
                }

                is AuthState.Fail -> {
                    binding.login.isEnabled = true
                    binding.progressBar.visibility = View.GONE
                    when (it.code) {
                        Constants.CodeError.email_un_verify -> {
                            NavigationManager.getInstance().openFragment(
                                OtpFragment.newInstance(
                                    otpType = Constants.TYPE_REGISTER,
                                    email = binding.username.text.toString()
                                )
                            )
                            showToastError(
                                activity = activity!!,
                                content = getString(R.string.email_un_verify)
                            )
                        }

                        Constants.CodeError.email_not_exits -> {
                            showToastError(
                                activity = activity!!,
                                content = getString(R.string.email_not_exits)
                            )
                        }

                        Constants.CodeError.ACCOUNT_LOCKED -> {
                            NavigationManager.getInstance().popBackStack()
                            showToastError(
                                activity = activity!!,
                                content = getString(R.string.account_locked)
                            )
                        }
                    }
                }

                AuthState.Loading -> {
                    binding.login.isEnabled = false
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    @SuppressLint("UseRequireInsteadOfGet")
    fun validateInput(email: String?, password: String?): Boolean {
        if (email.isNullOrEmpty()) {
            showToastError(
                activity = activity!!,
                content = getString(R.string.email_is_empty)
            )
            return false
        }
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        if (!email.matches(Regex(emailPattern))) {
            showToastError(
                activity = activity!!,
                content = getString(R.string.please_enter_mail)
            )
            return false
        }
        if (password.isNullOrEmpty()) {
            showToastError(
                activity = activity!!,
                content = getString(R.string.pass_is_empty)
            )
            return false
        }

        if (password.length < 8) {
            showToastError(
                activity = activity!!,
                content = getString(R.string.pass_is_valid)
            )
            return false
        }
        return true
    }

    override fun getData() {

    }

    override fun onClick(v: View?) {

    }

}