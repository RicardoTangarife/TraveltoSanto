package com.ricardotangarife.traveltosanto.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReservaDAO {

    @Insert
    fun insertReserva(reserva: Reserva)

    @Query("SELECT * FROM tabla_reservas WHERE id_user LIKE :id")
    fun searchReservas(id:String): Reserva

    @Query("SELECT * FROM tabla_reservas")
    fun getReservas()  : List<Reserva>

}