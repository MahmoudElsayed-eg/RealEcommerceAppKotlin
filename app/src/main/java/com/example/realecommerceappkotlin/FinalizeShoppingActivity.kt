package com.example.realecommerceappkotlin

import android.app.Instrumentation
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.paypal.android.sdk.payments.PayPalConfiguration
import com.paypal.android.sdk.payments.PayPalPayment
import com.paypal.android.sdk.payments.PayPalService
import com.paypal.android.sdk.payments.PaymentActivity
import kotlinx.android.synthetic.main.activity_finalize_shopping.*
import java.math.BigDecimal

class FinalizeShoppingActivity : AppCompatActivity() {
    var ttPrice : Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finalize_shopping)
        val url = "http://192.168.1.3/OnlineStoreApp/calculate_total_price.php?invoice_number=${intent.getStringExtra("INVOICE_NUMBER")}"
        val requestQueue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(Request.Method.GET,url,{
            response ->
            btnPaymentProcess.text = "Pay $$response via PayPal"
            ttPrice = response.toLong()
        },{
            error->

        })
        requestQueue.add(stringRequest)
        val payPalConfiguration : PayPalConfiguration = PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(MyPayPal.clientID)
        val ppService = Intent(this,PayPalService::class.java)
        ppService.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,payPalConfiguration)
        startService(ppService)

        btnPaymentProcess.setOnClickListener {
            val ppProcessing = PayPalPayment(BigDecimal.valueOf(ttPrice),"USD",
                "Online Store Kotlin!",PayPalPayment.PAYMENT_INTENT_SALE)
            val payPalPaymentIntent = Intent(this, PaymentActivity::class.java)
            payPalPaymentIntent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,payPalConfiguration)
            payPalPaymentIntent.putExtra(PaymentActivity.EXTRA_PAYMENT,ppProcessing)
            startActivityForResult(payPalPaymentIntent,1000)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000) {
            if (resultCode == RESULT_OK) {
                val intent = Intent(this,ThankYouActivity::class.java)
                startActivity(intent)
                finish()
            }else {
                Toast.makeText(this,"sorry something went wrong try again",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this,PayPalService::class.java))
    }
}