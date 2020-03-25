package com.ricardotangarife.traveltosanto.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habitaciones")
class Habitacion (
    @PrimaryKey @ColumnInfo(name = "id") val id: String = "",
    @PrimaryKey @ColumnInfo(name = "titulo") val titulo: String = "",
    @ColumnInfo(name = "descripcion") val descripcion: String = "",
    @ColumnInfo(name = "dias") val dias: Int = 0,
    @ColumnInfo(name = "noches") val noches: Int = 0,
    @ColumnInfo(name = "precio") val precio: Int = 0,
    @ColumnInfo(name = "imagen") val imagen: String = "",
    @ColumnInfo(name = "visibilidad") var visibilidad : Boolean = true
)
