package com.swma.dnbn.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import com.swma.dnbn.R
import com.swma.dnbn.adapter.UserCartAdapter
import com.swma.dnbn.item.ItemCart
import kotlinx.android.synthetic.main.fragment_user_cart.view.*

class UserCartFragment : Fragment() {

    lateinit var cartList: ArrayList<ItemCart>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_user_cart, container, false)

        cartList = ArrayList()
        // Http 통신 데이터 받기
        cartList.add(ItemCart("1", "자연산 도토리", "https://img-s-msn-com.akamaized.net/tenant/amp/entityid/BBNBQK1.img?h=338&w=530&m=6&q=60&o=f&l=f",
            11900, 0))
        cartList.add(ItemCart("1", "그냥 도토리", "https://pbs.twimg.com/profile_images/779566054389317632/nf8zQ8tR_400x400.jpg",
            8900, 0))
        cartList.add(ItemCart("1", "고오급 도토리", "https://t1.daumcdn.net/cfile/tistory/993F40405A6E911428",
            30900, 0))
        cartList.add(ItemCart("1", "중급 도토리", "https://pbs.twimg.com/profile_images/779566054389317632/nf8zQ8tR_400x400.jpg",
            21900, 0))
        cartList.add(ItemCart("1", "싸구려 도토리", "https://pbs.twimg.com/profile_images/779566054389317632/nf8zQ8tR_400x400.jpg",
            5900, 0))

        rootView.apply {
            rv_cart.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(activity)
                focusable = View.NOT_FOCUSABLE
                adapter = UserCartAdapter(requireActivity(), cartList)
            }

            var totalPrice = 0
            for (cart in cartList){
                totalPrice += cart.productPrice.toInt()
            }

            textTotalPrice.text = String.format("%,d원", totalPrice)

            btn_totalBuy.setOnClickListener {
                Toast.makeText(context, "전체 구매하러 가기", Toast.LENGTH_SHORT).show()
            }

        }


        return rootView
    }

}
