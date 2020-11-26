package ru.turizmryazan.ui.wheretogo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.turizmryazan.R
import ru.turizmryazan.base.BaseFragment
import ru.turizmryazan.model.models.AttractionFilter
import ru.turizmryazan.model.models.Location
import ru.turizmryazan.model.models.Type
import ru.turizmryazan.ui.main.MainViewModel
import ru.turizmryazan.utils.Utils

class WhereToGoFilterFragment : BaseFragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var spinnerTypeAttraction: Spinner
    private lateinit var spinnerLocations: Spinner
    private var locationAdapter: ArrayAdapter<String>? = null
    private var typeAttractionsAdapter: ArrayAdapter<String>? = null
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
        return inflater.inflate(R.layout.fragment_where_to_go_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        listeners()
        observeMutable()
        setToolbarTitle(getString(R.string.filter))
    }

    private fun initViews(view: View) {
        spinnerLocations = view.findViewById(R.id.sp_location_fragment_where_to_go_filter)
        spinnerTypeAttraction = view.findViewById(R.id.sp_type_object_fragment_where_to_go_filter)
        tvCancel = view.findViewById(R.id.tv_cancel_fragment_where_to_go_filter)
        ibApply = view.findViewById(R.id.ib_apply_fragment_where_to_go)
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

        viewModel.dictionaryTypeAttractions.observe(
            viewLifecycleOwner,
            Observer { dictionaryTypeAttractions ->
                onDictionaryTypeAttractions(dictionaryTypeAttractions)
            })

        viewModel.attractionFilter.observe(viewLifecycleOwner, Observer { attractionFilter ->
            onAttractionsFilter(attractionFilter)
        })
    }

    private fun onClickApply() {

        var locationEatPlace: Location? = null
        if (spinnerLocations.selectedItemPosition != 0) {
            locationEatPlace =
                viewModel.dictionaryLocations.value?.get(spinnerLocations.selectedItemPosition - 1)
        }

        var typeAttraction: Type? = null
        if (spinnerTypeAttraction.selectedItemPosition != 0) {
            typeAttraction =
                viewModel.dictionaryTypeAttractions.value?.get(spinnerTypeAttraction.selectedItemPosition - 1)
        }

        viewModel.attractionFilter.value = AttractionFilter(locationEatPlace, typeAttraction)
        iNavigate?.back()
    }

    private fun onClickCancel() {
        spinnerTypeAttraction.setSelection(0)
        spinnerLocations.setSelection(0)
    }

    private fun onDictionaryLocations(dictionaryLocations: MutableList<Location>?) {
        dictionaryLocations?.let {
            spinnerLocations.adapter = Utils.getLocationAdapter(requireContext(), it)
        }
    }

    private fun onDictionaryTypeAttractions(dictionaryTypeAttractions: MutableList<Type>?) {
        dictionaryTypeAttractions?.let {
            spinnerTypeAttraction.adapter = Utils.getTypeAttractionAdapter(requireContext(), it)
        }
    }

    fun onAttractionsFilter(attractionFilter: AttractionFilter?) {
        attractionFilter?.let {
            it.location?.let {
                val position: Int? =
                    viewModel.dictionaryLocations.value?.indexOfFirst { c -> c.id == it.id }
                position?.let {
                    spinnerLocations.setSelection(position + 1)
                }
            }

            it.type?.let {
                val position: Int? =
                    viewModel.dictionaryTypeAttractions.value?.indexOfFirst { c -> c.id == it.id }
                position?.let {
                    spinnerTypeAttraction.setSelection(position + 1)
                }
            }
        }
    }

}