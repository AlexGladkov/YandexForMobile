package detail

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.yandex.sample.SampleRes

@Composable
fun SampleDetailScreen() {
    Text(
        modifier = Modifier.padding(16.dp).windowInsetsPadding(WindowInsets.systemBars),
        text = SampleRes.string.sample_details_screen,
        fontSize = 24.sp,
        fontWeight = FontWeight.Medium
    )
}