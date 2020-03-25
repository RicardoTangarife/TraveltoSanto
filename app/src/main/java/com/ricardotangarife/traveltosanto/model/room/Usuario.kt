package com.ricardotangarife.traveltosanto.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabla_usuario")
class Usuario(
    @PrimaryKey @ColumnInfo(name = "id")val id: String = "",
    @ColumnInfo(name= "nombre") val nombre: String = "",
    @ColumnInfo(name = "foto")val foto: String = ""
)