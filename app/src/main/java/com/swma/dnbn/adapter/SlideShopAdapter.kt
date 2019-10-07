package com.swma.dnbn.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.squareup.picasso.Picasso
import com.swma.dnbn.R
import kotlinx.android.synthetic.main.row_slide_shop_item.view.*

class SlideShopAdapter(private val context: Activity, private val items: ArrayList<String>): PagerAdapter(){




    override fun isViewFromObject(view: View, `object`: Any) = (view == `object`)

    override fun getCount() = items.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val slideLayout = context.layoutInflater.inflate(R.layout.row_slide_shop_item, container, false)

        items[position].let { url ->
            slideLayout.apply {
                Picasso.get().load(url).into(imageView)
            }
        }

        container.addView(slideLayout)

        return slideLayout
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }


}