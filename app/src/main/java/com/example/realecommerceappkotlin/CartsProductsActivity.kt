package com.example.realecommerceappkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_carts_products.*

class CartsProductsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carts_products)
        val stringUrl = "http://192.168.1.3/OnlineStoreApp/fetch_temporary_orders.php?email=${Person.email}"
        val cartsProductsList = ArrayList<String>()
        val requestQueue = Volley.newRequestQueue(this)
        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET,stringUrl,null,{
            response->
            for (i in 0.until(response.length())){
                cartsProductsList.add("${response.getJSONObject(i).getInt("id")}\n${response.getJSONObject(i).getString("name")}\n" +
                        "${response.getJSONObject(i).getInt("price")}\n${response.getJSONObject(i).getInt("amount")}")
            }
            cartProductListView.adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,cartsProductsList)

        },{
            error->
            val dialog = AlertDialog.Builder(this)
            dialog.setMessage(error.message)
            dialog.setTitle("Alert")
            dialog.create().show()
        })
        requestQueue.add(jsonArrayRequest)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cart_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item?.itemId == R.id.declineOrder) {
            val url = "http://192.168.1.3/OnlineStoreApp/delete_order.php?email=${Person.email}"
            val requestQueue = Volley.newRequestQueue(this)
            val stringRequest = StringRequest(Request.Method.GET,url,{
                response->
                val intent = Intent(this,HomeScreen::class.java)
                startActivity(intent)
            },{
                error->

            })
            requestQueue.add(stringRequest)

        }else if (item?.itemId == R.id.verifyOrder) {
            val url = "http://192.168.1.3/OnlineStoreApp/verify_order.php?email=${Person.email}"
            val requestQueue = Volley.newRequestQueue(this)
            val stringRequest = StringRequest(Request.Method.GET,url,{
                response->
                Toast.makeText(this,response,Toast.LENGTH_SHORT).show()
                val intent = Intent(this,FinalizeShoppingActivity::class.java)
                intent.putExtra("INVOICE_NUMBER",response)
                startActivity(intent)
            },{
                error->

            })
            requestQueue.add(stringRequest)
        }else if (item?.itemId == R.id.continueShopping) {
            val intent = Intent(this,HomeScreen::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}