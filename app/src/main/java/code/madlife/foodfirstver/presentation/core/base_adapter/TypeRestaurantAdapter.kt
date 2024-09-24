package code.madlife.foodfirstver.presentation.core.base_adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import code.madlife.foodfirstver.core.common.loadImage
import code.madlife.foodfirstver.data.CategoryRepository
import code.madlife.foodfirstver.data.model.response.CategoryResponse
import code.madlife.foodfirstver.databinding.ItemCategoryBinding


class TypeRestaurantAdapter(
    private val itemList: List<CategoryResponse>,
    private val itemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<TypeRestaurantAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    class ViewHolder(val binding: ItemCategoryBinding, private val listener: OnItemClickListener) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: CategoryResponse) {
            loadImage(binding.image.context, item.image, binding.image)
            binding.text.text = item.name
            binding.root.setOnClickListener {
                listener.onItemClick(item)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(category: CategoryResponse)
    }
}