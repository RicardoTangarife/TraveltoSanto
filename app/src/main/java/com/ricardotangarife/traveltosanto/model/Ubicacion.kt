package com.ricardotangarife.traveltosanto.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ubicaciones")
class Ubicacion (
    @PrimaryKey @ColumnInfo(name = "id") val id: String = "",
    @ColumnInfo(name = "nombre") val nombre: String = "",
    @ColumnInfo(name = "lat") val lat: String = "",
    @ColumnInfo(name = "lon") val lon: String = "",
    @ColumnInfo(name = "dir") val dir: String = ""
)
