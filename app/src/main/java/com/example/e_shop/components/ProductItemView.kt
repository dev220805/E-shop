package com.example.e_shop.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.e_shop.AppUtil
import com.example.e_shop.GlobalNavigation
import com.example.e_shop.model.ProductModel
import kotlin.math.roundToInt

@Composable
fun ProductItemView(
    modifier: Modifier = Modifier,
    product: ProductModel
) {
    val context = LocalContext.current
    val primaryColor = MaterialTheme.colorScheme.primary

    // Calculate discount %
    val discountPercent = try {
        val original = product.price.toDouble()
        val actual = product.actualprice.toDouble()
        if (original > 0) {
            (((original - actual) / original) * 100).roundToInt()
        } else 0
    } catch (e: Exception) { 0 }

    Box(
        modifier = modifier
            .padding(6.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(18.dp),
                ambientColor = primaryColor.copy(alpha = 0.1f),
                spotColor = primaryColor.copy(alpha = 0.08f)
            )
            .clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable {
                GlobalNavigation.navController.navigate("productdetails/" + product.id)
            }
    ) {
        Column {
            // Product image with discount badge
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .clip(RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
            ) {
                AsyncImage(
                    model = product.images.firstOrNull(),
                    contentDescription = product.title,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                )

                // Discount badge
                if (discountPercent > 0) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(8.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        Color(0xFFE53935),
                                        Color(0xFFFF7043)
                                    )
                                )
                            )
                            .padding(horizontal = 6.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "-$discountPercent%",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }

            // Product info
            Column(
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 8.dp, bottom = 10.dp)
            ) {
                Text(
                    text = product.title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 17.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "$${product.actualprice}",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = primaryColor
                        )
                        // NEW
                        if ((product.price.toDoubleOrNull() ?: 0.0) > (product.actualprice.toDoubleOrNull() ?: 0.0)) {
                            Text(
                                text = "$${product.price}",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                                textDecoration = TextDecoration.LineThrough
                            )
                        }
                    }

                    // Cart button — gradient circle
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .shadow(
                                elevation = 6.dp,
                                shape = CircleShape,
                                ambientColor = primaryColor.copy(alpha = 0.3f)
                            )
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(
                                        primaryColor,
                                        MaterialTheme.colorScheme.tertiary.copy(alpha = 0.9f)
                                    )
                                )
                            )
                            .clickable { AppUtil.addToCart(context, product.id) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Add to cart",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}
