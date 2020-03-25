package com.ricardotangarife.traveltosanto.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UsuarioDAO {

    @Insert
    fun insertUsuario(usuario: Usuario)

    @Query("SELECT * FROM tabla_usuario WHERE nombre LIKE :nombre")
    fun searchUsuario(nombre:String): Usuario
}