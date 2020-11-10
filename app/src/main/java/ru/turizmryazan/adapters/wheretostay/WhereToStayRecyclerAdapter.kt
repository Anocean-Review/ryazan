package ru.turizmryazan.adapters.wheretostay

import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.turizmryazan.R
import ru.turizmryazan.TurizmApp
import ru.turizmryazan.adapters.IRecyclerClick
import ru.turizmryazan.model.models.Hotel
import ru.turizmryazan.utils.Constants
import javax.inject.Inject

class WhereToStayRecyclerAdapter(private val iClick: IRecyclerClick) :
    RecyclerView.Adapter<WhereToStayRecyclerViewHolder>() {

    val collection: MutableList<Hotel> = mutableListOf()

    init {
        TurizmApp.daggerComponent?.inject(this)
    }

    @Inject
    lateinit var app: Application

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WhereToStayRecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_recycler_hotel, parent, false)

        return WhereToStayRecyclerViewHolder(view)
    }

    override fun onBindViewHolder(view: WhereToStayRecyclerViewHolder, position: Int) {

        val hotel: Hotel = collection[position]
        onBind(hotel, view)
    }

    private fun onBind(hotel: Hotel, view: WhereToStayRecyclerViewHolder) {

        view.tvRating.text = hotel.starsText ?: ""
        val url = Constants.BASE_IMAGE_URL + hotel.images?.main?.path
        Picasso.get()
            .load(url)
            .fit()
            .centerCrop()
            .into(view.ivPhoto)

        val title: String =
            if (!hotel.type?.name.isNullOrEmpty()) hotel.type?.name + " " + hotel.name else hotel.name
                ?: ""

        view.tvTitle.text = title
        view.tvAdress.text = hotel.address ?: ""

        view.mcardView.setOnClickListener {
            iClick.onRecyclerItemClick(hotel)
        }
    }

    override fun getItemCount(): Int {
        return collection.size
    }

    fun setData(hotels: MutableList<Hotel>) {
        collection.clear()
        collection.addAll(hotels)
        notifyDataSetChanged()
    }
}