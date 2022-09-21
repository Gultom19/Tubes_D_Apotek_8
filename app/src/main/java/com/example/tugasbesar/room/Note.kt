package com.example.tugasbesar.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tugasbesar.entity.Obat

@Entity
data class Note (
    @PrimaryKey(autoGenerate = true)
    val obat: String,
    val jenis: String,
    val harga: Float
)
