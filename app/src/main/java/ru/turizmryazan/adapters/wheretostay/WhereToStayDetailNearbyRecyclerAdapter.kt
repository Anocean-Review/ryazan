package ru.turizmryazan.adapters.wheretostay

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.turizmryazan.R
import ru.turizmryazan.adapters.IRecyclerClick

class WhereToStayDetailNearbyRecyclerAdapter(var iClick: IRecyclerClick):
    RecyclerView.Adapter<WhereToStayDetailNearbyViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WhereToStayDetailNearbyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_recycler_nearby, parent, false)

        return WhereToStayDetailNearbyViewHolder(view)
    }

    override fun onBindViewHolder(holder: WhereToStayDetailNearbyViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return 0
    }
}