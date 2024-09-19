package code.madlife.foodfirstver.presentation.core.base_adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import code.madlife.foodfirstver.R
import code.madlife.foodfirstver.core.common.loadImage
import code.madlife.foodfirstver.presentation.core.widget.autoimage.SliderViewAdapter

class ImageAutoSliderAdapter(private val listImage: List<String>) :
    SliderViewAdapter<ImageAutoSliderAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.slider_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(viewHolder: Holder, position: Int) {
        loadImage(viewHolder.imageView.context, listImage[position], viewHolder.imageView)
    }

    override fun getCount(): Int {
        return listImage.size
    }

    inner class Holder(itemView: View) : ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image_view)
    }
}