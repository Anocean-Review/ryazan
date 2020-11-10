package ru.turizmryazan.ui.main

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.turizmryazan.R
import ru.turizmryazan.navigation.INavigation
import ru.turizmryazan.utils.Constants


class MainActivity : AppCompatActivity(), IMainComponent, INavigation {

    private lateinit var navController: NavController
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        initViews()
        loadDictionary()
    }

    private fun initViews() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        bottomNavigation = findViewById(R.id.bottom_navigation)
    }

    private fun loadDictionary() {
        viewModel.loadDictionary()
    }

    override fun hideBottomNavigation() {
        if (bottomNavigation.isVisible) {
            bottomNavigation.visibility = View.GONE
        }
    }

    override fun showBottomNavigation() {
        if (bottomNavigation.isGone || !bottomNavigation.isVisible) {
            bottomNavigation.visibility = View.VISIBLE
        }
    }

    override fun setTransparentStatusBar() {
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }
    }

    override fun setDefaultStatusBar() {
        window.apply {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            statusBarColor = resources.getColor(R.color.main_background)
        }
    }

    fun View.setMarginTop(marginTop: Int) {
        val menuLayoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
        menuLayoutParams.setMargins(0, marginTop, 0, 0)
        this.layoutParams = menuLayoutParams
    }

    override fun openTestFragment() {
        navController.navigate(R.id.secondTestFragment)
    }

    override fun openWhereToStay() {
        navController.navigate(R.id.whereToStayFragment)
    }

    override fun openWhereToStayFilter() {
        navController.navigate(R.id.whereToStayFilterFragment)
    }

    override fun back() {
        navController.popBackStack()
    }

    override fun openErrorServerFragment() {
        navController.navigate(R.id.errorServerFragment)
    }

    override fun openWhereToEat() {
        navController.navigate(R.id.whereToEatFragment)
    }

    override fun openWhereToEatFilter() {
        navController.navigate(R.id.whereToEatFilterFragment)
    }

    override fun openWhereToEatDetail(id: String?) {
        val bundle: Bundle = Bundle()
        bundle.putString(Constants.EAT_PLACE_ID_TAG, id)
        navController.navigate(R.id.whereToEatDetailFragment, bundle)
    }

    override fun openWhereToGo() {
        navController.navigate(R.id.whereToGoFragment)
    }

    override fun openWhereToGoFilter() {
        navController.navigate(R.id.whereToGoFilterFragment)
    }

    override fun openWhereToGoDetail(id: String?) {
        val bundle: Bundle = Bundle()
        bundle.putString(Constants.ATRACTION_ID_TAG, id)
        navController.navigate(R.id.whereToGoDetailFragmnet, bundle)
    }

    override fun openLogin() {
        navController.navigate(R.id.loginFragment)
    }

    override fun openForgetPassword() {
        navController.navigate(R.id.forgetPasswordFragment)
    }

    override fun openRegistration() {
        navController.navigate(R.id.registrationFragment)
    }

    override fun openWhereToStayDetail(id: String?) {
        val bundle: Bundle = Bundle()
        bundle.putString(Constants.HOTEL_ID_TAG, id)
        navController.navigate(R.id.whereToStayDetailFragment, bundle)
    }

    override fun openWelcome() {
        navController.navigate(R.id.welcomeFragment)
    }
}