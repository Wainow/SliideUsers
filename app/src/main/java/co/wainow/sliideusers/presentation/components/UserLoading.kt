package co.wainow.sliideusers.presentation.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import co.wainow.sliideusers.R

const val DEFAULT_SHIMMER_AMOUNT = 8
private const val SHIMMER_ANIMATION_INITIAL_VALUE = 0f
private const val SHIMMER_ANIMATION_TARGET_VALUE = 1000f
private const val SHIMMER_GRADIENT_WIDTH = 200f
private const val SHIMMER_ANIMATION_DURATION = 1200

@Composable
fun UsersLoadingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(6.dp))
        repeat(DEFAULT_SHIMMER_AMOUNT) {
            ShimmerUserItem()
            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}

@Composable
fun ShimmerUserItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.onSecondary)
    ) {
        ShimmerItem(
            modifier = Modifier
                .padding(4.dp)
                .size(48.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            ShimmerItem(
                modifier = Modifier
                    .width(120.dp)
                    .height(14.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            ShimmerItem(
                modifier = Modifier
                    .width(180.dp)
                    .height(12.dp)
            )
        }
    }
}

@Composable
fun ShimmerItem(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(8.dp)
) {
    val shimmerColors = listOf(
        MaterialTheme.colorScheme.surface,
        MaterialTheme.colorScheme.onSurface,
        MaterialTheme.colorScheme.surface,
    )

    val transition = rememberInfiniteTransition(label = stringResource(R.string.shimmer))
    val translateAnim by transition.animateFloat(
        initialValue = SHIMMER_ANIMATION_INITIAL_VALUE,
        targetValue = SHIMMER_ANIMATION_TARGET_VALUE,
        animationSpec = infiniteRepeatable(
            animation = tween(SHIMMER_ANIMATION_DURATION, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = stringResource(R.string.shimmer_translate)
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnim, translateAnim),
        end = Offset(translateAnim + SHIMMER_GRADIENT_WIDTH, translateAnim + SHIMMER_GRADIENT_WIDTH)
    )

    Box(
        modifier = modifier
            .background(brush, shape)
    )
}