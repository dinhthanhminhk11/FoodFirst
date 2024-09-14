package code.madlife.foodfirstver.presentation.feature.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import code.madlife.foodfirstver.R
import code.madlife.foodfirstver.databinding.FragmentLoginBinding
import code.madlife.foodfirstver.databinding.FragmentUserBinding
import code.madlife.foodfirstver.presentation.NavigationManager
import code.madlife.foodfirstver.presentation.core.base.BaseFragment

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    companion object {
//        const val IS_GO_FROM_DETAIL = "IS_GO_FROM_DETAIL"
//        const val NEW_ID = "NEW_ID"
//        fun newInstance(goFromDetail: Boolean? = null, newsId: String? = null): LoginFragment {
//            val fragment = LoginFragment()
//            val args = Bundle()
//            if (goFromDetail != null) {
//                args.putBoolean(IS_GO_FROM_DETAIL, goFromDetail)
//            }
//            args.putString(NEW_ID, newsId)
//            fragment.arguments = args
//            return fragment
//        }
    }

    override fun initView() {
        binding.login.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun initObserver() {

    }

    override fun getData() {

    }

    override fun onClick(v: View?) {

    }

}