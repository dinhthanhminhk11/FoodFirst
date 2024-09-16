package code.madlife.foodfirstver.presentation.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import code.madlife.foodfirstver.presentation.NavigationManager
import timber.log.Timber

abstract class BaseFragment<T: ViewBinding>(private val bindingInflater: (layoutInflater: LayoutInflater) -> T): Fragment(), View.OnClickListener {
    var _binding: T? = null
    protected val binding get() = _binding!!
    var TAG: String = this.javaClass.simpleName
    var isClickAble = true

    //child
    var tabPosition: Int = -1

    fun isFragmentFocusing(fragment: BaseFragment<*> = this): Boolean {
        val parentFragment = fragment.parentFragment
        if (parentFragment == null)
            return NavigationManager.getInstance().getCurrentFragment() == fragment
        else {
            if (parentFragment is BaseTabFragment<*>) {
                return parentFragment.getViewPager()?.currentItem == fragment.tabPosition && isFragmentFocusing(parentFragment)
            }
        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("BaseFragment => onViewCreated")
        initView()
        initObserver()
    }

    /**
     * implement function
     */

    open fun initArgs(){}
    abstract fun initView()
    abstract fun initObserver()
    abstract fun getData()
}