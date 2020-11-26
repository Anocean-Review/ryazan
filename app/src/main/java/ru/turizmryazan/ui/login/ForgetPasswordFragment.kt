package ru.turizmryazan.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import ru.turizmryazan.R
import ru.turizmryazan.base.BaseFragment

class ForgetPasswordFragment : BaseFragment() {

    private lateinit var etLogin: EditText
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
        return inflater.inflate(R.layout.fragment_forget_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        listeners()
        setToolbarTitle(getString(R.string.recovery_password))
    }

    private fun initViews(view: View) {
        etLogin = view.findViewById(R.id.et_email_fragment_forget_fragment)
        tvRegister = view.findViewById(R.id.tv_register_fragment_forget)
        ibEnter = view.findViewById(R.id.ib_enter_fragment_forget)
        tvEnterText = view.findViewById(R.id.tv_enter_text_fragment_forget)
        progressBar = view.findViewById(R.id.pb_fragment_forget)
    }

    private fun listeners() {
        ibEnter.setOnClickListener {
            if (progressBar.visibility == View.GONE) {
                onClickEnter()
            }
        }

        tvRegister.setOnClickListener {
            onClickRegister()
        }
    }

    private fun onClickRegister() {
        iNavigate?.openRegistration()
    }

    private fun onClickEnter() {
        val email = etLogin.text.toString()
        if (!email.isEmpty()) {
            showProgressBar(true)
            mViewModel.rememberPassword(email)
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.enter_email),
                Toast.LENGTH_LONG
            ).show()
        }
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