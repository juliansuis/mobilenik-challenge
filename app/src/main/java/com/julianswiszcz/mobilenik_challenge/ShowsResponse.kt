package com.julianswiszcz.mobilenik_challenge

data class ShowsResponse(
    val showsList: List<QueryResponse>
)

data class QueryResponse(val score: Float, val show: Show)
