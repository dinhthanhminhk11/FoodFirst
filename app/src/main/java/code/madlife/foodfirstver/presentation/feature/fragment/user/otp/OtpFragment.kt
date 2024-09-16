package code.madlife.foodfirstver.presentation.feature.fragment.user.otp

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import code.madlife.foodfirstver.R
import code.madlife.foodfirstver.data.model.request.auth.REQLogin
import code.madlife.foodfirstver.databinding.FragmentOtpBinding
import code.madlife.foodfirstver.encryption.Login
import code.madlife.foodfirstver.presentation.NavigationManager
import code.madlife.foodfirstver.presentation.core.base.BaseFragment
import code.madlife.foodfirstver.presentation.core.widget.otp.OnOtpCompletionListener
import code.madlife.foodfirstver.presentation.feature.fragment.user.login.AuthState
import code.madlife.foodfirstver.presentation.feature.fragment.user.register.SetPassFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OtpFragment : BaseFragment<FragmentOtpBinding>(FragmentOtpBinding::inflate),
    OnOtpCompletionListener {
    private lateinit var countdownTimer: CountDownTimer
    private val otpValidityDurationInMillis: Long = 5000
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
            NavigationManager.getInstance().popBackStack()
        }

        binding.otp.requestFocus()
        binding.otp.setOtpCompletionListener(this)
    }

    override fun initArgs() {
        super.initArgs()
        email = arguments?.getString(EMAIL)
        otpType = arguments?.getString(OTP_TYPE)

    }

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
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_LONG)
                        .show()
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
                    NavigationManager.getInstance()
                        .openFragment(SetPassFragment.newInstance(email = email), true)
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