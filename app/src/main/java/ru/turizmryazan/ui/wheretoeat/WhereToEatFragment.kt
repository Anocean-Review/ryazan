package ru.turizmryazan.ui.wheretoeat

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
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen
import com.ethanhua.skeleton.Skeleton
import ru.turizmryazan.R
import ru.turizmryazan.adapters.IRecyclerClick
import ru.turizmryazan.adapters.wheretoeat.WhereToEatRecyclerAdapter
import ru.turizmryazan.base.BaseFragment
import ru.turizmryazan.model.models.EatPlace
import ru.turizmryazan.model.models.Hotel
import ru.turizmryazan.model.models.Location
import ru.turizmryazan.model.models.Type
import ru.turizmryazan.ui.main.MainViewModel
import ru.turizmryazan.utils.Constants

class WhereToEatFragment : BaseFragment(), IRecyclerClick {

    private lateinit var mViewModel: WhereToEatDetailViewModel
    private lateinit var viewModel: MainViewModel
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mRecyclerAdapter: WhereToEatRecyclerAdapter
    private lateinit var skeletonScreen: RecyclerViewSkeletonScreen
    private lateinit var ivFilter: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        mViewModel = ViewModelProvider(this).get(WhereToEatDetailViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        iMainComponent?.setTransparentStatusBar()
        iMainComponent?.hideBottomNavigation()
        return inflater.inflate(R.layout.fragment_where_to_eat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        listeners()
        observeMutable()
        loadData()
        setToolbarTitle(getString(R.string.where_to_eat))
    }

    private fun initViews(view: View) {
        ivFilter = view.findViewById(R.id.iv_filter_default_toolbar_menu)
        mRecyclerView = view.findViewById(R.id.rv_fragment_where_to_eat)
        mRecyclerAdapter = WhereToEatRecyclerAdapter(this)
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
        viewModel.eatPlaces.observe(viewLifecycleOwner, Observer { eatPlaces ->
            onEatPlaces(eatPlaces)
        })
    }

    private fun onClickFilter() {
        iNavigate?.openWhereToEatFilter()
    }

    override fun onSwipeRefresh() {
        viewModel.loadEatPlaces()
        skeletonScreen.show()
    }

    private fun onEatPlaces(eatPlaces: MutableList<EatPlace>?) {
        eatPlaces?.let {
            val location: Location? = viewModel.kitchensFilter.value?.location
            val type: Type? = viewModel.kitchensFilter.value?.type

            if ((location == null) && (type == null)) {
                mRecyclerAdapter.setData(it)
            } else {
                var filteredEatPlaces = it.toList()
                location?.let {
                    filteredEatPlaces =
                        filteredEatPlaces.filter { eatPlace -> eatPlace.location?.id == it.id }
                }
                type?.let {
                    try {
                        filteredEatPlaces =
                            filteredEatPlaces.filter { eatPlace -> eatPlace.type!!.any { c -> c.kitchenId == it.id } }
                    } catch (e: Exception) {
                        Log.d(
                            Constants.LOGS_TAG,
                            "${e.message} произошла ошибка фильтрации eatPlace"
                        )
                    }
                }
                mRecyclerAdapter.setData(filteredEatPlaces.toMutableList())
            }
            skeletonScreen.hide()
            swipeRefreshLayout?.isRefreshing = false
        }
    }

    private fun loadData() {
        if (viewModel.hotels.value == null) {
            viewModel.loadEatPlaces()
            skeletonScreen.show()
        }
    }

    override fun onRecyclerItemClick(obj: Any) {
        if(obj is EatPlace){
            iNavigate?.openWhereToEatDetail(obj.id)
        }
    }

    private val decorationList: RecyclerView.ItemDecoration =
        object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                val position = parent.getChildAdapterPosition(view)

                when (position) {
                    0 -> view.background = resources.getDrawable(R.drawable.to_eat_item_background)
                    1 -> view.background =
                        resources.getDrawable(R.drawable.to_eat_item_background_double)
                    else -> view.background = resources.getDrawable(R.color.main_background)
                }

                outRect.set(
                    0,
                    0,
                    0,
                    if (position == parent.adapter!!.itemCount - 1) resources.getDimensionPixelSize(
                        R.dimen._16sdp
                    ) else 0
                )
            }
        }
}