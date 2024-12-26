package code.madlife.foodfirstver.presentation.feature.fragment.user.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import code.madlife.foodfirstver.Capabilities
import code.madlife.foodfirstver.Contact
import code.madlife.foodfirstver.DeviceToken
import code.madlife.foodfirstver.DeviceTokenList
import code.madlife.foodfirstver.Mode
import code.madlife.foodfirstver.Options
import code.madlife.foodfirstver.PhoneNumber
import code.madlife.foodfirstver.R
import code.madlife.foodfirstver.TangoDevice
import code.madlife.foodfirstver.TangoRegistrationRequest
import code.madlife.foodfirstver.core.common.Constants
import code.madlife.foodfirstver.core.common.MySharedPreferences
import code.madlife.foodfirstver.core.common.showToastError
import code.madlife.foodfirstver.core.common.showToastSuccess
import code.madlife.foodfirstver.core.utils.Utility
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
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody


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
            val tangoRegistrationRequest: TangoRegistrationRequest =
                createFakeTangoRegistrationRequest();

            val byteArray = tangoRegistrationRequest.encode()

            val requestBody = RequestBody.create("application/x-protobuf".toMediaType(), byteArray)

            viewModel.fakeLogin(requestBody)
        }

        binding.tvSignUp.setOnClickListener {
            NavigationManager.getInstance().openFragment(RegisterFragment())
        }
    }

    private fun createFakeTangoRegistrationRequest(): TangoRegistrationRequest {
        return TangoRegistrationRequest(
            contact = Contact(
                phone_number = PhoneNumber(
                    iso2CountryCode = "vn",
                    subscriberNumber = "375784487"
                ),
                abookSize = 0,
                device = Utility.getDeviceInfo(),
                isPhone = true,
                iso2CountryCode = "vn",
                linkAccounts = true,
                locale = "vi_VN"
            ),
            tango_device = TangoDevice(
                deviceId = Utility.getDeviceId(requireActivity()),
                clientOsVersion = Utility.getAndroidVersion(),
                clientVersion = "8.79.1732129784",
                platform = 0
            ),
            device_token_list = createDeviceTokenList(),
            options = Options(
                allowAccessAddressBook = 0,
                storeAddressBook = 1
            ),
            capabilities = Capabilities(
                capabilities = createListCapabilities
            ),
            applicationId = "tango",
            mode = Mode.NORMAL,
            username = "fakeuser",
            password = "fakepassword",
            swiftPassword = "fakeswiftpassword",
            phonenumberOnly = true,
            requestAuthtokens = false,
        )
    }

    private val createListCapabilities = listOf(
        "twitter_digits.validation",
        "modalmessage",
        "UnsolicitedAccountVerificationSMS",
        "actionmessage-offer-call",
        "videomail",
        "textmessage",
        "imagemessage",
        "audiomessage",
        "groupmessage",
        "externalmessage",
        "capabilityparsable",
        "vgood_in_tc",
        "rrunread",
        "gift_vgood_pack_in_tc",
        "tc21",
        "tc_calllog",
        "scream",
        "voip_push_notification",
        "acme.v1",
        "cf.acme.v2",
        "social.v1",
        "social.v2",
        "social.v3",
        "social.v4",
        "social.v5",
        "social.v6",
        "tc.stranger",
        "tcalttext",
        "live.social.notification:1",
        "live.social.private",
        "live.social.private:2",
        "live.social.pullevents",
        "swift:7",
        "device.linking",
        "webrtc:1",
        "ios7.silent.push",
        "actionmessage",
        "cloud.profiles.v2",
        "ValidationCodeViaEmail",
        "ndigit.sms.validation",
        "swift:7"
    )

    fun createDeviceTokenList(inputMap: Map<DeviceTokenType, String>):  DeviceTokenList{
        val deviceTokens = inputMap.entries.map { entry ->
            val key = entry.key
            val value = entry.value
            val version = if (key == DeviceTokenType.IPHONE) "1.0" else null

            DeviceToken(
                content = value,
                type = key.toString(),
                version = version
            )
        }

        return DeviceTokenList(
            tokens = deviceTokens
        )
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
                    UserClient.saveUser(MySharedPreferences.getInstance(activity!!), user)
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
        if (password.isNullOrEmpty() && binding.passwordContainer.visibility != View.GONE) {
            showToastError(
                activity = activity!!,
                content = getString(R.string.pass_is_empty)
            )
            return false
        }

        if (password?.length!! < 8 && binding.passwordContainer.visibility != View.GONE) {
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