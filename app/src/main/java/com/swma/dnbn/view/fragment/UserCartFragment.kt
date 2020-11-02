package com.swma.dnbn.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import com.swma.dnbn.R
import com.swma.dnbn.model.dummyData.ProductDummy
import com.swma.dnbn.view.adapter.UserCartAdapter
import com.swma.dnbn.model.item.ItemProduct
import kotlinx.android.synthetic.main.fragment_user_cart.*
import kotlinx.android.synthetic.main.fragment_user_cart.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UserCartFragment : Fragment() {

    lateinit var cartList: ArrayList<ItemProduct>
    private val job = Job()
    private var totalPrice = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Dummy Data
        cartList = ProductDummy.productFoodData

        calculateTotalPrice(cartList)

        rv_cart.adapter = UserCartAdapter(requireActivity(), cartList)
        textTotalPrice.text = String.format("%,d원", totalPrice)

        rv_cart.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            focusable = View.NOT_FOCUSABLE
        }



        btn_totalBuy.setOnClickListener {
            Toast.makeText(context, "전체 구매하러 가기", Toast.LENGTH_SHORT).show()
        }
    }

    private fun calculateTotalPrice(carts: java.util.ArrayList<ItemProduct>) {
        for (cart in carts) {
            totalPrice += when (cart.productChangedPrice) {
                -1 -> cart.productPrice
                else -> cart.productChangedPrice
            }
        }
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

}
