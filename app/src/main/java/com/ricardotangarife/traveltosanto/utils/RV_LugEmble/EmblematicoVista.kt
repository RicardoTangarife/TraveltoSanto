package com.ricardotangarife.traveltosanto.utils.RV_LugEmble

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sitios_caracteristicos")
class EmblematicoVista (
    @PrimaryKey @ColumnInfo(name = "id") val id: String= "",
    @ColumnInfo(name = "imagen") val imagen:String = ""
)