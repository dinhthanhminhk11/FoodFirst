package code.madlife.foodfirstver.presentation.feature.fragment.category

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import code.madlife.foodfirstver.R
import code.madlife.foodfirstver.core.common.Constants
import code.madlife.foodfirstver.core.common.showToastError
import code.madlife.foodfirstver.data.model.Category
import code.madlife.foodfirstver.data.model.response.CategoryResponse
import code.madlife.foodfirstver.databinding.FragmentCategoryBinding
import code.madlife.foodfirstver.databinding.FragmentFavoriteBinding
import code.madlife.foodfirstver.presentation.NavigationManager
import code.madlife.foodfirstver.presentation.core.base.BaseFragment
import code.madlife.foodfirstver.presentation.core.base_adapter.CategoryAdapter
import code.madlife.foodfirstver.presentation.core.base_adapter.TypeRestaurantAdapter
import code.madlife.foodfirstver.presentation.feature.fragment.user.login.AuthState
import code.madlife.foodfirstver.presentation.feature.fragment.user.otp.OtpFragment
import code.madlife.foodfirstver.presentation.feature.fragment.user.register.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@Suppress("UNCHECKED_CAST")
@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>(FragmentCategoryBinding::inflate) {

    private val viewModel: CategoryViewModel by viewModels()

    override fun initView() {
        binding.toolbar.setNavigationOnClickListener {
            NavigationManager.getInstance().popBackStack()
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
                        adapter = TypeRestaurantAdapter(
                            it.data as List<CategoryResponse>,
                            object : TypeRestaurantAdapter.OnItemClickListener {
                                override fun onItemClick(category: CategoryResponse) {
//                                    NavigationManager.getInstance().openFragment(CategoryFragment())
                                }
                            })
                        layoutManager =
                            LinearLayoutManager(activity,  GridLayoutManager.VERTICAL, false)
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