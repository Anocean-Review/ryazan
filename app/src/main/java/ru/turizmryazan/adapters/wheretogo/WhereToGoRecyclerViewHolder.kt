package ru.turizmryazan.adapters.wheretogo

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import ru.turizmryazan.R

class WhereToGoRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val ivPhoto: ImageView = view.findViewById(R.id.iv_photo_item_recycler_go)
    val tvTitle: TextView = view.findViewById(R.id.tv_title_item_recycler_go)
    val tvAdress: TextView = view.findViewById(R.id.tv_adress_item_recycler_go)
    val mcardView: MaterialCardView = view.findViewById(R.id.cv_item_recycler_go)
    val tvDescription: TextView = view.findViewById(R.id.tv_description_item_recycler_go)
}