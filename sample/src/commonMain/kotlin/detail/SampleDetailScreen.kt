package detail

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SampleDetailScreen() {
    Text(modifier = Modifier.padding(16.dp), text = "Details Screen", fontSize = 24.sp, fontWeight = FontWeight.Medium)
}