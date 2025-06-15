package com.example.issdata

import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.text.TextPaint
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.protobuf.FloatValue
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.LocalSize
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.SizeMode
import com.example.issdata.ISSLightstreamerClient.floatValue
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.height
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import java.text.NumberFormat

class PissWidgetReceiver: GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = PissWidget()
}

class PissWidget: GlanceAppWidget() {


    override val sizeMode = SizeMode.Exact

    override suspend fun provideGlance(context: Context, id: GlanceId) {

            // In this method, load data needed to render the AppWidget.
            // Use `withContext` to switch to another thread for long running
            // operations.

        provideContent {
            GlanceTheme {
                MyContent()
            }
        }
    }
}

@Composable
private fun MyContent() {

    val size = LocalSize.current

    val fontSize = when {
        size.width < 100.dp -> 20.sp
        size.width < 200.dp -> 30.sp
        else -> 40.sp
    }

    val animatedProgress: Float by animateFloatAsState(
        floatValue,
        animationSpec = tween(
            durationMillis = 800,
            delayMillis = 200,
            easing = LinearOutSlowInEasing
        )
    )


    Column(
        modifier = GlanceModifier.fillMaxSize()
            .background(GlanceTheme.colors.background),
        verticalAlignment = Alignment.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Box(modifier = GlanceModifier.
        fillMaxSize().
        height(100.dp * animatedProgress)
            .background(GlanceTheme.colors.onBackground)) {
            Text(
                text = "${NumberFormat.getInstance().format(animatedProgress*100)}%",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = GlanceTheme.colors.onPrimary,
                    fontSize = 50.sp
                )
            )
        }
    }
}