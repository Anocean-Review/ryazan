package ru.turizmryazan.adapters.wheretogo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.turizmryazan.R
import ru.turizmryazan.adapters.IRecyclerClick
import ru.turizmryazan.model.models.Attraction
import ru.turizmryazan.utils.Constants

class WhereToGoRecyclerAdapter(private val iClick: IRecyclerClick): RecyclerView.Adapter<WhereToGoRecyclerViewHolder>() {

    private val collection: MutableList<Attraction> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WhereToGoRecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_recycler_go, parent, false)

        return WhereToGoRecyclerViewHolder(view)
    }

    override fun onBindViewHolder(view: WhereToGoRecyclerViewHolder, position: Int) {
        val attraction: Attraction = collection[position]
        onBind(attraction, view)
    }

    private fun onBind(attraction: Attraction, view: WhereToGoRecyclerViewHolder) {

        val url = Constants.BASE_IMAGE_URL + attraction.images?.main?.path
        Picasso.get()
            .load(url)
            .fit()
            .centerCrop()
            .into(view.ivPhoto)

        val title: String = attraction.name ?: ""

        view.tvTitle.text = title
        view.tvAdress.text = attraction.address ?: ""
        view.tvDescription.text = attraction.description

        view.mcardView.setOnClickListener {
            iClick.onRecyclerItemClick(attraction)
        }
    }

    override fun getItemCount(): Int {
        return collection.size
    }

    fun setData(attraction: MutableList<Attraction>) {
        collection.clear()
        collection.addAll(attraction)
        notifyDataSetChanged()
    }
}