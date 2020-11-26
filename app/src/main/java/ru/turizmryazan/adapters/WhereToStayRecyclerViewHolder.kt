package ru.turizmryazan.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import ru.turizmryazan.R

class WhereToStayRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val ivPhoto: ImageView = view.findViewById(R.id.iv_photo_item_recycler_hotel)
    val tvRating: TextView = view.findViewById(R.id.tv_rating_item_recycler_hotel)
    val tvTitle: TextView = view.findViewById(R.id.tv_title_item_recycler_hotel)
    val tvAdress: TextView = view.findViewById(R.id.tv_adress_item_recycler_hotel)
    val mcardView: MaterialCardView = view.findViewById(R.id.cv_item_recycler_hotel)
    val clStay: ConstraintLayout = view.findViewById(R.id.cl_stay)
}