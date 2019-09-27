package com.swma.dnbn.adapter

import android.app.Activity
import android.graphics.Paint
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import com.squareup.picasso.Picasso
import com.swma.dnbn.R
import com.swma.dnbn.item.ItemSlide
import kotlinx.android.synthetic.main.row_slide_item.view.*

class SlideAdapter(private val context: Activity, private val items: ArrayList<ItemSlide>): PagerAdapter(){

    override fun isViewFromObject(view: View, `object`: Any) = (view == `object`)

    override fun getCount() = items.size

    // attach item to slide
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val slideLayout = context.layoutInflater.inflate(R.layout.row_slide_item, container, false)
        val item = items[position]

        slideLayout.apply {
            textTitle.isSelected = true
            Picasso.get().load(item.slideImgUrl).into(imageView)
            textTitle.text = item.slideTitle
            textOriginPrice.text = String.format("%,d", item.slideOriginPrice)

            // 가격 할인 중이면
            if (item.slideChangedPrice != -1){
                textOriginPrice.apply {
                    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    setTextColor(ContextCompat.getColor(context, R.color.red))
                }
                textChangedPrice.apply {
                    text = String.format("%,d", item.slideChangedPrice)
                    visibility = View.VISIBLE
                }
            }

            // 클릭 리스너
            cardView.setOnClickListener {
                Toast.makeText(context, item.slideTitle + " Clicked!", Toast.LENGTH_SHORT).show()
            }

        }

        // View 달아주기
        container.addView(slideLayout, 0)
        return slideLayout
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }


}