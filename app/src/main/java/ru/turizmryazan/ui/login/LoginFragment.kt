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
import ru.turizmryazan.utils.Utils

class LoginFragment : BaseFragment() {

    private lateinit var etLogin: EditText
    private lateinit var etPassword: EditText
    private lateinit var tvForgetPassword: TextView
    private lateinit var tvRegister: TextView
    private lateinit var ibEnter: ImageButton
    private lateinit var mViewModel: LoginViewModel
    private lateinit var tvEnterText: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        iMainComponent?.setDefaultStatusBar()
        iMainComponent?.hideBottomNavigation()
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        listeners()
        observeMutable()
        setToolbarTitle(getString(R.string.enter))
    }

    private fun initViews(view: View) {
        etLogin = view.findViewById(R.id.et_login_fragment_login)
        etPassword = view.findViewById(R.id.et_login_fragment_password)
        tvForgetPassword = view.findViewById(R.id.tv_forgot_password_fragment_login)
        tvRegister = view.findViewById(R.id.tv_register_fragment_login)
        ibEnter = view.findViewById(R.id.ib_enter_fragment_login)
        tvEnterText = view.findViewById(R.id.tv_enter_text_fragment_login)
        progressBar = view.findViewById(R.id.pb_fragment_login)
    }

    private fun listeners() {
        ibEnter.setOnClickListener {
            if (progressBar.visibility == View.GONE) {
                onClickEnter()
            }
        }

        tvForgetPassword.setOnClickListener {
            onClickForgetPassword()
        }

        tvRegister.setOnClickListener {
            onClickRegister()
        }
    }

    private fun observeMutable() {
        mViewModel.authorizationResult.observe(viewLifecycleOwner, Observer { result ->
            onAuthorizationResult(result)
        })
    }

    private fun onAuthorizationResult(result: Boolean?) {
        result?.let {
            if (result) {
                Toast.makeText(requireContext(), getString(R.string.success), Toast.LENGTH_LONG)
                    .show()
            } else {
                Toast.makeText(requireContext(), getString(R.string.login_error), Toast.LENGTH_LONG)
                    .show()
            }
        }
        showProgressBar(false)
    }

    private fun onClickEnter() {
        val email = etLogin.text.toString()
        val password = etPassword.text.toString()

        if (checkEmailPassword(email, password)) {
            showProgressBar(true)
            mViewModel.authorization(email, password)
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.enter_login_and_password),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun checkEmailPassword(email: String, password: String): Boolean {
        return !email.isNullOrEmpty() && !password.isNullOrEmpty()
    }

    private fun onClickForgetPassword() {
        iNavigate?.openForgetPassword()
    }

    private fun onClickRegister() {
        iNavigate?.openRegistration()
    }

    private fun showProgressBar(bool: Boolean) {
        if (bool) {
            tvEnterText.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        } else {
            tvEnterText.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }
    }
}