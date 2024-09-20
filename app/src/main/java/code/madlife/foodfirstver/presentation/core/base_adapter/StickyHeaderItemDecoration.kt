package code.madlife.foodfirstver.presentation.core.base_adapter

import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import code.madlife.foodfirstver.databinding.LayoutHeaderNearFromYourBinding
import code.madlife.foodfirstver.presentation.feature.fragment.home.HomeFragment

class StickyHeaderItemDecoration(val adapter: HomeFragment.HomeViewAdapter) :
    RecyclerView.ItemDecoration() {

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val child = parent.getChildAt(0) ?: return
        val position = parent.getChildAdapterPosition(child)

        if (position >= adapter.getItemHome() + 2) {
            val headerView = createHeaderView(parent)
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

    private fun drawHeader(c: Canvas, headerView: View) {
        c.save()
        c.translate(0f, 0f)
        headerView.draw(c)
        c.restore()
    }
}