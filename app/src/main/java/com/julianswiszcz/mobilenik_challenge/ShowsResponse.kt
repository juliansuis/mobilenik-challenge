package com.julianswiszcz.mobilenik_challenge

data class ShowsResponse(
    val showsList: List<ShowContainer>
)

data class ShowContainer(val show: Show)
