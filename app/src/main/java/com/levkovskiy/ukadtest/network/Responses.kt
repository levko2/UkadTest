package com.levkovskiy.ukadtest.network

data class LoginResponse(

    val Tokens: Tokens,
    val UserCreationDate: String
)


data class GraphData(
    val StartDate: String,
    val Steps: Int
)

data class Tokens(
    val AuthToken: String,
    val SessionID: String,
    val SessionToken: String
)

data class ChartResponse(
    val Goal: Int,
    val GraphData: List<GraphData>
)