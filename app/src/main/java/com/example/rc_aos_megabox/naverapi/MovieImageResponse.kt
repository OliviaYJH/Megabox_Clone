package com.example.rc_aos_megabox.naverapi

data class MovieImageResponse(
    val display: Int,
    val items: List<Item>,
    val lastBuildDate: String,
    val start: Int,
    val total: Int
)