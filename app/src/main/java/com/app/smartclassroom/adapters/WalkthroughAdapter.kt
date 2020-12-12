package com.app.smartclassroom.adapters

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import coil.load
import com.app.smartclassroom.R
import com.app.smartclassroom.ui.walkthrough.AppOnboardItem

class WalkthroughAdapter (val items:ArrayList<AppOnboardItem>) : PagerAdapter() {

    override fun getCount(): Int {
        return items.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = LayoutInflater.from(container.context).inflate(R.layout.list_cell_feat_image, container, false)
        val imageView = itemView.findViewById<ImageView>(R.id.iv_feat)
        imageView.load(items[position].imageId)
        container.addView(itemView)
        return itemView
    }

    override fun saveState(): Parcelable? {
        return null
    }
}