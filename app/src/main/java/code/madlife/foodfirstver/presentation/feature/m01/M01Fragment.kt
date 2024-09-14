package code.madlife.foodfirstver.presentation.feature.m01

import android.util.Log
import android.view.View
import code.madlife.foodfirstver.databinding.M01FragmentBinding
import code.madlife.foodfirstver.encryption.Login
import code.madlife.foodfirstver.presentation.core.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class M01Fragment : BaseFragment<M01FragmentBinding>(M01FragmentBinding::inflate) {
    override fun initView() {

        val text = "{\"email\" : \"quanvd31102002@gmail.com\" , \"password\" : \"quan3110\"}"

        val textEncryp = Login.decryptData("+0iSbuC3ONtR/hkziBYrOLEKrodqou3n628ADuw6q5IHKrUQr1mLplfHSE5urWKnkjUYKZf5Yks0Ut0uOTj1BabwxkH72FS/0amV1RWszz0=")
        Log.e("MinhData" , textEncryp)

    }

    override fun initObserver() {

    }

    override fun getData() {

    }

    override fun onClick(p0: View?) {

    }
}