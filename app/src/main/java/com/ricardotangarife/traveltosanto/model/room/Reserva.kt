package com.ricardotangarife.traveltosanto.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabla_reservas")
class Reserva(
    @PrimaryKey @ColumnInfo(name = "id")val id: String = "",
    @ColumnInfo(name= "id_user") val id_user: String = "",
    @ColumnInfo(name = "titulo") val titulo: String = "",
    @ColumnInfo(name = "descripcion") val descripcion: String = "",
    @ColumnInfo(name = "fecha_ingreso") val fecha_ingreso : String = "",
    @ColumnInfo(name = "fecha_salida") val fecha_salida : String = "",
    @ColumnInfo(name = "precio") val precio : Int = 0
)