package com.qamar.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.qamar.myapplication.ui.theme.DynamicColor
import com.qamar.myapplication.ui.theme.DynamicColorOnline
import com.qamar.myapplication.ui.theme.MyApplicationTheme
import kotlin.math.roundToInt


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        repeat(3) {
                            StaticImage(
                                when (it) {
                                    0 -> R.drawable.pro1
                                    1 -> R.drawable.pro2
                                    else -> R.drawable.pro3
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StaticImage(img: Int) {
    val context = LocalContext.current
    var dynamicColor: DynamicColor? = null
    LaunchedEffect(Unit) {
        dynamicColor = DynamicColor(img, context)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        Box(
            modifier = Modifier
                .wrapContentWidth()
                .height(250.dp)
        ) {
            Image(
                painter = painterResource(id = img), contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
        }
        val density = LocalDensity.current
        val shapeSize = density.run { 78.dp.toPx() }
        val cutCornerShape = RoundedCornerShape(60)
        val outline = cutCornerShape.createOutline(
            Size(shapeSize, shapeSize),
            LocalLayoutDirection.current,
            density
        )
        var outlineHeight = 0f
        Box(
            Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(90.dp)
                .drawWithContent {
                    with(drawContext.canvas.nativeCanvas) {
                        saveLayer(null, null)
                        outlineHeight = outline.bounds.height
                        drawContent()
                        withTransform(
                            {
                                translate(
                                    left = outline.bounds.width / 3,
                                    top = -outlineHeight / 3
                                )
                            }
                        ) {
                            drawOutline(
                                outline = outline,
                                color = Color.Transparent,
                                blendMode = BlendMode.Clear
                            )

                        }
                    }
                }
                .offset(y = 15.dp)
                .blur(radiusX = 80.dp, radiusY = 15.dp)
                .alpha(0.98f)
        ) {
            Image(
                painterResource(id = img),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(
                    dynamicColor?.imageDynamicColor ?: Color.Transparent,
                    BlendMode.Dst
                )

            )
        }


        FloatingActionButton(
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(60.dp),
            containerColor = Color.White,
            modifier = Modifier
                .size(68.dp)
                .scale(0.9f)
                .align(Alignment.BottomStart)
                .layout { measurable, constraints ->
                    val placeable = measurable.measure(constraints)
                    layout(placeable.width, placeable.height) {
                        placeable.place(
                            IntOffset(
                                (outline.bounds.width / 2.2).roundToInt(),
                                (-outlineHeight / 3).roundToInt()
                            )
                        )
                    }
                }
                .offset(y = -(50.dp))

        ) {

        }
        Column(
            Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 10.dp)
        ) {
            Text(
                text = "Title Text Color",
                color = Color.White
            )
            Text(
                text = "Title Text Color",
                color = Color.White
            )


        }

    }
}

@Composable
fun OnlineImage(img: String) {
    var dynamicColor: DynamicColorOnline? = null
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        dynamicColor = DynamicColorOnline(
            img,
            context,
            scope
        )
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        Box(
            modifier = Modifier
                .wrapContentWidth()
                .height(250.dp)
        ) {
            AsyncImage(
                model = img, contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
        }
        val density = LocalDensity.current
        val shapeSize = density.run { 78.dp.toPx() }
        val cutCornerShape = RoundedCornerShape(60)
        val outline = cutCornerShape.createOutline(
            Size(shapeSize, shapeSize),
            LocalLayoutDirection.current,
            density
        )
        var outlineHeight = 0f
        Box(
            Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(90.dp)
                .drawWithContent {
                    with(drawContext.canvas.nativeCanvas) {
                        val checkPoint = saveLayer(null, null)
                        outlineHeight = outline.bounds.height
                        drawContent()
                        withTransform(
                            {
                                translate(
                                    left = outline.bounds.width / 3,
                                    top = -outlineHeight / 3
                                )
                            }
                        ) {
                            drawOutline(
                                outline = outline,
                                color = Color.Transparent,
                                blendMode = BlendMode.Clear
                            )

                        }
                    }
                }
                .offset(y = 15.dp)
                .blur(radiusX = 80.dp, radiusY = 15.dp)
                .alpha(0.98f)
        ) {
            AsyncImage(
                model = img, contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(
                    dynamicColor?.imageDynamicColor ?: Color.Transparent,
                    BlendMode.Dst
                )

            )
        }


        FloatingActionButton(
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(60.dp),
            containerColor = Color.White,
            modifier = Modifier
                .size(68.dp)
                .scale(0.9f)
                .align(Alignment.BottomStart)
                .layout { measurable, constraints ->
                    val placeable = measurable.measure(constraints)
                    layout(placeable.width, placeable.height) {
                        //  placeable.placeRelative(x.roundToPx(), y.roundToPx())
                        placeable.place(
                            IntOffset(
                                (outline.bounds.width / 2.2).roundToInt(),
                                (-outlineHeight / 3).roundToInt()
                            )
                        )
                    }
                }
                .offset(y = -(50.dp))

        ) {

        }
        Column(
            Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 10.dp)
        ) {
            Text(
                text = "Title Text Color",
                color = Color.White
            )
            Text(
                text = "Title Text Color",
                color = Color.White
            )


        }

    }
}


@Preview(showBackground = true)
@Composable
fun StaticImagePreview() {
    MyApplicationTheme {
        StaticImage(R.drawable.pro1)
    }
}

@Preview(showBackground = true)
@Composable
fun StaticImage1Preview() {
    MyApplicationTheme {
        StaticImage(R.drawable.pro2)
    }
}

@Preview(showBackground = true)
@Composable
fun StaticImage3Preview() {
    MyApplicationTheme {
        StaticImage(R.drawable.pro3)
    }
}
