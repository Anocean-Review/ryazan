package ru.turizmryazan.ui.wheretostay

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator
import ru.turizmryazan.R
import ru.turizmryazan.TurizmApp
import ru.turizmryazan.adapters.IViewPagerClick
import ru.turizmryazan.adapters.DetailViewPagerAdapter
import ru.turizmryazan.base.BaseFragment
import ru.turizmryazan.model.models.HotelDetail
import ru.turizmryazan.utils.Constants
import ru.turizmryazan.utils.InternalLink
import ru.turizmryazan.utils.Utils
import java.lang.Exception
import javax.inject.Inject

class WhereToStayDetailFragment : BaseFragment(), IViewPagerClick {

    init {
        TurizmApp.daggerComponent?.inject(this)
    }

    @Inject
    lateinit var internalLink: InternalLink

    private lateinit var mViewModel: WhereToStayDetailViewModel
    private lateinit var viewPager2: ViewPager2
    private lateinit var viewPagerAdapter: DetailViewPagerAdapter
    private lateinit var tvTitle: TextView
    private lateinit var tvRating: TextView
    private lateinit var tvDescription: TextView
    private lateinit var ivDescriptionGradient: ImageView
    private lateinit var tvMoreDetail: TextView
    private lateinit var tvAdress: TextView
    private lateinit var tvPayMethod: TextView
    private lateinit var tvAdvantage: TextView
    private lateinit var tvPhoneContact: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvWeb: TextView
    private lateinit var tvTime: TextView
    private lateinit var ibCreateMarshroute: ImageButton
    private lateinit var dotIndicator: SpringDotsIndicator
    private lateinit var collapsingToolbarLayout: CollapsingToolbarLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(this).get(WhereToStayDetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        iMainComponent?.hideBottomNavigation()
        iMainComponent?.setTransparentStatusBar()
        return inflater.inflate(R.layout.fragment_where_to_stay_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view)
        val hotelId: String? = arguments?.getString(Constants.HOTEL_ID_TAG)

        if (hotelId != null) {
            if (mViewModel.hotel.value == null) {
                mViewModel.loadData(hotelId)
            }
        } else {
            Log.d(Constants.LOGS_TAG, "Не передан guid отеля для детальной страницы")
        }
        listeners()
        observeMutable()
        setToolbarSettings()
    }

    private fun initView(view: View) {
        viewPager2 = view.findViewById(R.id.viewPager2)
        viewPagerAdapter = DetailViewPagerAdapter(this)
        viewPager2.adapter = viewPagerAdapter
        dotIndicator = view.findViewById(R.id.spring_dots_indicator)
        dotIndicator.setViewPager2(viewPager2)
        tvTitle = view.findViewById(R.id.tv_title_fragment_where_to_stay_deatil)
        tvRating = view.findViewById(R.id.tv_rating_fragment_where_to_stay_detail)
        tvDescription = view.findViewById(R.id.tv_description_fragment_where_to_stay_deatil)
        ivDescriptionGradient = view.findViewById(R.id.iv_description_gradient)
        tvMoreDetail = view.findViewById(R.id.tv_more_deatil_fragment_where_to_stay_detail)
        tvAdress = view.findViewById(R.id.tv_adress_fragment_where_to_stay_detail)
        tvPayMethod = view.findViewById(R.id.tv_pay_method_fragment_where_to_stay_detail)
        tvAdvantage = view.findViewById(R.id.tv_advantage_fragment_where_to_stay_detail)
        tvPhoneContact = view.findViewById(R.id.tv_contact_phone_fragment_where_to_stay_detail)
        tvEmail = view.findViewById(R.id.tv_email_fragment_where_to_stay_detail)
        tvWeb = view.findViewById(R.id.tv_web_fragment_where_to_stay_detail)
        tvTime = view.findViewById(R.id.tv_time_fragment_where_to_stay_detail)
        ibCreateMarshroute =
            view.findViewById(R.id.iv_create_marshroute_fragment_where_to_stay_detail)
        collapsingToolbarLayout = view.findViewById(R.id.collapsing_toolbar_layout)
    }

    private fun setToolbarSettings() {
        collapsingToolbarLayout.contentScrim = resources.getDrawable(R.color.where_to_stay_background)
    }

    private fun listeners() {
        tvDescription.setOnClickListener {
            onClickDescription()
        }
        tvMoreDetail.setOnClickListener {
            onClickMoreDetail()
        }
        tvPhoneContact.setOnClickListener {
            onClickPhoneContact()
        }
        tvEmail.setOnClickListener {
            onClickEmail()
        }
        tvWeb.setOnClickListener {
            onClickWeb()
        }
        ibCreateMarshroute.setOnClickListener {
            onClickCreateMarshroute()
        }
    }

