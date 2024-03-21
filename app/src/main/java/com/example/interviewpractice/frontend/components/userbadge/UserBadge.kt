package com.example.interviewpractice.frontend.components.userbadge

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.interviewpractice.R
import kotlinx.coroutines.delay

@Composable
fun UserBadgeDisplay(badgeInfo: MutableMap<String, Int>){
    var isExpanded by remember { mutableStateOf(false) }

    Column() {
        Text(
            text = "Your Badges",
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        if (!isExpanded) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .padding(horizontal = 8.dp)
                    .padding(vertical = 4.dp)
                    .clip(CircleShape).background(Color.White)
                    .border(
                        BorderStroke(width = 4.dp, color = Color.Gray),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    if ((badgeInfo["questionsAnswered"] ?: 0) >= 10) {
                        UserBadgePreview(
                            painter = painterResource(id = R.drawable.ten),
                            contentDescription = "Badge", modifier = Modifier.weight(1f),
                            flair = Color(0xFFCD7F32)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    if ((badgeInfo["questionsAnswered"] ?: 0) >= 25) {
                        UserBadgePreview(
                            painter = painterResource(id = R.drawable.twentyfive),
                            contentDescription = "Badge", modifier = Modifier.weight(1f),
                            flair = Color(0xFFC0C0C0)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    if ((badgeInfo["questionsAnswered"] ?: 0) >= 100) {
                        UserBadgePreview(
                            painter = painterResource(id = R.drawable.onehundred),
                            contentDescription = "Badge", modifier = Modifier.weight(1f),
                            flair = Color(0xFFFFD700)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier.clip(RoundedCornerShape(64.dp)).background(Color.White)
                    .border(
                        BorderStroke(width = 4.dp, color = Color.Gray),
                        shape = RoundedCornerShape(64.dp)
                    )
            ){
                if ((badgeInfo["questionsAnswered"] ?: 0) >= 10) {
                    UserBadge(
                        painter = painterResource(id = R.drawable.ten),
                        contentDescription = "Badge",
                        flair = Color(0xFFCD7F32),
                        description = "You have answered 10 questions!"
                    )
                    HorizontalDivider(modifier = Modifier.fillMaxWidth())
                }
                if ((badgeInfo["questionsAnswered"] ?: 0) >= 25) {
                    UserBadge(
                        painter = painterResource(id = R.drawable.twentyfive),
                        contentDescription = "Badge",
                        flair = Color(0xFFC0C0C0),
                        description = "You have answered 25 questions!"
                    )
                    HorizontalDivider(modifier = Modifier.fillMaxWidth())
                }
                if ((badgeInfo["questionsAnswered"] ?: 0) >= 100) {
                    UserBadge(
                        painter = painterResource(id = R.drawable.onehundred),
                        contentDescription = "Badge",
                        flair = Color(0xFFFFD700),
                        description = "You have answered 100 questions!"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(35.dp)
                .padding(horizontal = 8.dp)
                .padding(vertical = 4.dp)
                .clickable { isExpanded = !isExpanded },
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize(),
            ) {
                HorizontalDivider(modifier = Modifier.width(75.dp))
                Text(
                    text = if (!isExpanded) "Expand ↓" else "Collapse ↑",
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                HorizontalDivider(modifier = Modifier.width(75.dp))
            }
        }
    }
}

@Composable
fun UserBadge(
    painter: Painter,
    contentDescription: String?,
    flair: Color,
    description: String
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.weight(1f)
            .size(150.dp),
            contentAlignment = Alignment.Center)
        {
            ParticleCanvas(flair = flair, longer = true)
            Box(
                modifier = Modifier
                    .size(125.dp)
                    .padding(16.dp)
                    .clip(CircleShape)
                    .background(color = Color.LightGray)
                    .border(
                        width = 4.dp,
                        color = flair,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painter,
                    contentDescription = contentDescription,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )
            }
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .weight(1f)
        ) {
            Text(
                text = description,
                modifier = Modifier
                    .fillMaxWidth(),
                maxLines = 5,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
@Composable
fun UserBadgePreview(
    painter: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    flair: Color
) {

    Box(modifier = modifier
        .size(200.dp),
        contentAlignment = Alignment.Center)
    {
        ParticleCanvas(flair = flair, longer = false)
        Box(
            modifier = modifier
                .size(120.dp)
                .padding(16.dp)
                .clip(CircleShape).background(color = Color.LightGray)
                .border(
                    BorderStroke(width = 4.dp, color = flair),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                modifier = Modifier.size(100.dp).clip(CircleShape),
            )
        }
    }
}

@Composable
fun ParticleCanvas(flair: Color, longer: Boolean) {
    var particles by remember { mutableStateOf(listOf<Particle>()) }

    LaunchedEffect(Unit) {
        while (true) {
            val particle = Particle(
                x = 100f,
                y = 100f,
                size = (Math.random() * 15 + 1).toFloat(),
                speedX = (Math.random() * 3 - 1.5).toFloat(),
                speedY = (Math.random() * 3 - 1.5).toFloat(),
                angle = (Math.random() * 360).toFloat(),
                color = flair,
                lifespan = if (longer) 2500L else 1500L
            )
            particles = particles + particle
            delay(25) // Adjust this delay to control the speed of particles
        }
    }

    Canvas(modifier = Modifier.size(85.dp)) {
        particles = particles.filter { !it.isExpired() }

        particles.forEachIndexed { index, particle ->
            particle.move()
            drawParticle(particle)
        }
    }
}

fun DrawScope.drawParticle(particle: Particle) {
    drawRect(
        color = particle.color,
        topLeft = Offset(particle.x - particle.size / 2, particle.y - particle.size / 2),
        size = androidx.compose.ui.geometry.Size(particle.size, particle.size),
    )
}

data class Particle(
    var x: Float,
    var y: Float,
    val size: Float,
    val speedX: Float,
    val speedY: Float,
    var angle: Float,
    val color: Color,
    val lifespan: Long
) {
    private val startTime = System.currentTimeMillis()

    fun move() {
        val elapsedTime = System.currentTimeMillis() - startTime
        val fraction = elapsedTime.toFloat() / lifespan.toFloat()

        x += speedX
        y += speedY
        angle += 5 * fraction
    }

    fun isExpired(): Boolean {
        return System.currentTimeMillis() - startTime >= lifespan
    }
}

