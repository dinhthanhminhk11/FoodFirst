package code.madlife.foodfirstver.core.common

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import android.provider.Settings.Global.getString
import android.text.Spannable
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import code.madlife.foodfirstver.R
import code.madlife.foodfirstver.presentation.core.widget.toast.CookieBar
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
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

fun loadImage(context: Context, imageUrl: String?, imageView: ImageView) {
    val options = RequestOptions()
        .centerCrop()
        .placeholder(R.drawable.imageloading)
        .error(R.drawable.imageerror)

    Glide.with(context)
        .load(imageUrl)
        .apply(options)
        .into(imageView)
}

fun loadDrawable(context: Context, drawableResId: Int, imageView: ImageView) {
    val drawable: Drawable? = ContextCompat.getDrawable(context, drawableResId)
    drawable?.let {
        imageView.setImageDrawable(it)
    }
}

fun TextView.setUnderlinedText(text: CharSequence) {
    val spannableString = SpannableString(text)
    spannableString.setSpan(UnderlineSpan(), 0, text.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    this.text = spannableString
}