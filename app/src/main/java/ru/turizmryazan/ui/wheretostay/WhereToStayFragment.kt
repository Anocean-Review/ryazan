package ru.turizmryazan.ui.wheretostay

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen
import com.ethanhua.skeleton.Skeleton
import ru.turizmryazan.R
import ru.turizmryazan.adapters.IRecyclerClick
import ru.turizmryazan.adapters.WhereToStayRecyclerAdapter
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
        mViewModel = ViewModelProvider(this).get(WhereToStayDetailViewModel::class.java)
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

    private fun onHotels(hotels: MutableList<Hotel>?) {
        hotels?.let {
            val location: Location? = viewModel.hotelsFilter.value?.locationHotel
            val type: Type? = viewModel.hotelsFilter.value?.typeHotel

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
        }
    }

    private fun loadData() {
        if (viewModel.hotels.value == null) {
            viewModel.loadHotels()
            viewModel.loadDictionary()
            skeletonScreen.show()
        }
    }

    override fun onRecyclerItemClick(obj: Any) {
        if(obj is Hotel){
            iNavigate?.openWhereToStayDetail(obj.id)
        }
    }
}