package code.madlife.foodfirstver.presentation.feature.fragment.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.location.Location
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import code.madlife.foodfirstver.MainViewModel
import code.madlife.foodfirstver.R
import code.madlife.foodfirstver.core.common.loadImage
import code.madlife.foodfirstver.data.model.Category
import code.madlife.foodfirstver.data.model.ItemRestaurantHome
import code.madlife.foodfirstver.data.model.Shop
import code.madlife.foodfirstver.data.model.request.home.HomeRequest
import code.madlife.foodfirstver.databinding.FragmentHomeBinding
import code.madlife.foodfirstver.databinding.ItemEmptyBinding
import code.madlife.foodfirstver.databinding.LayoutBannerViewHomeBinding
import code.madlife.foodfirstver.databinding.LayoutCategoryViewBinding
import code.madlife.foodfirstver.databinding.LayoutDefaultHomeBinding
import code.madlife.foodfirstver.databinding.LayoutHeaderNearFromYourBinding
import code.madlife.foodfirstver.databinding.LayoutItemShopNearByBinding
import code.madlife.foodfirstver.presentation.NavigationManager
import code.madlife.foodfirstver.presentation.core.base.BaseFragment
import code.madlife.foodfirstver.presentation.core.base_adapter.CategoryAdapter
import code.madlife.foodfirstver.presentation.core.base_adapter.EmptyHolder
import code.madlife.foodfirstver.presentation.core.base_adapter.ImageAutoSliderAdapter
import code.madlife.foodfirstver.presentation.core.base_adapter.OnHeaderSelectionChangedListener
import code.madlife.foodfirstver.presentation.core.base_adapter.ShopAdapter
import code.madlife.foodfirstver.presentation.core.base_adapter.StickyHeaderItemDecoration
import code.madlife.foodfirstver.presentation.core.widget.autoimage.IndicatorView.animation.type.IndicatorAnimationType
import code.madlife.foodfirstver.presentation.core.widget.autoimage.SliderAnimations
import code.madlife.foodfirstver.presentation.core.widget.autoimage.SliderView
import code.madlife.foodfirstver.presentation.feature.fragment.category.CategoryFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    OnHeaderSelectionChangedListener {
    private val viewModel: MainViewModel by activityViewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var homeViewAdapter: HomeViewAdapter
    private var typeLoadRestaurant = "nearBy"
    private var typeLoadRestaurantSell = "nearByAndHighPoints"
    private lateinit var locationYourSelf: Location
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
            Category(4, getString(R.string.noodle_lunch, getTimeOfDay()), R.drawable.noodle),
            Category(6, getString(R.string.lunch, getTimeOfDay()), R.drawable.bowl_food_noodle),
            Category(6, getString(R.string.rice), R.drawable.rice),
            Category(5, getString(R.string.milk_tea), R.drawable.milk),
            Category(10, getString(R.string.noodle), R.drawable.porridge),
            Category(10, getString(R.string.partner), R.drawable.vip_svgrepo_com),
            Category(10, getString(R.string.vegetarian_food), R.drawable.vegetarian),
            Category(14, getString(R.string.all), R.drawable.all)
        )

        initRecyclerView()
    }

    override fun initObserver() {
        viewModel.addressData.observe(this) {
            binding.nameLocationYourSelf.text = it.toString()
        }

        viewModel.locationMutableLiveData.observe(this) {
            locationYourSelf = it
            homeViewModel.getListPostHomeType(
                HomeRequest(
                    it.longitude,
                    it.latitude,
                    typeLoadRestaurant
                )
            )
        }

        homeViewModel.listPostHomeMutableLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is RestaurantState.Fail -> {
                }

                RestaurantState.Init -> {

                }

                RestaurantState.Loading -> {
                }

                is RestaurantState.Success -> {
                    val serverData = it.data as List<ItemRestaurantHome>
                    homeViewAdapter.updateItems(serverData)
                }
            }
        }

        homeViewModel.listPostHomeTypeMutableLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is RestaurantState.Fail -> {
                }

                RestaurantState.Init -> {

                }

                RestaurantState.Loading -> {
                }

                is RestaurantState.Success -> {
                    val serverData = it.data as List<Shop>
                    homeViewAdapter.updateItemsRestaurantType(serverData)
                }
            }
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
        private val typeFooterHeader = 4
        private val typeItemShopNearBy = 5

        private var itemsItemRestaurantHome: List<ItemRestaurantHome> = listOf()
        private var itemListRestaurantType: List<Shop> = listOf()
        var selectedTextViewId: Int? = R.id.nearBy

        @SuppressLint("NotifyDataSetChanged")
        fun updateItems(newItems: List<ItemRestaurantHome>) {
            itemsItemRestaurantHome = newItems
            notifyDataSetChanged()
        }

        @SuppressLint("NotifyDataSetChanged")
        fun updateItemsRestaurantType(newItems: List<Shop>) {
            itemListRestaurantType = newItems
            notifyDataSetChanged()
        }

        fun getItemHome(): Int {
            return itemsItemRestaurantHome.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val inflate = LayoutInflater.from(context)
            return when (viewType) {
                typeBanner -> {
                    BannerViewHolder(
                        LayoutBannerViewHomeBinding.inflate(
                            inflate, parent, false
                        )
                    )
                }

                typeCategory -> {
                    CategoryViewHolder(
                        LayoutCategoryViewBinding.inflate(
                            inflate, parent, false
                        )
                    )
                }

                typeDefault -> {
                    DefaultItemViewHolder(
                        LayoutDefaultHomeBinding.inflate(
                            inflate, parent, false
                        )
                    )
                }

                typeFooterHeader -> {
                    FooterHeaderViewHolder(
                        LayoutHeaderNearFromYourBinding.inflate(
                            inflate, parent, false
                        )
                    )
                }

                typeItemShopNearBy -> {
                    NearByItemViewHolder(
                        LayoutItemShopNearByBinding.inflate(
                            inflate, parent, false
                        )
                    )
                }

                else -> EmptyHolder(ItemEmptyBinding.inflate(inflate, parent, false))
            }
        }

        override fun getItemCount(): Int =
            2 + itemsItemRestaurantHome.size + 1 + itemListRestaurantType.size

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

                typeDefault -> {
                    val defaultHomeBinding = holder as DefaultItemViewHolder
                    val item = itemsItemRestaurantHome[position - 2]
                    defaultHomeBinding.bind(item)
                }

                typeFooterHeader -> {
                    val footerViewHolder = holder as FooterHeaderViewHolder
                    footerViewHolder.bind()
                }

                typeItemShopNearBy -> {
                    val fakeViewHolder = holder as NearByItemViewHolder
                    val fakeItem =
                        itemListRestaurantType[position - (2 + itemsItemRestaurantHome.size + 1)]
                    fakeViewHolder.bind(fakeItem)
                }
            }
        }

        override fun getItemViewType(position: Int): Int {
            return when (position) {
                0 -> typeBanner
                1 -> typeCategory
                2 + itemsItemRestaurantHome.size -> typeFooterHeader
                in (2 until 2 + itemsItemRestaurantHome.size) -> typeDefault
                else -> typeItemShopNearBy
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
                    adapter = CategoryAdapter(
                        itemsCategory,
                        object : CategoryAdapter.OnItemClickListener {
                            override fun onItemClick(category: Category) {
                                NavigationManager.getInstance().openFragment(CategoryFragment())
                            }
                        })
                    layoutManager =
                        GridLayoutManager(activity, 2, GridLayoutManager.HORIZONTAL, false)
                }
            }
        }

        inner class DefaultItemViewHolder(val binding: LayoutDefaultHomeBinding) :
            RecyclerView.ViewHolder(binding.root) {
            @SuppressLint("SetTextI18n")
            fun bind(item: ItemRestaurantHome) {
                binding.title.text = item.title
                binding.content.text = item.content
                val restaurantList = item.restaurants ?: listOf()
                binding.recyclerView.apply {
                    adapter = ShopAdapter(restaurantList)
                    layoutManager =
                        LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                }

                binding.seeMore.setOnClickListener {

                }
            }
        }

        inner class FooterHeaderViewHolder(val binding: LayoutHeaderNearFromYourBinding) :
            RecyclerView.ViewHolder(binding.root) {

            @SuppressLint("SetTextI18n")
            fun bind() {
                setClickListener()
            }

            private fun setClickListener() {
                val textViewList = listOf(binding.nearBy, binding.sellWell, binding.evaluate)
                for (textView in textViewList) {
                    textView.setOnClickListener {
                        if (selectedTextViewId == it.id) {
                            return@setOnClickListener
                        }
                        selectedTextViewId = it.id
                        for (tv in textViewList) {
                            if (tv == it) {
                                tv.setTextColor(
                                    ContextCompat.getColor(
                                        binding.root.context,
                                        R.color.color_orange
                                    )
                                )
                                tv.setTypeface(null, Typeface.BOLD)
                            } else {
                                tv.setTextColor(
                                    ContextCompat.getColor(
                                        binding.root.context,
                                        R.color.gray
                                    )
                                )
                                tv.setTypeface(null, Typeface.NORMAL)
                            }
                        }
                        checkSelectedTextView()
                    }
                }
            }

            private fun checkSelectedTextView() {
                when (selectedTextViewId) {
                    R.id.nearBy -> {
                        homeViewModel.getListPostHomeType(
                            HomeRequest(
                                locationYourSelf.longitude,
                                locationYourSelf.latitude,
                                typeLoadRestaurant
                            )
                        )
                    }

                    R.id.sell_well -> {
                        homeViewModel.getListPostHomeType(
                            HomeRequest(
                                locationYourSelf.longitude,
                                locationYourSelf.latitude,
                                typeLoadRestaurantSell
                            )
                        )
                    }

                    R.id.evaluate -> {
                        homeViewModel.getListPostHomeType(
                            HomeRequest(
                                locationYourSelf.longitude,
                                locationYourSelf.latitude,
                                typeLoadRestaurantSell
                            )
                        )
                    }

                    else -> {
                    }
                }
            }
        }

        inner class NearByItemViewHolder(val binding: LayoutItemShopNearByBinding) :
            RecyclerView.ViewHolder(binding.root) {
            @SuppressLint("SetTextI18n")
            fun bind(item: Shop) {
                binding.name.text = item.name + " - " + item.title
                binding.address.text = item.address
                binding.ratting.text = item.points.toString()
                binding.distance.text = formatDistance(item.distanceKM)
                binding.time.text = binding.root.context.resources.getString(
                    R.string.timeSetUp,
                    item.timeSetUp.toString()
                )
                loadImage(binding.image.context, item.avatar, binding.image)
                binding.nameCategory.text = item.typeRestaurant[0].name
                if (item.typeRestaurant.size > 1) {
                    binding.nameCategory1.visibility = View.VISIBLE
                    binding.nameCategory1.text = item.typeRestaurant[1].name
                } else {
                    binding.nameCategory1.visibility = View.GONE
                }
            }
        }
    }

    fun formatDistance(distanceKM: Double): String {
        return if (distanceKM < 1) {
            "${(distanceKM * 1000).toInt()} m"
        } else {
            String.format("%.2f km", distanceKM)
        }
    }

    private fun initRecyclerView() {
        homeViewAdapter = HomeViewAdapter()
        binding.listViewHome.apply {
            adapter = homeViewAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        }
        binding.listViewHome.addItemDecoration(StickyHeaderItemDecoration(homeViewAdapter, this))
    }

    fun getTimeOfDay(): String {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)

        return when (hour) {
            in 5..9 -> "Sáng"
            in 9..13 -> "Trưa"
            in 14..17 -> "Chiều"
            in 18..20 -> "Tối"
            else -> "Đêm"
        }
    }

    override fun onHeaderSelectionChanged(selectedTextViewId: Int) {

    }
}