package com.example.realecommerceappkotlin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class AmountFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_amount, container, false)
        val edtAmount = view.findViewById<EditText>(R.id.edtEnterAmount)
        val btnAddToCart = view.findViewById<ImageButton>(R.id.imgAddToCart)
        btnAddToCart.setOnClickListener {
            val tpoUrl = "http://192.168.1.3/OnlineStoreApp/add_temporary_product.php?" +
                    "email=${Person.email}&id=${Person.productId}&amount=${edtAmount.text}"
            val requestQueue = Volley.newRequestQueue(activity)
            val stringRequest = StringRequest(Request.Method.GET,tpoUrl,{
                    response->
                val intent = Intent(activity,CartsProductsActivity::class.java)
                startActivity(intent)
            },{
                error->
                if (context != null) {
                 val dialog = AlertDialog.Builder(context!!)
                 dialog.setMessage(error.message)
                 dialog.setTitle("Alert")
                 dialog.create().show()
                }
            })
            requestQueue.add(stringRequest)

        }
        return view
    }

}