package ru.turizmryazan.ui.wheretogo

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen
import com.ethanhua.skeleton.Skeleton
import ru.turizmryazan.R
import ru.turizmryazan.adapters.IRecyclerClick
import ru.turizmryazan.adapters.wheretogo.WhereToGoRecyclerAdapter
import ru.turizmryazan.base.BaseFragment
import ru.turizmryazan.model.models.Attraction
import ru.turizmryazan.model.models.Hotel
import ru.turizmryazan.model.models.Location
import ru.turizmryazan.model.models.Type
import ru.turizmryazan.ui.main.MainViewModel
import ru.turizmryazan.utils.Constants

class WhereToGoFragment:BaseFragment(), IRecyclerClick {

    private lateinit var viewModel: MainViewModel
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mRecyclerAdapter: WhereToGoRecyclerAdapter
    private lateinit var skeletonScreen: RecyclerViewSkeletonScreen
    private lateinit var ivFilter: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        iMainComponent?.hideBottomNavigation()
        iMainComponent?.setTransparentStatusBar()
        return inflater.inflate(R.layout.fragment_where_to_go, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        listeners()
        observeMutable()
        loadData()

        setToolbarTitle(getString(R.string.where_to_go))
    }

    private fun initViews(view: View) {
        ivFilter = view.findViewById(R.id.iv_filter_default_toolbar_menu)
        mRecyclerView = view.findViewById(R.id.rv_fragment_where_to_go)
        mRecyclerAdapter = WhereToGoRecyclerAdapter(this)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.adapter = mRecyclerAdapter
        mRecyclerView.addItemDecoration(decorationList)

        skeletonScreen = Skeleton.bind(mRecyclerView)
            .shimmer(true)
            .count(2)
            .color(R.color.white)
            .adapter(mRecyclerAdapter)
            .load(R.layout.item_recycler_go_skeleton)
            .show()
    }

    private fun listeners() {
        ivFilter.setOnClickListener {
            onClickFilter()
        }
    }

    private fun observeMutable() {
        viewModel.attractions.observe(viewLifecycleOwner, Observer { attractions ->
            onAttractions(attractions)
        })
    }

    private fun onClickFilter() {
        iNavigate?.openWhereToGoFilter()
    }

    private fun loadData() {
        if (viewModel.attractions.value == null) {
            viewModel.loadAttractions()
            skeletonScreen.show()
        }
    }

    override fun onSwipeRefresh() {
        viewModel.loadAttractions()
        skeletonScreen.show()
    }

    private fun onAttractions(attractions: MutableList<Attraction>?) {
        attractions?.let {
            val location: Location? = viewModel.attractionFilter.value?.location
            val type: Type? = viewModel.attractionFilter.value?.type

            if ((location == null) && (type == null)) {
                mRecyclerAdapter.setData(it)
            } else {
                var filteredAttractions = it.toList()
                location?.let {
                    filteredAttractions =
                        filteredAttractions.filter { attraction -> attraction.location?.id == it.id }
                }
                type?.let {
                    try {
                        filteredAttractions =
                            filteredAttractions.filter { attraction -> attraction.type!!.any { c -> c.attractionsId == it.id } }
                    } catch (e: Exception) {
                        Log.d(
                            Constants.LOGS_TAG,
                            "${e.message} произошла ошибка фильтрации attraction"
                        )
                    }
                }
                mRecyclerAdapter.setData(filteredAttractions.toMutableList())
            }
            skeletonScreen.hide()
            swipeRefreshLayout?.isRefreshing = false
        }
    }

    override fun onRecyclerItemClick(obj: Any) {
        if(obj is Attraction){
           iNavigate?.openWhereToGoDetail(obj.id)
        }
    }

    private val decorationList: RecyclerView.ItemDecoration = object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            val position = parent.getChildAdapterPosition(view)

            when(position){
                0 -> view.background = resources.getDrawable(R.drawable.to_go_item_background_double)
                else ->  view.background = resources.getDrawable(R.color.main_background)
            }

            outRect.set(0,
                0,
                0,
                if (position == parent.adapter!!.itemCount - 1) resources.getDimensionPixelSize(R.dimen._16sdp) else 0 )
        }
    }
}