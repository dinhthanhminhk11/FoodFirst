package code.madlife.foodfirstver.core.common

import android.provider.Settings.Global.getString
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import code.madlife.foodfirstver.R
import code.madlife.foodfirstver.presentation.core.widget.toast.CookieBar
import com.google.gson.Gson

fun View?.show() {
    this?.visibility = View.VISIBLE
}

fun View?.hide() {
    this?.visibility = View.GONE
}

fun Any?.toJson(): String = if (this == null) "null" else Gson().toJson(this)

fun ViewPager2.reduceDragSensitivity(f: Int = 3) {
    val recyclerViewField = ViewPager2::class.java.getDeclaredField("mRecyclerView")
    recyclerViewField.isAccessible = true
    val recyclerView = recyclerViewField.get(this) as RecyclerView
    val touchSlopField = RecyclerView::class.java.getDeclaredField("mTouchSlop")
    touchSlopField.isAccessible = true
    val touchSlop = touchSlopField.get(recyclerView) as Int
    touchSlopField.set(recyclerView, touchSlop * f)       // "8" was obtained experimentally
}

fun showToastError(
    activity: FragmentActivity?,
    title: String? = activity?.getString(R.string.Notify),
    content: String
) {
    CookieBar.build(activity).setTitle(title)
        .setMessage(content)
        .setIcon(R.drawable.ic_warning_icon_check).setTitleColor(R.color.color_black)
        .setMessageColor(R.color.color_black).setDuration(3000)
        .setBackgroundRes(R.drawable.background_toast)
        .setCookiePosition(CookieBar.BOTTOM).show()
}

fun showToastSuccess(
    activity: FragmentActivity,
    title: String? = activity?.getString(R.string.Notify),
    content: String
) {
    CookieBar.build(activity)
        .setTitle(title)
        .setMessage(content)
        .setIcon(R.drawable.ic_complete_order).setTitleColor(R.color.color_black)
        .setMessageColor(R.color.color_black).setDuration(3000)
        .setBackgroundRes(R.drawable.background_toast)
        .setCookiePosition(CookieBar.BOTTOM).show()
}