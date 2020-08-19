package com.example.android11latamcompose.sections.detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.compose.animation.animate
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.example.android11latamcompose.model.Plant
import com.example.android11latamcompose.ui.purple200
import com.example.android11latamcompose.ui.typography

class PlantDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val plant = intent.getSerializableExtra(EXTRA_PARAMS) as Plant

        setContent {
            PlantDetailScreen(plant)
        }
    }

    @Composable
    fun PlantDetailScreen(plant: Plant) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(plant.name)
                    },
                    navigationIcon = {
                        IconButton(onClick = { finish() }) {
                            Icon(Icons.Filled.ArrowBack)
                        }
                    }
                )
            },
            bodyContent = {
                PlantDetail(plant)
            }
        )
    }

    companion object {
        const val EXTRA_PARAMS = "EXTRA_PLANT_DETAIL_PLANT"

        fun start(context: Context, plant: Plant) {
            val intent = Intent(context, PlantDetailActivity::class.java)
            intent.putExtra(EXTRA_PARAMS, plant)
            context.startActivity(intent)
        }
    }
}

@Composable
private fun PlantDetail(plant: Plant) {
    ScrollableColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalGravity = Alignment.CenterHorizontally
    ) {
        PlantAvatar(plant.image)
        Text(
            text = "${plant.shortDescription}",
            style = typography.h6,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
        )
        Text(
            text = "${plant.description}",
            style = typography.body1,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
        )
        LikedIndicator()
    }
}

@Composable
fun PlantAvatar(@DrawableRes image: Int) {

    val resource = imageResource(id = image)
    Image(
        resource,
        modifier = Modifier.size(240.dp)
            .padding(16.dp)
            .drawShadow(8.dp, CircleShape),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun LikedIndicator() {
    val selected = mutableStateOf(false)
    IconButton(
        onClick = { selected.value = !selected.value },
        Modifier.size(72.dp)
    ) {
        val icon = if (selected.value) Icons.Default.Star else Icons.Default.StarBorder
        val color = animate(if (selected.value) purple200 else Color.Black)
        Icon(icon, tint = color)
    }
}