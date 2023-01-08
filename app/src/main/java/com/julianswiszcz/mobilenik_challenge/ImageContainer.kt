package com.julianswiszcz.mobilenik_challenge

import com.google.gson.annotations.SerializedName

data class ImageContainer(
    @SerializedName("medium") val image: String
)