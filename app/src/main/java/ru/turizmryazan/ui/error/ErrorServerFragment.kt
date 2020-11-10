package ru.turizmryazan.ui.error

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import ru.turizmryazan.R
import ru.turizmryazan.base.BaseFragment

class ErrorServerFragment: BaseFragment() {

    private lateinit var ivErrorButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        iMainComponent?.setDefaultStatusBar()
        iMainComponent?.hideBottomNavigation()
        return inflater.inflate(R.layout.fragment_server_error, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        listeners()
    }

    private fun initViews(view: View) {
        ivErrorButton = view.findViewById(R.id.iv_error_button_fragment_server_error)
    }

    private fun listeners() {
        ivErrorButton.setOnClickListener {
            onClickErrorButton()
        }
    }

    private fun onClickErrorButton() {
        iNavigate?.back()
    }
}