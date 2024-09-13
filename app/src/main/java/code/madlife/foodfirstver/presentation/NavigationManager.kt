package code.madlife.foodfirstver.presentation

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import androidx.annotation.AnimRes
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.OnBackStackChangedListener
import androidx.fragment.app.commit
import code.madlife.foodfirstver.R
import code.madlife.foodfirstver.presentation.feature.m00_main.KingMainFragment
import timber.log.Timber

class NavigationManager : OnBackStackChangedListener {
    protected lateinit var mActivity: Activity
    lateinit var mFragmentManager: FragmentManager
    private var mContentId: Int? = null
    var navigateAble = true
    val handlerNavigate = Handler(Looper.getMainLooper())

    companion object {
        fun getInstance(): NavigationManager = NavigationManagerHolder.navigationManagerHolder
    }

    private object NavigationManagerHolder {
        @SuppressLint("StaticFieldLeak")
        val navigationManagerHolder = NavigationManager()
    }

    fun init(activity: Activity, fragmentManager: FragmentManager, @IdRes contentId: Int) {
        mActivity = activity
        mFragmentManager = fragmentManager
        mContentId = contentId
        mFragmentManager.addOnBackStackChangedListener(this)
    }

    /**
     * flag mark the Navigation is created on Fragment class
     */
    fun isBackStackEmpty() = mFragmentManager.backStackEntryCount == 0
    fun isRoot() = mFragmentManager.backStackEntryCount <= 1

    fun popBackStack() {
        mFragmentManager.popBackStack()
    }

    private inline fun <reified T : Fragment> getFragmentInBackStack(mFragmentManager: FragmentManager): T? {
        mFragmentManager.fragments.forEach {
            val findMainFragment = it
            if (findMainFragment is T) {
                return findMainFragment
            }
        }
        return null
    }

    fun popToHome() {
        if (mFragmentManager.backStackEntryCount > 0) {
            val homeFragmentId = mFragmentManager.getBackStackEntryAt(0).id
            mFragmentManager.popBackStack(homeFragmentId, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            getFragmentInBackStack<KingMainFragment>(mFragmentManager)?.backToHome()
        } else {
            popBackStack()
        }
    }

    fun openFragment(
        fragment: Fragment,
        isReplace: Boolean = false,
        @AnimRes enter: Int,
        @AnimRes exit: Int,
        @AnimRes popEnter: Int,
        @AnimRes popExit: Int
    ) {
        try {
            if (!navigateAble)
                return
            mFragmentManager.commit {
                if (enter != 0 || exit != 0 || popEnter != 0 || popExit != 0) {
                    setCustomAnimations(enter, exit, popEnter, popExit)
                }
                mContentId?.let {
                    if (isReplace)
                        replace(it, fragment)
                    else
                        add(it, fragment, fragment::class.simpleName)

                }
                addToBackStack((2147483646.0 * Math.random()).toInt().toString())
                navigateAble = false
                handlerNavigate.postDelayed({
                    navigateAble = true
                }, 1000)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun openFragment(fragment: Fragment, isReplace: Boolean = false) {
        openFragment(fragment, isReplace, R.anim.slide_in_left, R.anim.opacity_1_to_0, 0, R.anim.slide_out_right)
    }

    fun openFragmentBottomUp(fragment: Fragment, isReplace: Boolean = false) {
        openFragment(fragment, isReplace, R.anim.slide_in_up, R.anim.opacity_1_to_0, 0, R.anim.slide_out_down)
    }

    fun getCurrentFragment(): Fragment? {
        return try {
            mContentId?.let { mFragmentManager.findFragmentById(it) }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getColorFromDestination(fragment: Fragment) = when (fragment) {
//        is M17WebViewFragment -> R.color.primary
        else -> R.color.color_white
    }

    fun changeStatusBar(@ColorRes color: Int) {
        val listColorLight = listOf(R.color.color_white, R.color.active)
        try {
            WindowInsetsControllerCompat(mActivity.window, mActivity.window.decorView).apply {
                isAppearanceLightStatusBars = listColorLight.contains(color)
                isAppearanceLightNavigationBars = listColorLight.contains(color)

            }
            val sourceColor = ContextCompat.getColor(mActivity, color)
            mActivity.window.statusBarColor = sourceColor
            mActivity.window.navigationBarColor = sourceColor
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onBackStackChanged() {
        if (getCurrentFragment() == null) return
        val fragment: Fragment? = getCurrentFragment()
        Timber.e(fragment.toString())
        Handler(Looper.getMainLooper()).postDelayed({
            fragment?.let {
                val color = getColorFromDestination(it)
                changeStatusBar(color)
            }
        }, 300)
        Timber.d("dev_code check $fragment")
        try {
            when (fragment) {
                is KingMainFragment -> {
                    fragment.updateToolTip(true)
                    fragment.reloadFragmentCheckVideo()
                }

//                is M16MultimediaDetailFragment -> {
//                    fragment.runMedia()
//                }
//
//                is M08DetailNewsFragment -> {
//                    fragment.runMedia()
//                }

                else -> {
                    getFragmentInBackStack<KingMainFragment>(mFragmentManager)?.updateToolTip(false)
//                    PlayerController.getInstance(mActivity)?.pause("onBackStackChanged")
                }
            }
            updateToolTip(fragment)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun updateToolTip(fragment: Fragment?) {
        getFragmentInBackStack<KingMainFragment>(mFragmentManager)?.updateToolTip(fragment is KingMainFragment)
    }



    fun gotoGooglePlayForUpdateApp(context: Context?, url: String?) {
        try {
            if (context != null && !url.isNullOrEmpty()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }





}