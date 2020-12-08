package com.harish.smartclassroom

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.harish.smartclassroom.ui.walkthrough.AppOnboardItem

class WalkthroughTitleAdapter(val items:ArrayList<AppOnboardItem>) : PagerAdapter() {

    override fun getCount(): Int {
        return items.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = LayoutInflater.from(container.context).inflate(R.layout.list_cell_features_title, container, false)
        val tv_title = itemView.findViewById<TextView>(R.id.tv_onboarding_title)
        val tv_desc = itemView.findViewById<TextView>(R.id.tv_onboard_desc)
        tv_title.text = items[position].title
        tv_desc.text = items[position].desc
        container.addView(itemView)
        return itemView
    }

    override fun saveState(): Parcelable? {
        return null
    }
}