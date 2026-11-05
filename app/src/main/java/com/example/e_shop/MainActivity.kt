package com.example.e_shop

import android.app.AlertDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import com.example.e_shop.ui.theme.EShopTheme
import com.razorpay.PaymentResultListener


class MainActivity : ComponentActivity(), PaymentResultListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EShopTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(Modifier.padding(innerPadding))

                }
            }
        }
    }
    override fun onPaymentSuccess(razorpayPaymentId: String?) {
        AppUtil.ClearCartAndAddtoOrders()

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Payment Successful")
            .setMessage("Thank you! Your payment was completed.")
            .setPositiveButton("OK") { _, _ ->
                val navController = GlobalNavigation.navController
                navController.popBackStack()
                navController.navigate("Home")
            }
            .setCancelable(false)

        builder.show()
    }

    override fun onPaymentError(code: Int, description: String?) {
        AppUtil.showToast(this, "Payment Failed")
    }
}


