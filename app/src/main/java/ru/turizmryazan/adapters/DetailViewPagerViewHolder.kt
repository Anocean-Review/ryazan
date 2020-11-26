package ru.turizmryazan.adapters

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ru.turizmryazan.R

class DetailViewPagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val ivPhoto: ImageView = view.findViewById(R.id.iv_photo_item_viewpager_detail)
}