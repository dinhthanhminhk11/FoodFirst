package code.madlife.foodfirstver.presentation.feature.m00_main

import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import code.madlife.foodfirstver.R
import code.madlife.foodfirstver.core.common.Constants
import code.madlife.foodfirstver.core.common.reduceDragSensitivity
import code.madlife.foodfirstver.core.utils.PreferenceHelper
import code.madlife.foodfirstver.databinding.FragmentKingMainBinding
import code.madlife.foodfirstver.presentation.core.base.BaseTabFragment
import code.madlife.foodfirstver.presentation.core.base_adapter.MyViewPagerAdapter
import code.madlife.foodfirstver.presentation.core.common.Constants.TabMain.HOME_POSITION
import code.madlife.foodfirstver.presentation.core.common.Constants.TabMain.MULTIMEDIA_POSITION
import code.madlife.foodfirstver.presentation.core.utils.TimeUtils
import code.madlife.foodfirstver.presentation.core.widget.CustomTab
import code.madlife.foodfirstver.presentation.feature.fragment.home.FavoriteFragment
import code.madlife.foodfirstver.presentation.feature.fragment.home.HomeFragment
import code.madlife.foodfirstver.presentation.feature.fragment.home.MessageFragment
import code.madlife.foodfirstver.presentation.feature.fragment.home.OrdersFragment
import code.madlife.foodfirstver.presentation.feature.fragment.home.UserFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class KingMainFragment :
    BaseTabFragment<FragmentKingMainBinding>(FragmentKingMainBinding::inflate) {
    data class MainTabData(
        @DrawableRes val icon: Int,
        @DrawableRes val iconSelected: Int,
        @StringRes val title: Int
    )

//    private val listTab = listOf(
//        MainTabData(R.drawable.ic_tab_home, R.drawable.ic_tab_home_selected, R.string.home_title),
//        MainTabData(
//            R.drawable.baseline_video_camera_front_24_unselect,
//            R.drawable.baseline_video_camera_front_24,
//            R.string.live_title
//        ),
//        MainTabData(
//            R.drawable.ic_tab_multimedia,
//            R.drawable.ic_tab_multimedia_selected,
//            R.string.video_title
//        ),
//        MainTabData(
//            R.drawable.ic_tab_notification,
//            R.drawable.ic_tab_notification_selected,
//            R.string.notification_title
//        ),
//        MainTabData(
//            R.drawable.baseline_person_24,
//            R.drawable.baseline_person_24_selected,
//            R.string.user_title
//        )
//    )


    private val listTab = listOf(
        // sửa lại list thành home , yêu thích , đơn hàng , tin nhắn , user , ver cũ để ver2
        MainTabData(R.drawable.ic_tab_home, R.drawable.ic_tab_home_selected, R.string.home_title),
        MainTabData(
            R.drawable.baseline_favorite_24_unselect,
            R.drawable.baseline_favorite_24,
            R.string.favorite
        ),
        MainTabData(
            R.drawable.baseline_shopping_bag_24_unselect,
            R.drawable.baseline_shopping_bag_24,
            R.string.shop_bag
        ),
        MainTabData(
            R.drawable.baseline_message_24_unselect,
            R.drawable.baseline_message_24,
            R.string.message
        ),
        MainTabData(
            R.drawable.baseline_person_24,
            R.drawable.baseline_person_24_selected,
            R.string.user_title
        )
    )


    private var pagerAdapter: MyViewPagerAdapter<MainTabData, Fragment>? = null
    override fun getViewPager(): ViewPager2 = binding.pager

    override fun initView() {
        pagerAdapter = MyViewPagerAdapter(this, listTab) { _, position ->
            when (position) {
                Constants.TabMain.HOME -> HomeFragment()
                Constants.TabMain.LIVE -> FavoriteFragment()
                Constants.TabMain.VIDEO -> OrdersFragment()
                Constants.TabMain.NOTIFICATION -> MessageFragment()
                else -> UserFragment()
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

    fun updateToolTip(isShow: Boolean) {
//        if (needShowToolTip() && isShow)
//            showToolTipForYou()
//        else
//            removeToolTip()
    }

    private fun needShowToolTip(): Boolean {
        return PreferenceHelper.getInstance()
            .get(Constants.Preference.PREF_IS_SHOW_TOOL_TIP, true) &&
                isFragmentFocusing(this) &&
                TimeUtils.checkInRange6h30To18h30()

    }

    fun reloadFragmentCheckVideo() {
        try {
            when (tabSelecting) {
                MULTIMEDIA_POSITION -> {
//                    getTabMultimedia()?.reloadFragmentCheckVideo()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun backToHome() {
        try {
            if (tabSelecting != HOME_POSITION) {
                binding.pager.setCurrentItem(HOME_POSITION, false)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}