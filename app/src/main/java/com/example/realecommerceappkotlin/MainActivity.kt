package com.example.realecommerceappkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnLogin.setOnClickListener {
            val loginUrl = "http://192.168.1.3/OnlineStoreApp/login_app_user.php?email=" + edtEmailLogin.text.toString() +
                    "&pass=" + edtPassLogin.text.toString()
            val requestQueue = Volley.newRequestQueue(this)
            val stringRequest = StringRequest(Request.Method.GET,loginUrl,{
                response->
                if (response == "the user does not exist"){
                    val dialog = AlertDialog.Builder(this)
                    dialog.setMessage(response)
                    dialog.setTitle("alert")
                    dialog.create().show()
                }else{
                    Person.email = edtEmailLogin.text.toString()
                    Toast.makeText(this,response,Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,HomeScreen::class.java)
                    startActivity(intent)
                    finish()
                }
            },{
                error->
                val dialog = AlertDialog.Builder(this)
                dialog.setMessage(error.message)
                dialog.setTitle("alert")
                dialog.create().show()
            })

            requestQueue.add(stringRequest)

        }
        btnSignUp.setOnClickListener {
            val intent = Intent(this,SignUp::class.java)
            startActivity(intent)
        }
    }
}