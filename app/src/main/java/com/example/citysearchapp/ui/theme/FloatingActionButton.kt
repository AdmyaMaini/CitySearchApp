package com.example.citysearchapp.ui.theme

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FloatingActionButton(){
    var isListIcon by remember { mutableStateOf(true) } // State to toggle icon

    IconButton(
        onClick = { isListIcon = !isListIcon }, // Toggle the icon state
        modifier = Modifier
            .size(48.dp)
            .border(3.dp, Color.Black, if (isListIcon) RoundedCornerShape(8.dp) else CircleShape)
            .padding(8.dp) ,
    ) {
        Icon(
            imageVector = if (isListIcon) Icons.AutoMirrored.Default.List else Icons.Default.Place,
            contentDescription = if (isListIcon) "List Icon" else "Map Icon",
            modifier = Modifier.size(32.dp) // Icon size
        )
    }

}