package com.example.rc_aos_megabox

data class BoxOfficeResult(
    val boxofficeType: String,
    val dailyBoxOfficeList: List<DailyBoxOffice>,
    val showRange: String
)