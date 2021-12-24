package com.example.rc_aos_megabox

data class MovieData(
    val title: String,
    val rank: String,
    val buy: String,
    val star: String,
    val img: Int,
    val id: Int = IdNum(),
    val movieCd : String
    /*
    ,
    val img: String
     */

){
    companion object {
        var i = 0
        fun IdNum(): Int{
            return i++
        }
    }

}
