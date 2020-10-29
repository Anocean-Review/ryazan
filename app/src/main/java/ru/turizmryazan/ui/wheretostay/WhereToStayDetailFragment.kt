package ru.turizmryazan.ui.wheretostay

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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import ru.turizmryazan.R
import ru.turizmryazan.adapters.IViewPagerClick
import ru.turizmryazan.adapters.WhereToStayDeatilViewPagerAdapter
import ru.turizmryazan.base.BaseFragment
import ru.turizmryazan.model.models.HotelDeatil
import ru.turizmryazan.utils.Constants

class WhereToStayDetailFragment : BaseFragment(), IViewPagerClick {

    private lateinit var mViewModel: WhereToStayDetailViewModel
    private lateinit var viewPager2: ViewPager2
    private lateinit var viewPagerAdapter: WhereToStayDeatilViewPagerAdapter
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
    private lateinit var tvAllNearby: TextView

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
            Log.v(Constants.LOGS_TAG, "Не передан guid отеля для детальной страницы")
        }
        listeners()
        observeMutable()
    }

    private fun initView(view: View) {
        viewPager2 = view.findViewById(R.id.viewPager2)
        viewPagerAdapter = WhereToStayDeatilViewPagerAdapter(this)
        viewPager2.adapter = viewPagerAdapter
        tvTitle = view.findViewById(R.id.tv_title_fragment_where_to_stay_deatil)
        tvRating = view.findViewById(R.id.tv_rating_fragment_where_to_stay_detail)
        tvDescription = view.findViewById(R.id.tv_description_fragment_where_to_stay_deatil)
        ivDescriptionGradient = view.findViewById(R.id.iv_description_gradient)
        tvMoreDetail = view.findViewById(R.id.tv_more_deatil_fragment_where_to_stay_detail)
        tvAdress = view.findViewById(R.id.tv_adress_fragment_where_to_stay_detail)
        tvPayMethod = view.findViewById(R.id.tv_pay_method_fragment_where_to_stay_detail)
        tvAdvantage = view.findViewById(R.id.tv_advantage_fragment_where_to_stay_detail)
        tvPhoneContact = view.findViewById(R.id.tv_contact_fragment_where_to_stay_detail)
        tvEmail = view.findViewById(R.id.tv_email_fragment_where_to_stay_detail)
        tvWeb = view.findViewById(R.id.tv_web_fragment_where_to_stay_detail)
        tvTime = view.findViewById(R.id.tv_time_fragment_where_to_stay_detail)
        ibCreateMarshroute =
            view.findViewById(R.id.iv_create_marshroute_fragment_where_to_stay_detail)
        tvAllNearby = view.findViewById(R.id.tv_all_nearby_fragment_where_to_stay_detail)
    }

    private fun listeners() {
        tvDescription.setOnClickListener {
            onClickDescription()
        }
        tvMoreDetail.setOnClickListener {
            onClickMoreDetail()
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

    private fun onHotel(hotel: HotelDeatil?) {
        hotel?.let {
            viewPagerAdapter.setData(getImageUrlList(it))

            tvTitle.text = it.name ?: ""
            tvRating.text = it.starsText ?: ""

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tvDescription.text = Html.fromHtml(it.description, Html.FROM_HTML_MODE_COMPACT)
            } else {
                tvDescription.text = Html.fromHtml(it.description)
            }

            if (!it.address.isNullOrEmpty()) {
                tvAdress.text = it.address ?: ""
                tvAdress.visibility = View.VISIBLE
            } else {
                tvAdress.visibility = View.GONE
            }

            if (it.payment != 0 && it.payment == 1) {
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
                val contact: String = it.phone.toString() + it.contactPerson
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

    private fun getImageUrlList(hotel: HotelDeatil): MutableList<String> {
        val list: MutableList<String> = mutableListOf()
        hotel.files?.forEach {
            val url = Constants.BASE_IMAGE_URL + it.path
            list.add(url)
        }
        return list
    }

    override fun onViewPagerItemClick(obj: Any) {

    }
}