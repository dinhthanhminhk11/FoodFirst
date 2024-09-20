package code.madlife.foodfirstver.presentation.core.base_adapter

import androidx.recyclerview.widget.RecyclerView
import code.madlife.foodfirstver.databinding.ItemEmptyBinding
import timber.log.Timber

class EmptyHolder(val binding: ItemEmptyBinding) : RecyclerView.ViewHolder(binding.root) {
    init {
        Timber.e("EmptyHolder")
    }
}