    private fun onClickCreateMarshroute() {

        mViewModel.hotel.value?.lat?.let { lat ->
            mViewModel.hotel.value?.lon?.let { lon ->
                internalLink.openMap(lat, lon, requireActivity())
            }
        }
    }

    private fun onClickWeb() {
        mViewModel.hotel.value?.site?.let {
           internalLink.openSite(it, requireActivity())
        }
    }

    private fun onClickEmail() {
        mViewModel.hotel.value?.email?.let {
           internalLink.openEmail(it, requireActivity())
        }
    }

    private fun onClickPhoneContact() {
        mViewModel.hotel.value?.phone?.let {
           internalLink.openPhone(it, requireActivity())
        }
    }

    private fun observeMutable() {
        mViewModel.hotel.observe(viewLifecycleOwner, Observer { hotel ->
            onHotel(hotel)
        })
    }

    private fun onClickMoreDetail() {
        toogleMoreDetail()
        toggleDescription()
    }

    private fun onClickDescription() {
        toogleMoreDetail()
        toggleDescription()
    }

    private fun toggleDescription() {
        if (tvDescription.maxLines == Constants.MAX_LINES_DESCRIPTION) {
            tvDescription.maxLines = Constants.MAX_LINES_BIG
            ivDescriptionGradient.visibility = View.INVISIBLE
        } else {
            tvDescription.maxLines = Constants.MAX_LINES_DESCRIPTION
            ivDescriptionGradient.visibility = View.VISIBLE
        }
    }

    private fun toogleMoreDetail() {
        if (tvDescription.maxLines == Constants.MAX_LINES_DESCRIPTION) {
            tvMoreDetail.text = getString(R.string.hide)
        } else {
            tvMoreDetail.text = getString(R.string.more_detail)
        }
    }

    private fun onHotel(hotel: HotelDetail?) {
        hotel?.let {
            val imageList = Utils.getImageUrlList(it.images?.gallery)
            viewPagerAdapter.setData(imageList)
            if (imageList.size > 1) {
                dotIndicator.visibility = View.VISIBLE
            } else {
                dotIndicator.visibility = View.INVISIBLE
            }

            val title: String =
                if (!it.type?.name.isNullOrEmpty()) it.type?.name + " " + it.name else it.name ?: ""
            tvTitle.text = title

            tvRating.text = it.starsText ?: ""

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tvDescription.text = Html.fromHtml(it.description, Html.FROM_HTML_MODE_COMPACT)
            } else {
                tvDescription.text = Html.fromHtml(it.description)
            }

            if (it.description?.length!! > Constants.MAX_DESCRIPTION_LENGTH) {
                tvMoreDetail.visibility = View.VISIBLE
                ivDescriptionGradient.visibility = View.VISIBLE
            } else {
                tvMoreDetail.visibility = View.INVISIBLE
                ivDescriptionGradient.visibility = View.INVISIBLE
                tvDescription.setOnClickListener(null)
            }

            if (!it.address.isNullOrEmpty()) {
                tvAdress.text = it.address ?: ""
                tvAdress.visibility = View.VISIBLE
            } else {
                tvAdress.visibility = View.GONE
            }

            if (it.payment == 1) {
                tvPayMethod.text = getString(R.string.be_payment)
                tvAdress.visibility = View.VISIBLE
            } else {
                tvPayMethod.visibility = View.GONE
            }

            if (it.advantages != null && it.advantages == 1) {
                tvAdvantage.text = getString(R.string.advantage)
                tvAdvantage.visibility = View.VISIBLE
            } else {
                tvAdvantage.visibility = View.GONE
            }

            if (it.phone != null) {
                val contact: String = it.phone.toString()
                tvPhoneContact.text = contact
                tvPhoneContact.visibility = View.VISIBLE
            } else {
                tvPhoneContact.visibility = View.GONE
            }

            if (!it.email.isNullOrEmpty()) {
                tvEmail.text = it.email
                tvEmail.visibility = View.VISIBLE
            } else {
                tvEmail.visibility = View.GONE
            }

            if (!it.site.isNullOrEmpty()) {
                tvWeb.text = it.site
                tvWeb.visibility = View.VISIBLE
            } else {
                tvWeb.visibility = View.GONE
            }

            if (!it.workTime.isNullOrEmpty()) {
                tvTime.text = it.workTime
                tvTime.visibility = View.VISIBLE
            } else {
                tvTime.visibility = View.GONE
            }
        }
    }

    override fun onViewPagerItemClick(obj: Any) {

    }
}