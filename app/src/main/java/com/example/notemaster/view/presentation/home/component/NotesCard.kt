@file:kotlin.OptIn(ExperimentalMaterial3Api::class)

package com.example.notemaster.view.presentation.home.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesCard(
    title: String,
    text: String,
    onItemNotesClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    //random color for card
    val cardColors = listOf(
        Color(0xFFBBDEFB),
        Color(0xFFB2EBF2),
        Color(0xFFE1BEE7),
        Color(0xFFF8BBD0),
        Color(0xFFFFAB91)
    )
    val randomColor = cardColors.random()
    Card(
        onClick = onItemNotesClicked,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = randomColor,
            contentColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = text,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF888888)
            )
        }
    }
}

@Preview
@Composable
fun PreviewNotesCard() {
    NotesCard(
        title = "Design Sprint Notes",
        text = "May 12, 2025",
        onItemNotesClicked = {}
    )
}



