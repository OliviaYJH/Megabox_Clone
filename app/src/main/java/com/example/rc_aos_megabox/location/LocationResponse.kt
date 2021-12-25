package com.example.rc_aos_megabox.location

data class LocationResponse(
    val addresses: List<Addresse>,
    val errorMessage: String,
    val meta: Meta,
    val status: String
)