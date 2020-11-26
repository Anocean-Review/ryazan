package ru.turizmryazan.adapters

import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.turizmryazan.R
import ru.turizmryazan.TurizmApp
import ru.turizmryazan.model.models.Hotel
import ru.turizmryazan.utils.Constants
import javax.inject.Inject

class WhereToStayRecyclerAdapter(val iClick: IRecyclerClick) :
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
        when(position){
            0 -> view.clStay.background =
                app.resources.getDrawable(R.drawable.to_stay_item_backhround)
            1 -> view.clStay.background =
                app.resources.getDrawable(R.drawable.to_stay_item_background_double_colors)
            else ->  view.clStay.background = app.resources.getDrawable(R.color.main_background)
        }

        val hotel: Hotel = collection[position]
        onBind(hotel, view)
    }

    private fun onBind(hotel: Hotel, view: WhereToStayRecyclerViewHolder) {

        view.tvRating.text = hotel.starsText ?: ""
        val url = Constants.BASE_IMAGE_URL + hotel.fileMain?.path
        Picasso.get()
            .load(url)
            .fit()
            .centerCrop()
            .into(view.ivPhoto)
        view.tvTitle.text = hotel.name ?: ""
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