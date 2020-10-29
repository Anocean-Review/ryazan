package ru.turizmryazan.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.squareup.picasso.Picasso
import ru.turizmryazan.R
import ru.turizmryazan.base.BaseFragment
import ru.turizmryazan.navigation.INavigation

class MainFragment : BaseFragment() {

    private lateinit var ivMain: ImageView
    private lateinit var ivWhereToStay: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v("Logs", "OnCreate")

    }

    override fun onStart() {
        super.onStart()
        Log.v("Logs", "onStart")

    }

    override fun onResume() {
        super.onResume()
        Log.v("Logs", "onResume")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.v("Logs", "onCreateView")
        iMainComponent?.setTransparentStatusBar()
        iMainComponent?.showBottomNavigation()
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        listeners()
    }

    private fun initViews(view: View) {
        ivMain = view.findViewById(R.id.iv_main_fragment)
        ivWhereToStay = view.findViewById(R.id.iv_menu_where_to_stay_frament_main)
    }

    private fun listeners() {
        ivMain.setOnClickListener {
            iNavigate?.openWelcome()
        }

        ivWhereToStay.setOnClickListener {
            iNavigate?.openWhereToStay()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}