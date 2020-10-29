package ru.turizmryazan.ui.wheretostay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.turizmryazan.R
import ru.turizmryazan.base.BaseFragment
import ru.turizmryazan.model.models.HotelsFilter
import ru.turizmryazan.model.models.Location
import ru.turizmryazan.model.models.Type
import ru.turizmryazan.ui.main.MainViewModel

class WhereToStayFilterFragment : BaseFragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var spinnerTypeHotel: Spinner
    private lateinit var spinnerLocations: Spinner
    private var locationAdapter: ArrayAdapter<String>? = null
    private var typeHotelsAdapter: ArrayAdapter<String>? = null
    private lateinit var tvCancel: TextView
    private lateinit var ibApply: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        iMainComponent?.setDefaultStatusBar()
        iMainComponent?.hideBottomNavigation()
        return inflater.inflate(R.layout.fragment_where_to_stay_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        listeners()
        observeMutable()
        setToolbarTitle(getString(R.string.filter))
    }

    private fun initViews(view: View) {
        spinnerLocations = view.findViewById(R.id.sp_location_fragment_where_to_stay_filter)
        spinnerTypeHotel = view.findViewById(R.id.sp_type_object_fragment_where_to_stay_filter)
        tvCancel = view.findViewById(R.id.tv_cancel_fragment_where_to_stay_filter)
        ibApply = view.findViewById(R.id.ib_apply_fragment_where_to_stay)
    }

    private fun listeners() {
        tvCancel.setOnClickListener {
            onClickCancel()
        }

        ibApply.setOnClickListener {
            onClickApply()
        }
    }

    private fun observeMutable() {
        viewModel.dictionaryLocations.observe(viewLifecycleOwner, Observer { dictionaryLocations ->
            onDictionaryLocations(dictionaryLocations)
        })

        viewModel.dictionaryTypeHotels.observe(
            viewLifecycleOwner,
            Observer { dictionaryTypeHotels ->
                onDictionaryTypeHotels(dictionaryTypeHotels)
            })

        viewModel.hotelsFilter.observe(viewLifecycleOwner, Observer { hotelsFilter ->
            onHotelsFilter(hotelsFilter)
        })
    }

    private fun onClickApply() {

        var locationHotel: Location? = null
        if (spinnerLocations.selectedItemPosition != 0) {
            locationHotel =
                viewModel.dictionaryLocations.value?.get(spinnerLocations.selectedItemPosition - 1)
        }

        var typeHotel: Type? = null
        if (spinnerTypeHotel.selectedItemPosition != 0) {
            typeHotel =
                viewModel.dictionaryTypeHotels.value?.get(spinnerTypeHotel.selectedItemPosition - 1)
        }

        viewModel.hotelsFilter.value = HotelsFilter(locationHotel, typeHotel)
        iNavigate?.back()
    }

    private fun onClickCancel() {
        spinnerTypeHotel.setSelection(0)
        spinnerLocations.setSelection(0)
    }

    private fun onDictionaryLocations(dictionaryLocations: MutableList<Location>?) {
        dictionaryLocations?.let {
            spinnerLocations.adapter = getLocationAdapter(it)
        }
    }

    private fun onDictionaryTypeHotels(dictionaryTypeHotels: MutableList<Type>?) {
        dictionaryTypeHotels?.let {
            spinnerTypeHotel.adapter = getTypeHotelsAdapter(it)
        }
    }

    fun onHotelsFilter(hotelsFilter: HotelsFilter?) {
        hotelsFilter?.let {
            it.locationHotel?.let {
                val position: Int? =
                    viewModel.dictionaryLocations.value?.indexOfFirst { c -> c.id == it.id }
                position?.let {
                    spinnerLocations.setSelection(position + 1)
                }
            }

            it.typeHotel?.let {
                val position: Int? =
                    viewModel.dictionaryTypeHotels.value?.indexOfFirst { c -> c.id == it.id }
                position?.let {
                    spinnerTypeHotel.setSelection(position + 1)
                }
            }
        }
    }

    private fun getLocationAdapter(locations: MutableList<Location>): SpinnerAdapter {
        val listLoaction: MutableList<String> = mutableListOf(getString(R.string.location))

        locations.forEach { location ->
            listLoaction.add(location.name ?: "")
        }

        return ArrayAdapter(
            requireContext(),
            R.layout.spinner_item,
            listLoaction
        )
    }

    private fun getTypeHotelsAdapter(it: MutableList<Type>): SpinnerAdapter {
        val listTypeHotels: MutableList<String> = mutableListOf(getString(R.string.type_hotels))

        it.forEach { typeHotel ->
            listTypeHotels.add(typeHotel.name ?: "")
        }

        return ArrayAdapter(
            requireContext(),
            R.layout.spinner_item,
            listTypeHotels
        )
    }

}