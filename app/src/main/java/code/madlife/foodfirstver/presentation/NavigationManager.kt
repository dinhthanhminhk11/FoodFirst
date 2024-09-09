package code.madlife.foodfirstver.presentation

import android.annotation.SuppressLint
import android.app.Activity
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.OnBackStackChangedListener

class NavigationManager : OnBackStackChangedListener {
    protected var mActivity: Activity? = null
    lateinit var mFragmentManager: FragmentManager
    private var mContentId: Int? = null

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
     *  flag mark the Navigation is created on Fragment class
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
//            getFragmentInBackStack<>()
        }
    }

    fun getCurrentFragment(): Fragment? {
        return try {
            mContentId?.let { mFragmentManager.findFragmentById(it) }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun onBackStackChanged() {

    }
}