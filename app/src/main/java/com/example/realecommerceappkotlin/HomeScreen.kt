package com.example.realecommerceappkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_home_screen.*

class HomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        val brandsList = ArrayList<String>()
        val brandsUrl = "http://192.168.1.3/OnlineStoreApp/fetch_brands.php"
        val requestQueue = Volley.newRequestQueue(this)
        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET,brandsUrl,null,{
            response ->
            for (i in 0.until(response.length())){
                brandsList.add(response.getJSONObject(i).getString("brand"))
            }
            brandsListView.adapter = ArrayAdapter(this,R.layout.brands_item_text_view,brandsList)
        },{
            error ->
            val dialog = AlertDialog.Builder(this)
            dialog.setMessage(error.message)
            dialog.setTitle("Alert")
            dialog.create().show()
        })
        requestQueue.add(jsonArrayRequest)
        brandsListView.setOnItemClickListener { parent, view, position, id ->

            val tappedBrand = brandsList[position]
            val intent = Intent(this,FetchEproductsActivity::class.java)
            intent.putExtra("BRAND",tappedBrand)
            startActivity(intent)
        }
    }
}