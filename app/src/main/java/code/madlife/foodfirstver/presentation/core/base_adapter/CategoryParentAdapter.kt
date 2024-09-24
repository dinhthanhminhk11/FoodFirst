package code.madlife.foodfirstver.presentation.core.base_adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import code.madlife.foodfirstver.R
import code.madlife.foodfirstver.core.common.loadImageCategory
import code.madlife.foodfirstver.data.model.response.CategoryResponse
import code.madlife.foodfirstver.databinding.ItemCategoryParentBinding

class CategoryParentAdapter(
    private val itemList: List<CategoryResponse>,
    private val itemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<CategoryParentAdapter.ViewHolder>() {
    private var rowIndex = 0
    private var selected = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCategoryParentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position], position)
    }

    override fun getItemCount(): Int = itemList.size

    inner class ViewHolder(
        val binding: ItemCategoryParentBinding,
        private val listener: OnItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: CategoryResponse, position: Int) {
            binding.text.text = item.name

            if (selected) {
                if (position == 0) {
                    listener.onItemClick(item)
                    loadImageCategory(binding.image.context, item.imageUnselect, binding.image)
                    binding.text.setTextColor(binding.root.resources.getColor(R.color.color_orange))
                    binding.root.setBackgroundColor(Color.WHITE)
                }
                selected = false
            } else {
                if (rowIndex == position) {
                    loadImageCategory(binding.image.context, item.imageUnselect, binding.image)
                    binding.text.setTextColor(binding.root.resources.getColor(R.color.color_orange))
                    binding.root.setBackgroundColor(Color.WHITE)
                } else {
                    loadImageCategory(binding.image.context, item.image, binding.image)
                    binding.text.setTextColor(Color.GRAY)
                    binding.root.setBackgroundColor(binding.root.resources.getColor(R.color.color_F7F7F7))
                }
            }



            binding.root.setOnClickListener {
                rowIndex = position
                listener.onItemClick(item)
                notifyDataSetChanged()
            }
        }

        private fun setItemSelected() {

        }

        private fun setItemUnselected() {

        }
    }

    interface OnItemClickListener {
        fun onItemClick(category: CategoryResponse)
    }
}