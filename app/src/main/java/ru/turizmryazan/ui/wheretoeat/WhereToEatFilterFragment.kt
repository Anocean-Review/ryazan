package ru.turizmryazan.ui.wheretoeat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.turizmryazan.R
import ru.turizmryazan.base.BaseFragment
import ru.turizmryazan.model.models.*
import ru.turizmryazan.ui.main.MainViewModel
import ru.turizmryazan.utils.Utils

class WhereToEatFilterFragment: BaseFragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var spinnerTypeKitchen: Spinner
    private lateinit var spinnerLocations: Spinner
    private var locationAdapter: ArrayAdapter<String>? = null
    private var typeKitchensAdapter: ArrayAdapter<String>? = null
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
        return inflater.inflate(R.layout.fragmentt_where_to_eat_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        listeners()
        observeMutable()
        setToolbarTitle(getString(R.string.filter))
    }

    private fun initViews(view: View) {
        spinnerLocations = view.findViewById(R.id.sp_location_fragment_where_to_eat_filter)
        spinnerTypeKitchen = view.findViewById(R.id.sp_type_object_fragment_where_to_eat_filter)
        tvCancel = view.findViewById(R.id.tv_cancel_fragment_where_to_eat_filter)
        ibApply = view.findViewById(R.id.ib_apply_fragment_where_to_eat)
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

        viewModel.dictionaryTypeKitchens.observe(
            viewLifecycleOwner,
            Observer { dictionaryTypeKitchens ->
                onDictionaryTypeKitchens(dictionaryTypeKitchens)
            })

        viewModel.kitchensFilter.observe(viewLifecycleOwner, Observer { kitchensFilter ->
            onKitchensFilter(kitchensFilter)
        })
    }

    private fun onClickApply() {

        var locationEatPlace: Location? = null
        if (spinnerLocations.selectedItemPosition != 0) {
            locationEatPlace =
                viewModel.dictionaryLocations.value?.get(spinnerLocations.selectedItemPosition - 1)
        }

        var typeKitchen: Type? = null
        if (spinnerTypeKitchen.selectedItemPosition != 0) {
            typeKitchen =
                viewModel.dictionaryTypeKitchens.value?.get(spinnerTypeKitchen.selectedItemPosition - 1)
        }

        viewModel.kitchensFilter.value = KitchensFilter(locationEatPlace, typeKitchen)
        iNavigate?.back()
    }

    private fun onClickCancel() {
        spinnerTypeKitchen.setSelection(0)
        spinnerLocations.setSelection(0)
    }

    private fun onDictionaryLocations(dictionaryLocations: MutableList<Location>?) {
        dictionaryLocations?.let {
            spinnerLocations.adapter = Utils.getLocationAdapter(requireContext(), it)
        }
    }

    private fun onDictionaryTypeKitchens(dictionaryTypeKitchens: MutableList<Type>?) {
        dictionaryTypeKitchens?.let {
            spinnerTypeKitchen.adapter = Utils.getTypeKitchensAdapter(requireContext(), it)
        }
    }

    fun onKitchensFilter(kitchensFilter: KitchensFilter?) {
        kitchensFilter?.let {
            it.location?.let {
                val position: Int? =
                    viewModel.dictionaryLocations.value?.indexOfFirst { c -> c.id == it.id }
                position?.let {
                    spinnerLocations.setSelection(position + 1)
                }
            }

            it.type?.let {
                val position: Int? =
                    viewModel.dictionaryTypeKitchens.value?.indexOfFirst { c -> c.id == it.id }
                position?.let {
                    spinnerTypeKitchen.setSelection(position + 1)
                }
            }
        }
    }
}