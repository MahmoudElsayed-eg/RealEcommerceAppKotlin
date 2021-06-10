package com.example.realecommerceappkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        btnSignUpSignUp.setOnClickListener {
            if(edtPassSignUp.text.toString().trim() != "" && edtPassSignUp.text.toString() == edtPassSignUpConfirm.text.toString()){
                //register
                val stringUrl = "http://192.168.1.3/OnlineStoreApp/join_new_user.php?email=" + edtEmailSignUp.text.toString() +
                        "&username=" +edtUserNameSignUp.text.toString() + "&pass=" +
                        edtPassSignUp.text.toString()
                val requestQueue = Volley.newRequestQueue(this)
                val stringRequest = StringRequest(Request.Method.GET,stringUrl,{
                        response->
                    if (response == "A user already exists"){
                        val dialog = AlertDialog.Builder(this)
                        dialog.setMessage(response)
                        dialog.setTitle("Alert")
                        dialog.create().show()
                    }else{
                        Person.email = edtEmailSignUp.text.toString()
                        Toast.makeText(this,response,Toast.LENGTH_SHORT).show()
                        val intent = Intent(this,HomeScreen::class.java)
                        startActivity(intent)
                        finish()
                    }
                },{
                    error->
                    val dialog = AlertDialog.Builder(this)
                    dialog.setMessage(error.message)
                    dialog.setTitle("Alert")
                    dialog.create().show()
                })
                requestQueue.add(stringRequest)
            }else{
                val dialog = AlertDialog.Builder(this)
                dialog.setMessage("Password Mismatch")
                dialog.setTitle("Alert")
                dialog.create().show()
            }
        }
        btnLoginSignUp.setOnClickListener {
            finish()
        }
    }
}