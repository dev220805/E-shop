package com.example.e_shop.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_shop.components.BannerView
import com.example.e_shop.components.HeaderView
import com.example.e_shop.components.ProductItemView
import com.example.e_shop.components.categoriesview
import com.example.e_shop.model.ProductModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@Composable
fun HomePage(modifier: Modifier = Modifier) {

    var productList by remember { mutableStateOf<List<ProductModel>>(emptyList()) }

    LaunchedEffect(Unit) {
        Firebase.firestore
            .collection("data")
            .document("stock")
            .collection("products")
            .get()
            .addOnSuccessListener { snapshot ->
                productList = snapshot.documents.mapNotNull { it.toObject(ProductModel::class.java) }
            }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(start = 14.dp, end = 14.dp, top = 16.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        // ── Header ──────────────────────────────────────────────────
        item(span = { GridItemSpan(2) }) {
            HeaderView(modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(14.dp))
        }

        // ── Banner ──────────────────────────────────────────────────
        item(span = { GridItemSpan(2) }) {
            BannerView(modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(18.dp))
        }

        // ── Promo chip ───────────────────────────────────────────────
        item(span = { GridItemSpan(2) }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                                MaterialTheme.colorScheme.tertiary.copy(alpha = 0.10f)
                            )
                        )
                    )
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "🔥", fontSize = 18.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Flash Deals Today",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Up to 50% off — limited time only!",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(18.dp))
        }

        // ── Categories section ───────────────────────────────────────
        item(span = { GridItemSpan(2) }) {
            SectionHeader(title = "Categories", subtitle = "Shop by type")
            Spacer(modifier = Modifier.height(10.dp))
        }

        item(span = { GridItemSpan(2) }) {
            categoriesview()
            Spacer(modifier = Modifier.height(20.dp))
        }

        // ── Products section ─────────────────────────────────────────
        item(span = { GridItemSpan(2) }) {
            SectionHeader(title = "Featured Products", subtitle = "Hand-picked for you")
            Spacer(modifier = Modifier.height(10.dp))
        }

        items(productList) { product ->
            ProductItemView(
                modifier = Modifier.fillMaxWidth(),
                product = product
            )
        }

        item(span = { GridItemSpan(2) }) {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun SectionHeader(title: String, subtitle: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurface,
                letterSpacing = (-0.2).sp
            )
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            )
        }
        Text(
            text = "See all →",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
