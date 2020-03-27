package com.ricardotangarife.traveltosanto.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "reservas")
class Reservar (
    @PrimaryKey @ColumnInfo(name = "id_reserva") val id_reserva: String = "",
    @ColumnInfo(name= "id_user") val id_user: String = "",
    @ColumnInfo(name = "id_hab") val id_hab:String = "",
    @ColumnInfo(name = "CC") val CC: Int = 0,
    @ColumnInfo(name = "fecha_ingreso") val fecha_ingreso : String = "",
    @ColumnInfo(name = "fecha_salida") val fecha_salida : String = "",
    @ColumnInfo(name = "precio") val precio : Int = 0,
    @ColumnInfo(name = "telefono") val telefono : String = ""
)