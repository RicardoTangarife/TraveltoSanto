package com.ricardotangarife.traveltosanto.utils.RV_LugEmble

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "detalle")
class DetalleTurismo (
    @PrimaryKey @ColumnInfo(name = "id") val id: String ="",
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "descripcion") val descripcion : String = ""
)