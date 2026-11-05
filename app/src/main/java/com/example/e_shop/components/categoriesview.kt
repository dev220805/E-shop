package com.example.e_shop.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.e_shop.GlobalNavigation
import com.example.e_shop.model.categorymodel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@Composable
fun categoriesview(modifier: Modifier = Modifier) {
    val categorylist = remember { mutableStateOf<List<categorymodel>>(emptyList()) }
    var selectedId by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        Firebase.firestore
            .collection("data")
            .document("stock")
            .collection("categories")
            .get()
            .addOnSuccessListener { snapshot ->
                val resultList = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(categorymodel::class.java)
                }
                categorylist.value = resultList
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error fetching categories", e)
            }
    }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 2.dp, vertical = 6.dp)
    ) {
        items(categorylist.value) { item ->
            categoryitem(
                category = item,
                isSelected = selectedId == item.id,
                onSelected = { selectedId = item.id }
            )
        }
    }
}

@Composable
fun categoryitem(
    category: categorymodel,
    isSelected: Boolean = false,
    onSelected: () -> Unit = {}
) {
    val primaryColor = MaterialTheme.colorScheme.primary

    Box(
        modifier = Modifier
            .width(88.dp)
            .shadow(
                elevation = if (isSelected) 10.dp else 4.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = if (isSelected) primaryColor.copy(alpha = 0.3f) else Color.Black.copy(alpha = 0.08f),
                spotColor = if (isSelected) primaryColor.copy(alpha = 0.25f) else Color.Transparent
            )
            .clip(RoundedCornerShape(16.dp))
            .background(
                if (isSelected)
                    Brush.linearGradient(
                        colors = listOf(
                            primaryColor,
                            MaterialTheme.colorScheme.tertiary.copy(alpha = 0.85f)
                        )
                    )
                else
                    Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.colorScheme.surface
                        )
                    )
            )
            .clickable {
                onSelected()
                GlobalNavigation.navController.navigate("categoryproduct/" + category.id)
            }
            .padding(vertical = 12.dp, horizontal = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = category.imageUrl,
                contentDescription = category.name,
                modifier = Modifier.size(44.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = category.name,
                textAlign = TextAlign.Center,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                maxLines = 1
            )
        }
    }
}
