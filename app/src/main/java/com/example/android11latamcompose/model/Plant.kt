package com.example.android11latamcompose.model

import androidx.annotation.DrawableRes
import java.io.Serializable

data class Plant(
    val name: String,
    val shortDescription: String,
    @DrawableRes val image: Int,
    val description: String
) : Serializable