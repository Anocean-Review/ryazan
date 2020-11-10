package ru.turizmryazan.adapters.wheretoeat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.turizmryazan.R
import ru.turizmryazan.adapters.IRecyclerClick
import ru.turizmryazan.model.models.EatPlace
import ru.turizmryazan.utils.Constants
import ru.turizmryazan.utils.Utils

class WhereToEatRecyclerAdapter(private val iClick: IRecyclerClick) :
    RecyclerView.Adapter<WhereToEatRecyclerViewHolder>() {

    private val collection: MutableList<EatPlace> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WhereToEatRecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_recycler_hotel, parent, false)

        return WhereToEatRecyclerViewHolder(view)
    }

    override fun onBindViewHolder(view: WhereToEatRecyclerViewHolder, position: Int) {

        val eatPlace: EatPlace = collection[position]
        onBind(eatPlace, view)
    }

    private fun onBind(eatPlace: EatPlace, view: WhereToEatRecyclerViewHolder) {

        view.kitchenTag.text = Utils.getTypesKitchen(eatPlace.type)
        val url = Constants.BASE_IMAGE_URL + eatPlace.images?.main?.path
        Picasso.get()
            .load(url)
            .fit()
            .centerCrop()
            .into(view.ivPhoto)

        val title: String = eatPlace.name ?: ""

        view.tvTitle.text = title
        view.tvAdress.text = eatPlace.address ?: ""

        view.mcardView.setOnClickListener {
            iClick.onRecyclerItemClick(eatPlace)
        }
    }

    override fun getItemCount(): Int {
        return collection.size
    }

    fun setData(eatPlaces: MutableList<EatPlace>) {
        collection.clear()
        collection.addAll(eatPlaces)
        notifyDataSetChanged()
    }
}