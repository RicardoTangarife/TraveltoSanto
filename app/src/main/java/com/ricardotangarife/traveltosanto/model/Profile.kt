package com.ricardotangarife.traveltosanto.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "perfil")
class Profile (
    @PrimaryKey @ColumnInfo(name = "id") val id: String = "",
    @ColumnInfo(name= "id_user") val id_user: String = "",
    @ColumnInfo(name = "imagen") val imagen: String = ""
)
