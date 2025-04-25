package com.flatify.android

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flatify.mangaverse.presentation.ui.theme.ButtonColor1
import com.flatify.mangaverse.presentation.ui.theme.ButtonColor2
import com.flatify.mangaverse.presentation.ui.theme.ButtonRippleColor
import com.flatify.mangaverse.presentation.ui.theme.GramatikaTrial

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .padding(start = 20.dp, end = 20.dp)
            .shadow(
                elevation = 5.dp,      // shadow size
                shape = RoundedCornerShape(16.dp), // shape matters for soft edges
                clip = false           // set to true if you want to clip the content
            )
            .clip(RoundedCornerShape(49.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(ButtonColor1, ButtonColor2)
                ))
            .fillMaxWidth()
            .clickable(
//                enabled = enabled,
//                indication = rememberRipple(
//                    bounded = true,
//                    color = ButtonRippleColor
//                ),
//                interactionSource = remember { MutableInteractionSource() },
//                role = Role.Button
            ) {
                if(enabled){
                    onClick()
                }
            }
            .padding(vertical = 12.dp, horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(vertical = 2.dp),
            text = text,
            fontSize = 16.sp,
            fontFamily = GramatikaTrial,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )
    }
}