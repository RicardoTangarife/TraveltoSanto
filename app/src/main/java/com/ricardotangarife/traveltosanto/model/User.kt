package com.ricardotangarife.traveltosanto.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabla usuario")
class User (
    @PrimaryKey @ColumnInfo(name = "id") val id: String = "",
    @ColumnInfo(name = "nombre") val nombre: String = "",
    @PrimaryKey @ColumnInfo(name = "correo") val correo: String = "",
    @ColumnInfo(name = "movil") val movil: String = "",
    @ColumnInfo(name = "password") val password: String = ""
)
