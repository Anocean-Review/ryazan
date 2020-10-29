package ru.turizmryazan.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.turizmryazan.R

class WhereToStayDeatilViewPagerAdapter(val iViewPagerClick: IViewPagerClick): RecyclerView.Adapter<WhereToStayViewPagerViewHolder>() {

    val collection: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WhereToStayViewPagerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_viewpager_hotel_detail, parent, false)
        return WhereToStayViewPagerViewHolder(view)
    }

    override fun onBindViewHolder(view: WhereToStayViewPagerViewHolder, position: Int) {
        val url = collection[position]
        Picasso.get().load(url).fit().centerCrop().into(view.ivPhoto)
    }

    override fun getItemCount(): Int {
        return collection.size
    }

    fun setData(list: MutableList<String>){
        collection.clear()
        collection.addAll(list)
        notifyDataSetChanged()
    }
}