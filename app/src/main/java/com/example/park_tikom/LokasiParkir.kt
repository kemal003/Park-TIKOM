package com.example.park_tikom

import java.io.Serializable

data class LokasiParkir(
    val imageUrl : String,
    val kuota : Long,
    val lokasi : String,
    val terpakai : Long,
    val deskripsi : String
) : Serializable
