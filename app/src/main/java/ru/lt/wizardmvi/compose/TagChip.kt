package ru.lt.wizardmvi.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.lt.wizardmvi.ui.theme.Purple40

@Composable
fun TagChip(text: String, isSelected: Boolean, onSelectedChange: (Boolean) -> Unit) {
    val shape = RoundedCornerShape(16.dp)
    Surface(
        color = if (isSelected) Purple40 else Color.LightGray,
        contentColor = if (isSelected) Color.White else Color.Black,
        shape = shape,
//        border = BorderStroke(1.dp, Color.Magenta),
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { onSelectedChange(!isSelected) })
            .padding(16.dp)
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            Text(
                text = text,
                fontSize = 24.sp,
                fontWeight = FontWeight(400)

            )
        }
    }
}
