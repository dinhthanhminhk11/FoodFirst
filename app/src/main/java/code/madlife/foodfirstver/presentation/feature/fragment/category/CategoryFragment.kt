package code.madlife.foodfirstver.presentation.feature.fragment.category

import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import code.madlife.foodfirstver.R
import code.madlife.foodfirstver.data.model.ParentType
import code.madlife.foodfirstver.data.model.response.CategoryResponse
import code.madlife.foodfirstver.databinding.FragmentCategoryBinding
import code.madlife.foodfirstver.presentation.NavigationManager
import code.madlife.foodfirstver.presentation.core.base.BaseFragment
import code.madlife.foodfirstver.presentation.core.base_adapter.CategoryParentAdapter
import code.madlife.foodfirstver.presentation.core.base_adapter.TypeRestaurantAdapter
import dagger.hilt.android.AndroidEntryPoint

@Suppress("UNCHECKED_CAST")
@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>(FragmentCategoryBinding::inflate) {

    private val viewModel: CategoryViewModel by viewModels()
    private lateinit var adapterTypeRestaurantAdapter: TypeRestaurantAdapter
    private lateinit var layoutAnimationController : LayoutAnimationController
    override fun initView() {
        binding.toolbar.setNavigationOnClickListener {
            NavigationManager.getInstance().popBackStack()
        }
        adapterTypeRestaurantAdapter =
            TypeRestaurantAdapter(object : TypeRestaurantAdapter.OnItemClickListener {
                override fun onItemClick(category: ParentType) {

                }
            })

        layoutAnimationController = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fade_in)
        binding.listRestaurantType.layoutAnimation = layoutAnimationController

        binding.listRestaurantType.apply {
            adapter = adapterTypeRestaurantAdapter
            layoutManager =
                GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
        }

    }

    override fun initObserver() {
        viewModel.categoryMutableLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is CategoryState.Fail -> {

                }

                CategoryState.Init -> {

                }

                CategoryState.Loading -> {

                }

                is CategoryState.Success -> {
                    binding.listParentCategory.apply {
                        adapter = CategoryParentAdapter(
                            it.data as List<CategoryResponse>,
                            object : CategoryParentAdapter.OnItemClickListener {
                                override fun onItemClick(category: CategoryResponse) {
                                    adapterTypeRestaurantAdapter.setData(category.parentType)
                                    val layoutAnimationController = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fade_in2)
                                    binding.listRestaurantType.layoutAnimation = layoutAnimationController
                                }
                            })
                        layoutManager =
                            LinearLayoutManager(activity, GridLayoutManager.VERTICAL, false)
                    }
                }
            }
        }
    }

    override fun getData() {
    }

    override fun onClick(v: View?) {
    }
}