package ru.turizmryazan.ui.wheretostay

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen
import com.ethanhua.skeleton.Skeleton
import com.google.android.material.appbar.CollapsingToolbarLayout
import ru.turizmryazan.R
import ru.turizmryazan.adapters.IRecyclerClick
import ru.turizmryazan.adapters.wheretostay.WhereToStayRecyclerAdapter
import ru.turizmryazan.base.BaseFragment
import ru.turizmryazan.model.models.Hotel
import ru.turizmryazan.model.models.Location
import ru.turizmryazan.model.models.Type
import ru.turizmryazan.ui.main.MainViewModel

class WhereToStayFragment : BaseFragment(), IRecyclerClick {

    private lateinit var mViewModel: WhereToStayDetailViewModel
    private lateinit var viewModel: MainViewModel
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mRecyclerAdapter: WhereToStayRecyclerAdapter
    private lateinit var skeletonScreen: RecyclerViewSkeletonScreen
    private lateinit var ivFilter: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        iMainComponent?.hideBottomNavigation()
        iMainComponent?.setTransparentStatusBar()
        return inflater.inflate(R.layout.fragment_where_to_stay, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        listeners()
        observeMutable()
        loadData()

        setToolbarTitle(getString(R.string.where_to_stay))
    }

    fun initViews(view: View) {
        ivFilter = view.findViewById(R.id.iv_filter_default_toolbar_menu)
        mRecyclerView = view.findViewById(R.id.rv_fragment_where_to_stay)
        mRecyclerAdapter = WhereToStayRecyclerAdapter(this)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.adapter = mRecyclerAdapter
        mRecyclerView.addItemDecoration(decorationList)

        skeletonScreen = Skeleton.bind(mRecyclerView)
            .shimmer(true)
            .count(5)
            .color(R.color.white)
            .adapter(mRecyclerAdapter)
            .load(R.layout.item_recycler_skeleton)
            .show()
    }

    private fun listeners() {
        ivFilter.setOnClickListener {
            onClickFilter()
        }
    }

    private fun observeMutable() {
        viewModel.hotels.observe(viewLifecycleOwner, Observer { hotels ->
            onHotels(hotels)
        })
    }

    private fun onClickFilter() {
        iNavigate?.openWhereToStayFilter()
    }

    override fun onSwipeRefresh() {
        viewModel.loadHotels()
        skeletonScreen.show()
    }

    private fun onHotels(hotels: MutableList<Hotel>?) {
        hotels?.let {
            val location: Location? = viewModel.hotelsFilter.value?.location
            val type: Type? = viewModel.hotelsFilter.value?.type

            if ((location == null) && (type == null)) {
                mRecyclerAdapter.setData(it)
            } else {
                var filteredHotels = it.toList()
                location?.let {
                    filteredHotels = filteredHotels.filter { hotel -> hotel.location?.id == it.id }
                }
                type?.let {
                    filteredHotels = filteredHotels.filter { hotel -> hotel.type?.id == it.id }
                }
                mRecyclerAdapter.setData(filteredHotels.toMutableList())
            }
            skeletonScreen.hide()
            swipeRefreshLayout?.isRefreshing = false
        }
    }

    private fun loadData() {
        if (viewModel.hotels.value == null) {
            viewModel.loadHotels()
            skeletonScreen.show()
        }
    }

    override fun onRecyclerItemClick(obj: Any) {
        if(obj is Hotel){
            iNavigate?.openWhereToStayDetail(obj.id)
        }
    }

    private val decorationList: RecyclerView.ItemDecoration = object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            val position = parent.getChildAdapterPosition(view)

            when(position){
                0 -> view.background = resources.getDrawable(R.drawable.to_stay_item_backhround)
                1 -> view.background = resources.getDrawable(R.drawable.to_stay_item_background_double)
                else ->  view.background = resources.getDrawable(R.color.main_background)
            }

            outRect.set(0,
                0,
                0,
                if (position == parent.adapter!!.itemCount - 1) resources.getDimensionPixelSize(R.dimen._16sdp) else 0 )
        }
    }
}