package code.madlife.foodfirstver.presentation.feature.fragment.user.otp

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Html
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
import code.madlife.foodfirstver.databinding.FragmentOtpBinding
import code.madlife.foodfirstver.encryption.Login
import code.madlife.foodfirstver.presentation.NavigationManager
import code.madlife.foodfirstver.presentation.core.base.BaseFragment
import code.madlife.foodfirstver.presentation.core.widget.dialog.DialogConfirmCustom
import code.madlife.foodfirstver.presentation.core.widget.otp.OnOtpCompletionListener
import code.madlife.foodfirstver.presentation.core.widget.toast.CookieBar
import code.madlife.foodfirstver.presentation.feature.fragment.user.login.AuthState
import code.madlife.foodfirstver.presentation.feature.fragment.user.register.SetPassFragment
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OtpFragment : BaseFragment<FragmentOtpBinding>(FragmentOtpBinding::inflate),
    OnOtpCompletionListener {
    private lateinit var countdownTimer: CountDownTimer
    private val otpValidityDurationInMillis: Long = 60000
    private var otpType: String? = null;
    private var email: String? = null;
    private var countResent: Int = 1;
    private val viewModel: OtpViewModel by viewModels()

    companion object {
        const val OTP_TYPE = "OTP_TYPE"
        const val EMAIL = "EMAIL"
        fun newInstance(otpType: String? = null, email: String? = null): OtpFragment {
            val fragment = OtpFragment()
            val args = Bundle()
            args.putString(EMAIL, email)
            args.putString(OTP_TYPE, otpType)
            fragment.arguments = args
            return fragment
        }
    }

    override fun initView() {
        val otpMessage = getString(R.string.content_otp_text_view, email)
        binding.content.text = Html.fromHtml(otpMessage, Html.FROM_HTML_MODE_LEGACY)
        startCountdownTimer()
        binding.resent.setOnClickListener {
            countdownTimer.cancel()

            val text =
                "{\"email\" : \"${email}\" , \"type\" : \"${otpType}\"}"
            val textEntryPoint = Login.encryptData(text)
            viewModel.reSentOtp(REQLogin(textEntryPoint))
        }

        binding.toolbar.setNavigationOnClickListener {
            DialogConfirmCustom.create(
                context = requireActivity(), content = getString(R.string.text_confirm_otp)
            ) {
                NavigationManager.getInstance().popBackStack()
            }.show()
        }

        binding.otp.requestFocus()
        binding.otp.setOtpCompletionListener(this)
    }

    override fun initArgs() {
        super.initArgs()
        email = arguments?.getString(EMAIL)
        otpType = arguments?.getString(OTP_TYPE)

    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun initObserver() {
        viewModel.resentOtpMutableLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is AuthState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    startCountdownTimer()
                    countResent++;
                }

                is AuthState.Fail -> {
                    binding.progressBar.visibility = View.GONE
                    if (it.code == Constants.CodeError.ACCOUNT_LOCKED) {
                        NavigationManager.getInstance().popBackStack()
                        showToastError(
                            activity = activity!!,
                            content = getString(R.string.account_locked)
                        )
                    }
                }

                AuthState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }

        viewModel.verifyOtpMutableLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is AuthState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    if (it.data.type.equals(Constants.TYPE_LOGIN)) {
                        val gson = Gson()
                        val user: User =
                            gson.fromJson(
                                Login.decryptData(it.data.data.toString()),
                                User::class.java
                            )
                        UserClient.setUserFromUser(user)
                        MySharedPreferences.getInstance(requireActivity())
                            .putString(Constants.TOKEN_USER, user.token.toString())
                        showToastSuccess(
                            activity = activity!!,
                            content = getString(R.string.login_success)
                        )
                        NavigationManager.getInstance().popToHome()
                    } else {
                        NavigationManager.getInstance()
                            .openFragment(SetPassFragment.newInstance(email = email), true)
                    }
                }

                is AuthState.Fail -> {
                    binding.progressBar.visibility = View.GONE
                    when (it.code) {
                        Constants.CodeError.ACCOUNT_LOCKED -> {
                            NavigationManager.getInstance().popBackStack()
                            showToastError(
                                activity = activity!!,
                                content = getString(R.string.account_locked)
                            )
                        }

                        Constants.CodeError.OTP_valid -> {
                            binding.otp.setLineColor(resources.getColor(R.color.color_red))
                            binding.otp.setTextColor(resources.getColor(R.color.color_red))
                            showToastError(
                                activity = activity!!,
                                content = getString(R.string.otp_valid)
                            )
                        }

                        Constants.CodeError.OTP_expired -> {
                            binding.otp.setLineColor(resources.getColor(R.color.color_red))
                            binding.otp.setTextColor(resources.getColor(R.color.color_red))
                            showToastError(
                                activity = activity!!,
                                content = getString(R.string.otp_expired)
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

    private fun startCountdownTimer() {
        countdownTimer = object : CountDownTimer(otpValidityDurationInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                val minutes = secondsRemaining / 60
                val seconds = secondsRemaining % 60
                val downTime = String.format("%d:%02d", minutes, seconds)
                binding.resent.text = downTime
                binding.resent.isEnabled = false
            }

            override fun onFinish() {
                binding.resent.text = getString(R.string.sent_again)
                binding.resent.isEnabled = true
                if (countResent == 5) {
                    binding.resent.visibility = View.GONE
                }
            }
        }
        countdownTimer.start()
    }

    override fun onOtpCompleted(otp: String?) {
        binding.progressBar.visibility = View.VISIBLE
        val text =
            "{\"email\" : \"${email}\" , \"otp\" : \"${otp}\" , \"type\" : \"${otpType}\"}"
        val textEntryPoint = Login.encryptData(text)
        viewModel.verifyOtp(REQLogin(textEntryPoint))
    }
}