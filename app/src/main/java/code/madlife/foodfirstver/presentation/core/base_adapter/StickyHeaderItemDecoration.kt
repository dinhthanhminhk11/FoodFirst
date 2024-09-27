package code.madlife.foodfirstver.presentation.core.base_adapter

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import code.madlife.foodfirstver.R
import code.madlife.foodfirstver.databinding.LayoutHeaderNearFromYourBinding
import code.madlife.foodfirstver.presentation.feature.fragment.home.HomeFragment

class StickyHeaderItemDecoration(
    val adapter: HomeFragment.HomeViewAdapter,
    private val listener: OnHeaderSelectionChangedListener
) : RecyclerView.ItemDecoration() {

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val child = parent.getChildAt(0) ?: return
        val position = parent.getChildAdapterPosition(child)

        if (position >= adapter.getItemHome() + 2) {
            val headerView = createHeaderView(parent)
            updateHeaderBasedOnSelection(headerView)
            setClickListener(headerView)
            drawHeader(c, headerView)
        }
    }

    private fun createHeaderView(parent: RecyclerView): View {
        val binding = LayoutHeaderNearFromYourBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        val headerView = binding.root
        headerView.measure(
            View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        headerView.layout(0, 0, headerView.measuredWidth, headerView.measuredHeight)

        return headerView
    }

    private fun updateHeaderBasedOnSelection(headerView: View) {
        val binding = LayoutHeaderNearFromYourBinding.bind(headerView)
        val selectedTextViewId = adapter.selectedTextViewId
        val textViewList = listOf(binding.nearBy, binding.sellWell, binding.evaluate)
        for (tv in textViewList) {
            if (tv.id == selectedTextViewId) {
                tv.setTextColor(
                    ContextCompat.getColor(binding.root.context, R.color.color_orange)
                )
                tv.setTypeface(null, Typeface.BOLD)
            } else {
                tv.setTextColor(
                    ContextCompat.getColor(binding.root.context, R.color.gray)
                )
                tv.setTypeface(null, Typeface.NORMAL)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setClickListener(headerView: View) {
        val binding = LayoutHeaderNearFromYourBinding.bind(headerView)
        val textViewList = listOf(binding.nearBy, binding.sellWell, binding.evaluate)

        for (textView in textViewList) {
            textView.setOnClickListener {
                adapter.selectedTextViewId = it.id
                updateHeaderBasedOnSelection(headerView)
                listener.onHeaderSelectionChanged(it.id)
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun drawHeader(c: Canvas, headerView: View) {
        c.save()
        c.translate(0f, 0f)
        headerView.draw(c)
        c.restore()
    }
}
