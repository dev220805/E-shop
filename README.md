# 🛒 E-Shop — Android E-Commerce App

A fully functional Android e-commerce application built with **Kotlin** and **Jetpack Compose**, powered by **Firebase** for authentication and real-time data, with **Razorpay** payment integration.

---

## ✨ Features

- 🔐 **User Authentication** — Email/password login & signup via Firebase Auth
- 🏠 **Home Screen** — Auto-scrolling banner, category chips, featured products grid
- 🗂️ **Category Browsing** — Filter products by category
- 📦 **Product Details** — Image pager, discount badge, specifications, add to cart
- 🛒 **Cart Management** — Add, remove, adjust quantity in real-time (Firestore-synced)
- 💳 **Checkout & Payment** — Price breakdown with discount/tax + Razorpay payment gateway
- 📋 **Order History** — View all past orders with status badges
- 👤 **Profile Page** — Update delivery address, view cart stats, sign out
- 🌗 **Dynamic Theming** — Supports Material You dynamic colors (Android 12+)

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose + Material 3 |
| Navigation | Jetpack Navigation Compose |
| Auth | Firebase Authentication |
| Database | Firebase Firestore |
| Image Loading | Coil |
| Payment | Razorpay Checkout SDK |
| Architecture | MVVM (ViewModel + Compose State) |
| Min SDK | 26 (Android 8.0) |
| Target SDK | 36 |

---

## 🗂️ Project Structure
app/src/main/java/com/example/e_shop/
├── Screen/          # Auth, Login, Signup, HomeScreen
├── pages/           # HomePage, CartPage, CheckoutPage, ProductDetails, Orders, Profile
├── components/      # BannerView, CategoryView, ProductItemView, CartItemView, OrderView
├── model/           # ProductModel, UserModel, OrderModel, CategoryModel
├── viewmodel/       # AuthViewModel
├── AppNavigation.kt # NavHost with all routes
├── AppUtil.kt       # Cart helpers, payment, toast utilities
└── MainActivity.kt  # Entry point + Razorpay result handler

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog or later
- Android device/emulator (API 26+)
- Firebase project with Firestore & Auth enabled

### Setup

1. Clone the repo
```bash
   git clone https://github.com/dev220805/E-shop
```
2. Open in Android Studio and sync Gradle
3. Add your own `google-services.json` to `app/`
4. Set up Firestore with these collections:
   - `users` — user profiles
   - `data/stock/products` — product listings
   - `data/stock/categories` — categories
   - `data/banners` — banner image URLs
   - `orders` — placed orders
5. Replace the Razorpay test key in `AppUtil.kt` with your own
6. Run the app ▶️

---

## 💳 Payment Flow

Razorpay test mode is enabled. On successful payment, the cart is cleared and the order is saved to Firestore automatically.

---
