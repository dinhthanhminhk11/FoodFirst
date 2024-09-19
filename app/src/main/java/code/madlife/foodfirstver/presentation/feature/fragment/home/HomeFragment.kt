package code.madlife.foodfirstver.presentation.feature.fragment.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import code.madlife.foodfirstver.MainViewModel
import code.madlife.foodfirstver.R
import code.madlife.foodfirstver.data.model.Category
import code.madlife.foodfirstver.databinding.FragmentHomeBinding
import code.madlife.foodfirstver.databinding.LayoutBannerViewHomeBinding
import code.madlife.foodfirstver.databinding.LayoutCategoryViewBinding
import code.madlife.foodfirstver.presentation.core.base.BaseFragment
import code.madlife.foodfirstver.presentation.core.base_adapter.CategoryAdapter
import code.madlife.foodfirstver.presentation.core.base_adapter.ImageAutoSliderAdapter
import code.madlife.foodfirstver.presentation.core.widget.autoimage.IndicatorView.animation.type.IndicatorAnimationType
import code.madlife.foodfirstver.presentation.core.widget.autoimage.SliderAnimations
import code.madlife.foodfirstver.presentation.core.widget.autoimage.SliderView

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var homeViewAdapter: HomeViewAdapter
    val imageListBanner = listOf(
        "https://i.pinimg.com/originals/e8/96/dd/e896dd155ee52df25df712044d3e4090.jpg",
        "https://i.pinimg.com/736x/1a/e3/2a/1ae32a19d958002c7fb2a99c9a53f012.jpg",
        "https://i.pinimg.com/originals/6c/62/54/6c6254f6a76076df97a104b8027bdadb.jpg",
        "https://i.pinimg.com/originals/ac/c4/99/acc4999915b7e75da7b55ece26ed5c3b.jpg",
        "https://lh5.googleusercontent.com/P9_u6qAHXZ9ptkzRdEUAqX2Fmtncx2k4uHnsDAw2BURaaQB-gihrjZ-cKY_fHFvk3pSqPP9lpLlrmYmaUplQE99enJ9PdRh0Ga_ZRtrwwI4MvY5xgp8PNOBinGNkUNmNxdSnPbIslJZHGc-TmyH0rCsfGB4Lqp5tHfnAQ6DY-9sXyRw27OiV3moMCg",
        "https://sctyenbai.gov.vn/sites/default/files/5128_hinh_2.png",
        "https://vieclamkinhdoanh.vn/blog/wp-content/uploads/2023/05/ban-hang-tren-shopee-food-topcv-3.jpg",
        "https://static-images.vnncdn.net/files/publish/2023/11/8/a111111-1301.jpg"
    )

    private lateinit var itemsCategory: List<Category>


    override fun initView() {
        itemsCategory = listOf(
            Category(1, getString(R.string.popular), R.drawable.fire),
            Category(2, getString(R.string.bread), R.drawable.bread),
            Category(3, getString(R.string.hot_pot), R.drawable.hot),
            Category(4, getString(R.string.nutrition), R.drawable.noodle),
            Category(5, getString(R.string.non_alcoholic_drinks), R.drawable.milk),
            Category(6, getString(R.string.rice), R.drawable.rice),
            Category(7, getString(R.string.snacks), R.drawable.snacks_popcorn),
            Category(8, getString(R.string.dessert), R.drawable.cream_cup_dessert),
            Category(9, getString(R.string.bread_asian), R.drawable.cream_day_ice_2),
            Category(10, getString(R.string.vegetarian_food), R.drawable.vegetarian),
            Category(11, getString(R.string.noodle), R.drawable.bowl_food_noodle),
            Category(12, getString(R.string.porridge), R.drawable.porridge),
            Category(13, getString(R.string.sea_food), R.drawable.lobster),
            Category(14, getString(R.string.all), R.drawable.all)
        )
        initRecyclerView()
    }

    override fun initObserver() {
        viewModel.addressData.observe(this) {
            binding.nameLocationYourSelf.text = it.toString()
        }
    }

    override fun getData() {
    }

    override fun onClick(v: View?) {

    }


    inner class HomeViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        private val typeBanner = 1
        private val typeCategory = 2
        private val typeDefault = 3
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return when (viewType) {
                typeBanner -> {
                    val view = LayoutBannerViewHomeBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                    BannerViewHolder(view)
                }

                typeCategory -> {
                    val view = LayoutCategoryViewBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                    CategoryViewHolder(view)
                }

                else -> throw IllegalArgumentException("Unknown view type")
            }
        }

        override fun getItemCount(): Int = 2

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when (holder.itemViewType) {
                typeBanner -> {
                    val topViewHolder = holder as BannerViewHolder
                    topViewHolder.bind()
                }

                typeCategory -> {
                    val categoryViewHolder = holder as CategoryViewHolder
                    categoryViewHolder.bind()
                }

            }
        }

        override fun getItemViewType(position: Int): Int {
            return when (position) {
                0 -> typeBanner
                1 -> typeCategory
                else -> throw IllegalArgumentException("Unknown position: $position")
            }
        }

        inner class BannerViewHolder(val binding: LayoutBannerViewHomeBinding) :
            RecyclerView.ViewHolder(binding.root) {
            @SuppressLint("SetTextI18n")
            fun bind() {
                val imageAutoAdapter = ImageAutoSliderAdapter(imageListBanner)
                binding.imageItem.setSliderAdapter(imageAutoAdapter)
                binding.imageItem.setIndicatorAnimation(IndicatorAnimationType.THIN_WORM)

                binding.imageItem.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
                binding.imageItem.autoCycleDirection =
                    SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
                binding.imageItem.indicatorSelectedColor = Color.WHITE
                binding.imageItem.indicatorUnselectedColor = Color.GRAY
                binding.imageItem.scrollTimeInSec = 4
                binding.imageItem.startAutoCycle()
            }
        }

        inner class CategoryViewHolder(val binding: LayoutCategoryViewBinding) :
            RecyclerView.ViewHolder(binding.root) {
            @SuppressLint("SetTextI18n")
            fun bind() {
                binding.listCategory.apply {
                    adapter = CategoryAdapter(itemsCategory)
                    layoutManager =
                        GridLayoutManager(activity, 2, GridLayoutManager.HORIZONTAL, false)
                }
            }
        }
    }

    private fun initRecyclerView() {
        homeViewAdapter = HomeViewAdapter()
        binding.listViewHome.apply {
            adapter = homeViewAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        }
    }
}