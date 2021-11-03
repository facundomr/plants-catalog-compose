package com.example.android11latamcompose.sections.list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.android11latamcompose.R
import com.example.android11latamcompose.model.Plant
import com.example.android11latamcompose.sections.detail.PlantDetailActivity
import com.example.android11latamcompose.ui.Android11LATAMComposeTheme
import com.example.android11latamcompose.ui.typography

class PlantsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Android11LATAMComposeTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(title = {
                            Text(getString(R.string.plants_list_title))
                        })
                    },
                    content = {
                        PlantsScreenContent()
                    }
                )
            }
        }
    }

    @Composable
    fun PlantsScreenContent() {

        val viewModel = viewModels<PlantsViewModel>().value
        val state = viewModel.state.observeAsState()

        when (state.value) {
            is PlantsViewModel.State.Loading -> LoadingScreen()
            is PlantsViewModel.State.Success -> {
                PlantsList(
                    plants = (state.value as PlantsViewModel.State.Success).plants,
                    onPlantClicked = {
                        PlantDetailActivity.start(this@PlantsActivity, it)
                    })
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        CircularProgressIndicator(
            Modifier
                .wrapContentHeight()
                .wrapContentWidth()
        )
    }
}

@Composable
fun PlantsList(plants: List<Plant>, onPlantClicked: (Plant) -> Unit) {
    LazyColumn() {

        items(plants) { plant ->
            PlantCard(plant, onPlantClicked)
        }
    }
}

@Composable
fun PlantCard(plant: Plant, callback: (Plant) -> Unit) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(onClick = {
                callback(plant)
            })
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "${plant.name}",
                style = typography.h6
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${plant.shortDescription}",
                style = typography.body2
            )
        }
    }
}
