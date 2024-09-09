package code.madlife.foodfirstver.presentation.feature.m00_main

import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import code.madlife.foodfirstver.R
import code.madlife.foodfirstver.core.common.Constants.TabMain.TAB_0
import code.madlife.foodfirstver.core.common.Constants.TabMain.TAB_1
import code.madlife.foodfirstver.core.common.Constants.TabMain.TAB_2
import code.madlife.foodfirstver.core.common.Constants.TabMain.TAB_3
import code.madlife.foodfirstver.core.common.reduceDragSensitivity
import code.madlife.foodfirstver.databinding.M00FragmentMainBinding
import code.madlife.foodfirstver.presentation.core.base.BaseTabFragment
import code.madlife.foodfirstver.presentation.core.base_adapter.MyViewPagerAdapter
import code.madlife.foodfirstver.presentation.core.widget.CustomTab
import code.madlife.foodfirstver.presentation.feature.m01.M01Fragment
import code.madlife.foodfirstver.presentation.feature.m02.M02Fragment
import code.madlife.foodfirstver.presentation.feature.m03.M03Fragment
import code.madlife.foodfirstver.presentation.feature.m04.M04Fragment
import code.madlife.foodfirstver.presentation.feature.m05.M05Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class M00MainFragment : BaseTabFragment<M00FragmentMainBinding>(M00FragmentMainBinding::inflate) {
    data class MainTabData(
        @DrawableRes val icon: Int,
        @DrawableRes val iconSelected: Int,
        @StringRes val title: Int
    )

    private val listTab = listOf(
        MainTabData(R.drawable.ic_tab_home, R.drawable.ic_tab_home_selected, R.string.tab_1),
        MainTabData(R.drawable.ic_tab_for_you, R.drawable.ic_tab_for_you_selected, R.string.tab_2),
        MainTabData(
            R.drawable.ic_tab_multimedia,
            R.drawable.ic_tab_multimedia_selected,
            R.string.tab_3
        ),
        MainTabData(
            R.drawable.ic_tab_notification,
            R.drawable.ic_tab_notification_selected,
            R.string.tab_4
        ),
        MainTabData(R.drawable.ic_tab_menu, R.drawable.ic_tab_menu_selected, R.string.tab_5)
    )

    private var pagerAdapter: MyViewPagerAdapter<MainTabData, Fragment>? = null
    override fun getViewPager(): ViewPager2 = binding.pager

    override fun initView() {
        pagerAdapter = MyViewPagerAdapter(this, listTab) { _, position ->
            when (position) {
                TAB_0 -> M01Fragment()
                TAB_1 -> M02Fragment()
                TAB_2 -> M03Fragment()
                TAB_3 -> M04Fragment()
                else -> M05Fragment()
            }
        }
        binding.pager.adapter = pagerAdapter
        binding.pager.reduceDragSensitivity()
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            val customViewTab = getCustomViewTab(
                ContextCompat.getDrawable(requireContext(), listTab.get(position).icon),
                ContextCompat.getDrawable(requireContext(), listTab.get(position).iconSelected),
                getString(listTab[position].title)
            )
            tab.customView = customViewTab
        }.attach()
        binding.pager.offscreenPageLimit = listTab.size
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                (tab?.customView as CustomTab).isSelected = true
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                (tab?.customView as CustomTab?)?.isSelected = false
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
        for (i in 0 until binding.tabLayout.tabCount) {
            val tabView = binding.tabLayout.getTabAt(i)?.view
            tabView?.setPadding(0, 0, 0, 0)
        }
    }

    override fun initObserver() {

    }

    override fun getData() {

    }

    override fun onClick(p0: View?) {

    }

    fun getCustomViewTab(image: Drawable?, imageSelected: Drawable?, text: String): CustomTab {
        val tab = CustomTab(requireContext())
        tab.setDrawable(image, imageSelected)
        tab.text.text = text
        return tab
    }
}