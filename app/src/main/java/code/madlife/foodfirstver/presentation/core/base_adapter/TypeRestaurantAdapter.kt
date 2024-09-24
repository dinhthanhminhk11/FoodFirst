package code.madlife.foodfirstver.presentation.core.base_adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import code.madlife.foodfirstver.core.common.loadImageCategory
import code.madlife.foodfirstver.data.model.ParentType
import code.madlife.foodfirstver.databinding.ItemRestaurantTypeBinding


class TypeRestaurantAdapter(
    private val itemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<TypeRestaurantAdapter.ViewHolder>() {
    private var itemList: List<ParentType> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newItemList: List<ParentType>) {
        itemList = newItemList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRestaurantTypeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    class ViewHolder(val binding: ItemRestaurantTypeBinding, private val listener: OnItemClickListener) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: ParentType) {
            loadImageCategory(binding.image.context, item.image, binding.image)
            binding.text.text = item.name
            binding.root.setOnClickListener {
                listener.onItemClick(item)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(category: ParentType)
    }
}