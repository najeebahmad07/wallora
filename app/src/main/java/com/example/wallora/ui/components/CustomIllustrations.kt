package com.example.wallora.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wallora.ui.theme.*

/**
 * Custom Vector Illustration matching the reference design:
 * Friendly person waving beside a large rounded smartphone displaying Wallora logo & Welcome text.
 */
@Composable
fun WelcomeUserIllustration(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(280.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            // Soft organic background blobs
            drawCircle(
                color = Color(0xFFDCE7FE),
                radius = width * 0.42f,
                center = Offset(width * 0.5f, height * 0.5f)
            )
            drawCircle(
                color = Color(0xFFFFD5D3),
                radius = width * 0.16f,
                center = Offset(width * 0.82f, height * 0.22f)
            )
            drawCircle(
                color = Color(0xFF2A54E8).copy(alpha = 0.85f),
                radius = width * 0.1f,
                center = Offset(width * 0.18f, height * 0.2f)
            )
            drawCircle(
                color = Color(0xFF2A54E8).copy(alpha = 0.7f),
                radius = width * 0.04f,
                center = Offset(width * 0.12f, height * 0.34f)
            )
            drawCircle(
                color = Color(0xFFFF6B6B).copy(alpha = 0.85f),
                radius = width * 0.05f,
                center = Offset(width * 0.08f, height * 0.72f)
            )

            // Floor Shadow
            drawOval(
                color = Color(0xFF8EAEF7).copy(alpha = 0.4f),
                topLeft = Offset(width * 0.1f, height * 0.84f),
                size = Size(width * 0.8f, height * 0.12f)
            )

            // Phone Container
            val phoneWidth = width * 0.44f
            val phoneHeight = height * 0.68f
            val phoneLeft = width * 0.18f
            val phoneTop = height * 0.14f
            val cornerRad = 32f

            // Phone Body (Outer border)
            drawRoundRect(
                color = Color(0xFF2A54E8),
                topLeft = Offset(phoneLeft, phoneTop),
                size = Size(phoneWidth, phoneHeight),
                cornerRadius = CornerRadius(cornerRad, cornerRad),
                style = Stroke(width = 8f)
            )

            // Phone Screen (White Fill)
            drawRoundRect(
                color = Color.White,
                topLeft = Offset(phoneLeft + 4f, phoneTop + 4f),
                size = Size(phoneWidth - 8f, phoneHeight - 8f),
                cornerRadius = CornerRadius(cornerRad - 4f, cornerRad - 4f)
            )

            // Notch / Pill Header
            drawRoundRect(
                color = Color(0xFF2A54E8),
                topLeft = Offset(phoneLeft + phoneWidth * 0.3f, phoneTop + 14f),
                size = Size(phoneWidth * 0.4f, 12f),
                cornerRadius = CornerRadius(6f, 6f)
            )

            // Logo Circle on Phone Display
            val logoCenter = Offset(phoneLeft + phoneWidth * 0.5f, phoneTop + phoneHeight * 0.38f)
            val logoRadius = phoneWidth * 0.22f
            drawCircle(
                color = Color(0xFFFF6B6B),
                radius = logoRadius,
                center = logoCenter
            )

            // Logo Wavy Line
            val wavePath = Path().apply {
                moveTo(logoCenter.x - logoRadius * 0.5f, logoCenter.y + 4f)
                cubicTo(
                    logoCenter.x - logoRadius * 0.2f, logoCenter.y - logoRadius * 0.4f,
                    logoCenter.x + logoRadius * 0.2f, logoCenter.y + logoRadius * 0.4f,
                    logoCenter.x + logoRadius * 0.5f, logoCenter.y - 4f
                )
            }
            drawPath(
                path = wavePath,
                color = Color.White,
                style = Stroke(width = 6f)
            )

            // Person Drawing (Character on right side of phone)
            val personLeft = width * 0.62f

            // Legs (Pants - Coral/Orange)
            drawRoundRect(
                color = Color(0xFFFF6B6B),
                topLeft = Offset(personLeft + 10f, height * 0.56f),
                size = Size(20f, height * 0.28f),
                cornerRadius = CornerRadius(10f, 10f)
            )
            drawRoundRect(
                color = Color(0xFFFF6B6B),
                topLeft = Offset(personLeft + 38f, height * 0.56f),
                size = Size(20f, height * 0.28f),
                cornerRadius = CornerRadius(10f, 10f)
            )

            // Shoes (Navy Blue)
            drawRoundRect(
                color = Color(0xFF17145C),
                topLeft = Offset(personLeft, height * 0.81f),
                size = Size(32f, 16f),
                cornerRadius = CornerRadius(8f, 8f)
            )
            drawRoundRect(
                color = Color(0xFF17145C),
                topLeft = Offset(personLeft + 36f, height * 0.81f),
                size = Size(32f, 16f),
                cornerRadius = CornerRadius(8f, 8f)
            )

            // Torso (Blue Shirt)
            val shirtPath = Path().apply {
                moveTo(personLeft + 8f, height * 0.34f)
                lineTo(personLeft + 62f, height * 0.34f)
                lineTo(personLeft + 58f, height * 0.58f)
                lineTo(personLeft + 12f, height * 0.58f)
                close()
            }
            drawPath(path = shirtPath, color = Color(0xFF2A54E8))

            // Head (Skin tone)
            drawCircle(
                color = Color(0xFFD69468),
                radius = 28f,
                center = Offset(personLeft + 35f, height * 0.27f)
            )

            // Hair (Brown Curly top)
            drawCircle(
                color = Color(0xFF5A3B28),
                radius = 30f,
                center = Offset(personLeft + 35f, height * 0.23f)
            )

            // Waving Hand & Arm
            val handPath = Path().apply {
                moveTo(personLeft + 10f, height * 0.38f)
                lineTo(personLeft - 18f, height * 0.30f)
                lineTo(personLeft - 26f, height * 0.25f)
            }
            drawPath(
                path = handPath,
                color = Color(0xFF2A54E8),
                style = Stroke(width = 16f)
            )
            drawCircle(
                color = Color(0xFFD69468),
                radius = 14f,
                center = Offset(personLeft - 26f, height * 0.24f)
            )
        }

        // Overlayed text inside phone screen
        Box(
            modifier = Modifier
                .offset(x = (-12).dp, y = 30.dp)
                .size(width = 140.dp, height = 160.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                text = "Welcome",
                color = WalloraNavy,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 20.dp)
            )
        }
    }
}

