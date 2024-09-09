package code.madlife.foodfirstver.presentation.core.base_adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import code.madlife.foodfirstver.presentation.core.base.BaseFragment

@Suppress("UNCHECKED_CAST")
class MyViewPagerAdapter<D : Any, T : Fragment>(
    val fragment: Fragment,
    val listData: List<D>,
    private val createFragment: (data: D, position: Int) -> T
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = listData.size

    override fun createFragment(position: Int): Fragment {
        return getFragment(position)
    }

    fun getFragment(index: Int): T {
        var fragment = fragment.childFragmentManager.findFragmentByTag("f$index") as? T
        if (fragment == null) {
            fragment = createFragment.invoke(listData[index], index)
        }
        if (fragment is BaseFragment<*>)
            fragment.tabPosition = index
        return fragment
    }
}