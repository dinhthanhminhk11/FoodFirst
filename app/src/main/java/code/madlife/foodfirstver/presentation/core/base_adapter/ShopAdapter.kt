package code.madlife.foodfirstver.presentation.core.base_adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import code.madlife.foodfirstver.core.common.loadImage
import code.madlife.foodfirstver.data.model.Shop
import code.madlife.foodfirstver.databinding.ItemShopBinding

class ShopAdapter(private val itemList: List<Shop>) :
    RecyclerView.Adapter<ShopAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemShopBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size


    class ViewHolder(val binding: ItemShopBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Shop) {
            loadImage(binding.image.context, item.avatar, binding.image)
            binding.title.text = item.name
        }
    }
}