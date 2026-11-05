package com.example.e_shop

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.e_shop.Screen.AuthScreen
import com.example.e_shop.Screen.HomeScreen
import com.example.e_shop.Screen.LoginScreen
import com.example.e_shop.Screen.SignupScreen
import com.example.e_shop.pages.CheckoutPage
import com.example.e_shop.pages.categoryproduct
import com.example.e_shop.pages.ordersPage
import com.example.e_shop.pages.productdetails
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


@Composable
fun AppNavigation(modifier: Modifier){
    val navController = rememberNavController()
    GlobalNavigation.navController = navController

    val isLoggedIn = Firebase.auth.currentUser!=null
    val firstPage = if(isLoggedIn) "HomeScreen" else "auth"

    NavHost(navController = navController, startDestination = firstPage)   {

        composable("auth"){
            AuthScreen(modifier,navController)
        }

        composable("Login"){
            LoginScreen(modifier,navController)
        }
        composable("Signup"){
            SignupScreen(modifier,navController)
        }
        composable("HomeScreen"){
            HomeScreen(modifier,navController)
        }
        composable("categoryproduct/{categoryId}"){
            var categoryId = it.arguments?.getString("categoryId")
            categoryproduct(modifier,categoryId?:"")
        }
        composable("productdetails/{productId}"){
            var productId = it.arguments?.getString("productId")
            productdetails(modifier, productId ?:"")
        }
        composable("Checkout"){
            CheckoutPage(modifier)
        }
        composable("ordersPage"){
            ordersPage(modifier)
        }

    }

}
object GlobalNavigation{
    lateinit var navController : NavHostController

}