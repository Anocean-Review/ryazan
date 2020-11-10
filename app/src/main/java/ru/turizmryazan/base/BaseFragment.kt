package ru.turizmryazan.base

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.turizmryazan.R
import ru.turizmryazan.navigation.INavigation
import ru.turizmryazan.ui.main.IMainComponent

abstract class BaseFragment : Fragment() {

    var iMainComponent: IMainComponent? = null
    var iNavigate: INavigation? = null
    var tvDefaultToolbarTitle: TextView? = null
    var ivBackButton: ImageView? = null
    var swipeRefreshLayout: SwipeRefreshLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        iMainComponent = requireActivity() as IMainComponent
        iNavigate = requireActivity() as INavigation

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBaseViews(view)
        listenersBase()
    }

    private fun initBaseViews(view: View) {
        tvDefaultToolbarTitle = view.findViewById(R.id.tv_title_default_toolbar_menu)
        ivBackButton = view.findViewById(R.id.iv_arrow_left_default_toolbar_menu)
        swipeRefreshLayout = view.findViewById(R.id.swipe_to_refresh)
        swipeRefreshLayout?.setProgressViewOffset(false, -200, -200)
    }

    private fun listenersBase() {
        ivBackButton?.let {
            it.setOnClickListener {
                onClickBackButton()
            }
        }

        swipeRefreshLayout?.let {
            it.setOnRefreshListener {
                onSwipeRefresh()
            }
        }
    }

    open fun onSwipeRefresh() {

    }

    fun setToolbarTitle(value: String) {
        tvDefaultToolbarTitle?.let {
            it.text = value
        }
    }

    private fun onClickBackButton() {
        iNavigate?.back()
    }

    open fun onErrorDetail(value: Boolean?) {
        value?.let {
            if (it){
                iNavigate?.openErrorServerFragment()
            }
        }
    }
}