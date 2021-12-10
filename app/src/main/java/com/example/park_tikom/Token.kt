package com.example.park_tikom

data class Token(
    var id : String = "",
    var startDate : String = "",
    var startTime: String = "",
    var endTime : String = "",
    var location : String = "",
    var isCheckedIn : Boolean = false,
    var owner : String = ""
)
