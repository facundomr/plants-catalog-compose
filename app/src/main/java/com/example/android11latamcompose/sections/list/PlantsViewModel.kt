package com.example.android11latamcompose.sections.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.android11latamcompose.data.PlantsRepository
import com.example.android11latamcompose.model.Plant

class PlantsViewModel : ViewModel() {

    val state = liveData {
        emit(State.Loading)
        emit(State.Success(PlantsRepository.getAll()))
    }


    sealed class State {
        object Loading : State()
        class Success(val plants: List<Plant>) : State()
    }

}