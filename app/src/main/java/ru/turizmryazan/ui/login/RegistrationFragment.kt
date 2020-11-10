package ru.turizmryazan.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.turizmryazan.R
import ru.turizmryazan.base.BaseFragment
import ru.turizmryazan.model.models.Age
import ru.turizmryazan.model.models.Location
import ru.turizmryazan.model.models.Type
import ru.turizmryazan.ui.main.MainViewModel
import ru.turizmryazan.utils.Utils

class RegistrationFragment : BaseFragment() {

    private lateinit var etEmail: EditText
    private lateinit var spRegion: Spinner
    private lateinit var spAge: Spinner
    private lateinit var spSex: Spinner
    private lateinit var etPassword: EditText
    private lateinit var etRepeatPassword: EditText
    private lateinit var ibRegister: ImageButton
    private lateinit var mViewModel: LoginViewModel
    private lateinit var viewModel: MainViewModel
    private lateinit var tvRegisterText: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        iMainComponent?.setDefaultStatusBar()
        iMainComponent?.hideBottomNavigation()
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        observeMutable()
        setToolbarTitle(getString(R.string.registration))
    }

    private fun initViews(view: View) {
        etEmail = view.findViewById(R.id.et_email_fragment_registration)
        spRegion = view.findViewById(R.id.sp_region_fragment_registration)
        spAge = view.findViewById(R.id.sp_age_fragment_registration)
        spSex = view.findViewById(R.id.sp_sex_fragment_registration)
        etPassword = view.findViewById(R.id.et_password_fragment_register)
        etRepeatPassword = view.findViewById(R.id.et_repeat_password_fragment_register)
        ibRegister = view.findViewById(R.id.ib_enter_fragment_register)
        tvRegisterText = view.findViewById(R.id.tv_enter_text_fragment_register)
        progressBar = view.findViewById(R.id.pb_fragment_register)
    }

    private fun observeMutable() {
        viewModel.dictionaryLocations.observe(viewLifecycleOwner, Observer { locations ->
            onLocations(locations)
        })

        viewModel.dictionaryTypeAge.observe(viewLifecycleOwner, Observer { ages ->
            onAges(ages)
        })

        viewModel.dictionaryTypeSex.observe(viewLifecycleOwner, Observer { sexs ->
            onSexs(sexs)
        })
    }

    private fun onLocations(locations: MutableList<Location>?) {
        locations?.let {
            spRegion.adapter = Utils.getLocationAdapter(requireContext(), it)
        }
    }

    private fun onAges(ages: MutableList<Age>?) {
        ages?.let {
            spAge.adapter = Utils.getTypeAgeAdapter(requireContext(), it)
        }
    }

    private fun onSexs(sexs: MutableList<Type>?) {
        sexs?.let {
            spSex.adapter = Utils.getTypeSexAdapter(requireContext(), it)
        }
    }
}