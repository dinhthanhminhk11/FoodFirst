package code.madlife.foodfirstver.presentation.feature.fragment.user

import android.os.CountDownTimer
import android.view.View
import code.madlife.foodfirstver.R
import code.madlife.foodfirstver.databinding.FragmentOtpBinding
import code.madlife.foodfirstver.presentation.NavigationManager
import code.madlife.foodfirstver.presentation.core.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OtpFragment : BaseFragment<FragmentOtpBinding>(FragmentOtpBinding::inflate) {
    private lateinit var countdownTimer: CountDownTimer
    private val otpValidityDurationInMillis: Long = 60000
    override fun initView() {
        startCountdownTimer()
        binding.resent.setOnClickListener {
            countdownTimer.cancel()
            startCountdownTimer()
        }

        binding.btnContinue.setOnClickListener {

        }

        binding.toolbar.setNavigationOnClickListener {
            NavigationManager.getInstance().popBackStack()
        }
    }

    override fun initObserver() {
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
            }
        }
        countdownTimer.start()
    }
}