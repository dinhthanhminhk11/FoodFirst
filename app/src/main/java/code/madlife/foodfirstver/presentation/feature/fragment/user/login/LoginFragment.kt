package code.madlife.foodfirstver.presentation.feature.fragment.user.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import code.madlife.foodfirstver.R
import code.madlife.foodfirstver.core.common.Constants
import code.madlife.foodfirstver.core.common.MySharedPreferences
import code.madlife.foodfirstver.data.model.request.auth.REQLogin
import code.madlife.foodfirstver.data.model.user.User
import code.madlife.foodfirstver.data.model.user.UserClient
import code.madlife.foodfirstver.data.model.user.UserClient.email
import code.madlife.foodfirstver.databinding.FragmentLoginBinding
import code.madlife.foodfirstver.encryption.Login
import code.madlife.foodfirstver.presentation.NavigationManager
import code.madlife.foodfirstver.presentation.core.base.BaseFragment
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
            binding.passwordContainer.visibility = if (isLoginByPass) View.VISIBLE else View.GONE
        }


        binding.login.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            if (validateInput(binding.username.text.toString(), binding.password.text.toString())) {
                if (isLoginByPass) {
                    val text =
                        "{\"email\" : \"${binding.username.text.toString()}\" , \"password\" : \"${binding.password.text.toString()}\"}"
                    val textEntryPoint = Login.encryptData(text)
                    viewModel.login(REQLogin(textEntryPoint))
                }
            }
        }

        binding.tvSignUp.setOnClickListener {
            NavigationManager.getInstance().openFragment(RegisterFragment())
        }
    }

    override fun initObserver() {
        viewModel.authState.observe(viewLifecycleOwner) {
            when (it) {
                is AuthState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val result = it.data
                    Toast.makeText(requireContext(), result.message.toString(), Toast.LENGTH_LONG)
                        .show()
                    val gson = Gson()
                    val user: User =
                        gson.fromJson(Login.decryptData(it.data.data.toString()), User::class.java)
                    UserClient.setUserFromUser(user)
                    MySharedPreferences.getInstance(requireActivity())
                        .putString(Constants.TOKEN_USER, user.token.toString())
                    NavigationManager.getInstance().popBackStack()
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

    fun validateInput(email: String?, password: String?): Boolean {
        if (email.isNullOrEmpty()) {
            showError("Email không được để trống")
            return false
        }
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        if (!email.matches(Regex(emailPattern))) {
            showError("Định dạng email không hợp lệ")
            return false
        }
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


    override fun getData() {

    }

    override fun onClick(v: View?) {

    }

}