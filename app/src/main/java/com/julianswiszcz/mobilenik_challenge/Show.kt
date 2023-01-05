package com.julianswiszcz.mobilenik_challenge

import com.google.gson.annotations.SerializedName

data class Show(
    val id: Int,
    @SerializedName("image") val image: ImageContainer?,
    val name: String,
    val summary: String,
)