/**
 * Wallora Logo Composable
 */
@Composable
fun WalloraLogo(
    modifier: Modifier = Modifier,
    size: Int = 40
) {
    Box(
        modifier = modifier
            .size(size.dp)
            .clip(RoundedCornerShape(size.dp * 0.28f))
            .background(
                Brush.linearGradient(
                    colors = listOf(WalloraPrimaryBlue, WalloraNavy)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Rounded.Wallpaper,
            contentDescription = "Wallora Logo",
            tint = WalloraWhite,
            modifier = Modifier.size((size * 0.6f).dp)
        )
    }
}

/**
 * Splash Screen Vector Artwork
 */
@Composable
fun SplashLogoIllustration(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Box(
        modifier = modifier.size(180.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val cX = size.width / 2f
            val cY = size.height / 2f

            drawCircle(
                color = WalloraLightBlue.copy(alpha = 0.25f),
                radius = (size.width / 2f) * scale,
                center = Offset(cX, cY)
            )
            drawCircle(
                color = WalloraPrimaryBlue.copy(alpha = 0.15f),
                radius = (size.width / 2.4f) * scale,
                center = Offset(cX, cY)
            )
        }

        Box(
            modifier = Modifier
                .size(100.dp)
                .shadow(16.dp, RoundedCornerShape(28.dp), spotColor = WalloraPrimaryBlue)
                .clip(RoundedCornerShape(28.dp))
                .background(
                    Brush.linearGradient(
                        colors = listOf(WalloraPrimaryBlue, WalloraNavy)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.Wallpaper,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(56.dp)
            )
        }
    }
}

/**
 * Home Hero Illustration
 */
@Composable
fun HomeHeroIllustration(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(130.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        WalloraNavy,
                        WalloraPrimaryBlue
                    )
                )
            )
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Surface(
                    color = WalloraCoral.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "NEW COLLECTIONS",
                        color = WalloraCoral,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Discover 4K Wallpapers",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "High resolution wallpapers curated daily",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 12.sp
                )
            }

            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.AutoAwesome,
                    contentDescription = null,
                    tint = WalloraCoral,
                    modifier = Modifier.size(42.dp)
                )
            }
        }
    }
}

/**
 * Empty Search Results Vector Illustration
 */
@Composable
fun EmptySearchIllustration(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(WalloraLightBlue.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.SearchOff,
                contentDescription = null,
                tint = WalloraPrimaryBlue,
                modifier = Modifier.size(56.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No Wallpapers Found",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Try searching with a different keyword like Nature, 4K, or AMOLED",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 13.sp
        )
    }
}

/**
 * Empty Favourites Vector Illustration
 */
@Composable
fun EmptyFavoritesIllustration(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(WalloraCoral.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.FavoriteBorder,
                contentDescription = null,
                tint = WalloraCoral,
                modifier = Modifier.size(56.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No Favorites Saved Yet",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Tap the heart icon on any wallpaper to add it to your personal collection.",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 13.sp
        )
    }
}
