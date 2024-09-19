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

    val itemsCategory = listOf(
        Category(1, "Title1", "https://cdn-icons-png.flaticon.com/128/159/159599.png"),
        Category(2, "Title1", "https://cdn-icons-png.flaticon.com/128/3159/3159461.png"),
        Category(3, "Title1", "https://cdn-icons-png.flaticon.com/128/1530/1530297.png"),
        Category(4, "Title1", "https://cdn-icons-png.flaticon.com/128/708/708949.png"),
        Category(5 ,"Title1", "https://cdn-icons-png.flaticon.com/128/1066/1066276.png"),
        Category(6, "Title1", "https://cdn-icons-png.flaticon.com/128/3333/3333833.png"),
        Category(7, "Title1", "https://cdn-icons-png.flaticon.com/128/395/395598.png"),
        Category(8, "Title1", "https://cdn-icons-png.flaticon.com/128/3049/3049334.png"),
        Category(8, "Title1", "https://cdn-icons-png.flaticon.com/128/3049/3049334.png"),
        Category(8, "Title1", "https://cdn-icons-png.flaticon.com/128/3049/3049334.png"),
        Category(8, "Title1", "https://cdn-icons-png.flaticon.com/128/3049/3049334.png"),
        Category(8, "Title1", "https://cdn-icons-png.flaticon.com/128/3049/3049334.png"),
        Category(8, "Title1", "https://cdn-icons-png.flaticon.com/128/3049/3049334.png"),
        Category(8, "Title1", "https://cdn-icons-png.flaticon.com/128/3049/3049334.png"),
        Category(8, "Title1", "https://cdn-icons-png.flaticon.com/128/3049/3049334.png")
    )

    override fun initView() {
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