package com.example.realecommerceappkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_fetch_eproducts.*
import java.util.*
import kotlin.collections.ArrayList

class FetchEproductsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetch_eproducts)
        val brand = intent.getStringExtra("BRAND")
        txtBrand.text = "$brand products :"
        val brandUrl = "http://192.168.1.3/OnlineStoreApp/get_electronic_products.php?brand=$brand"
        val productsArray = ArrayList<Product>()
        val requestQueue = Volley.newRequestQueue(this)
        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET,brandUrl,null,{
            response ->
            for (i in 0.until(response.length())){
                productsArray.add(Product(response.getJSONObject(i).getInt("id"),response.getJSONObject(i).getString("name"),
                response.getJSONObject(i).getInt("price"),response.getJSONObject(i).getString("picture")))
            }
            productRV.adapter = ProductsAdapter(this,productsArray)
            productRV.layoutManager = LinearLayoutManager(this)
        },{
            error ->
            val dialog = AlertDialog.Builder(this)
            dialog.setMessage(error.message)
            dialog.setTitle("alert")
            dialog.create().show()
        })
        requestQueue.add(jsonArrayRequest)
    }
}