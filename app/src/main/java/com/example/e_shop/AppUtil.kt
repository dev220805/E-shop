package com.example.e_shop

import android.app.Activity
import java.util.Locale
import android.content.Context
import android.os.Message
import android.widget.Toast
import androidx.compose.material3.TimePicker
import com.example.e_shop.model.ordermodel
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import com.razorpay.Checkout
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.UUID

object AppUtil {

    fun showToast(context: Context,message : String){
        Toast.makeText(context,message, Toast.LENGTH_LONG).show()
    }

    fun addToCart(context:Context,productId : String){
        val userDoc = Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)

        userDoc.get().addOnCompleteListener {
            if (it.isSuccessful) {
                val currentCart = it.result.get("cartItems") as? Map<String, Long> ?: emptyMap()
                val currentQuantity = currentCart[productId] ?: 0
                val updatedQuantity = currentQuantity + 1;

                val updatedCart = mapOf("cartItems.$productId" to updatedQuantity)

                userDoc.update(updatedCart)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            showToast(context, "Item added to cart")
                        } else {
                            showToast(context, "Failed to add item to cart")
                        }
                    }

            }

        }
    }

    fun removeFromCart(context:Context,productId : String,removeall : Boolean=false){
        val userDoc = Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)

        userDoc.get().addOnCompleteListener {
            if (it.isSuccessful) {
                val currentCart = it.result.get("cartItems") as? Map<String, Long> ?: emptyMap()
                val currentQuantity = currentCart[productId] ?: 0
                val updatedQuantity = currentQuantity - 1;

                val updatedCart =
                    if(updatedQuantity<=0 || removeall)
                        mapOf("cartItems.$productId" to FieldValue.delete())
                else
                    mapOf("cartItems.$productId" to updatedQuantity)

                userDoc.update(updatedCart)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            showToast(context, "Item removed from cart")
                        } else {
                            showToast(context, "Failed to remove item from cart")
                        }
                    }

            }

        }
    }

    fun ClearCartAndAddtoOrders(){
        val userDoc = Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)


        userDoc.get().addOnCompleteListener {
            if (it.isSuccessful) {
                val currentCart = it.result.get("cartItems") as? Map<String, Long> ?: emptyMap()

                val order = ordermodel(
                    id = "ORD_"+ UUID.randomUUID().toString().replace("-","").take(10).uppercase(),
                    userId = FirebaseAuth.getInstance().currentUser?.uid!!,
                    date = Timestamp.now(),
                    items = currentCart,
                    status = "Ordered",
                    address = it.result.get("address") as String
                )

                Firebase.firestore.collection("orders")
                    .document(order.id).set(order)
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            userDoc.update("cartItems", FieldValue.delete())
                        }
                    }


            }

        }

    }
    fun getDiscountPercentage() : Float{
        return 10.0f
    }
    fun getTaxPercentage() : Float{
        return 13.0f
    }

    fun razorpayApiKey() : String{
        return "rzp_test_SMpJHbJS5Amx5t"
    }

    fun startpayment(amount : Float){
        val checkout = Checkout()
        checkout.setKeyID(razorpayApiKey())

        val options = JSONObject()
        options.put("name","E Shop")
        options.put("description","")
        options.put("amount",amount*100)
        options.put("currency","INR")

        checkout.open(GlobalNavigation.navController.context as Activity,options)

    }

    fun formatData(timestamp: Timestamp): String {
        val sdf = SimpleDateFormat("dd MM yyyy, hh:mm a", Locale.getDefault())
        return sdf.format(timestamp.toDate())
    }

}


