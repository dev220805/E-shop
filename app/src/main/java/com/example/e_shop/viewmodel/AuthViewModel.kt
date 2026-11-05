package com.example.e_shop.viewmodel

import androidx.lifecycle.ViewModel
import com.example.e_shop.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class AuthViewModel : ViewModel() {

    private val auth = Firebase.auth

    private val firestore = Firebase.firestore

    fun login(email: String,password: String,onResult : (Boolean, String?)-> Unit){
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    onResult(true,null)

                }else{
                    onResult(false,it.exception?.localizedMessage)

                }
            }

    }

    fun signup ( email : String,name : String,password : String,onResult : (Boolean, String?)-> Unit){

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->

                val userId = result.user!!.uid
                val userModel = UserModel(name, email, userId)

                firestore.collection("users")
                    .document(userId)
                    .set(userModel)
                    .addOnSuccessListener {
                        onResult(true, null) // ✅ SUCCESS ONLY HERE
                    }
                    .addOnFailureListener { e ->
                        onResult(false, e.localizedMessage)
                    }


            }
            .addOnFailureListener { e ->
                onResult(false, e.localizedMessage)
            }
    }

}