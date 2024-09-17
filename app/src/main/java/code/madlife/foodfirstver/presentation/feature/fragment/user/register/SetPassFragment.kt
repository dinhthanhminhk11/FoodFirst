package code.madlife.foodfirstver.presentation.feature.fragment.user.register

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import code.madlife.foodfirstver.R
import code.madlife.foodfirstver.core.common.Constants
import code.madlife.foodfirstver.core.common.MySharedPreferences
import code.madlife.foodfirstver.core.common.showToastError
import code.madlife.foodfirstver.core.common.showToastSuccess
import code.madlife.foodfirstver.data.model.request.auth.REQLogin
import code.madlife.foodfirstver.data.model.user.User
import code.madlife.foodfirstver.data.model.user.UserClient
import code.madlife.foodfirstver.databinding.FragmentSetPassBinding
import code.madlife.foodfirstver.encryption.Login
import code.madlife.foodfirstver.presentation.NavigationManager
import code.madlife.foodfirstver.presentation.core.base.BaseFragment
import code.madlife.foodfirstver.presentation.core.widget.dialog.DialogConfirmCustom
import code.madlife.foodfirstver.presentation.feature.fragment.user.login.AuthState
import code.madlife.foodfirstver.presentation.feature.fragment.user.login.LoginFragment
import com.google.gson.Gson
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
            DialogConfirmCustom.create(
                context = requireActivity(), content = getString(R.string.text_confirm_setPass)
            ) {
                NavigationManager.getInstance().popBackToFragment(LoginFragment::class.java)
            }.show()
        }

        binding.btnContinue.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            binding.btnContinue.isEnabled = false
            if (validateInput(binding.password.text.toString())) {
                val text =
                    "{\"email\" : \"${email}\" , \"password\" : \"${binding.password.text.toString()}\"}"
                val textEntryPoint = Login.encryptData(text)
                viewModel.setPassword(REQLogin(textEntryPoint))
            }
        }
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun initObserver() {
        viewModel.setPasswordMutableLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is AuthState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    DialogConfirmCustom.create(
                        context = requireActivity(),
                        content = getString(R.string.text_confirm_setPass_login),
                        textConfirm = getString(R.string.agree)
                    ) {
                        val text =
                            "{\"email\" : \"${email}\" , \"password\" : \"${binding.password.text.toString()}\"}"
                        val textEntryPoint = Login.encryptData(text)
                        viewModel.login(REQLogin(textEntryPoint))
                    }.show()
                }

                is AuthState.Fail -> {
                    binding.btnContinue.isEnabled = true
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }

                AuthState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
        viewModel.authState.observe(viewLifecycleOwner) {
            when (it) {
                is AuthState.Success -> {
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

    @SuppressLint("UseRequireInsteadOfGet")
    fun validateInput(password: String?): Boolean {
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
}