package com.example.rc_aos_megabox.location

data class Addresse(
    val addressElements: List<AddressElement>,
    val distance: Double,
    val englishAddress: String,
    val jibunAddress: String,
    val roadAddress: String,
    val x: String,
    val y: String
)