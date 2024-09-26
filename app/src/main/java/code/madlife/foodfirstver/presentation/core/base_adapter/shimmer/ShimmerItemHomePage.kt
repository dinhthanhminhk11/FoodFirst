package code.madlife.foodfirstver.presentation.core.base_adapter.shimmer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import code.madlife.foodfirstver.R
import com.facebook.shimmer.ShimmerFrameLayout

class ShimmerItemHomePage(private val itemCount: Int) :
    RecyclerView.Adapter<ShimmerItemHomePage.ShimmerViewHolder>() {
    inner class ShimmerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShimmerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.layout_shimmer_item_post_home, parent, false)
        return ShimmerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ShimmerViewHolder, position: Int) {
        val shimmerLayout = holder.itemView.findViewById<ShimmerFrameLayout>(R.id.shimmerLayout)
        shimmerLayout.startShimmer()
    }

    override fun getItemCount(): Int = itemCount
}