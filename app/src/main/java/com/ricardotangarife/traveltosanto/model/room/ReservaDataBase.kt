package com.ricardotangarife.traveltosanto.model.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Reserva::class], version = 1)
abstract class ReservaDataBase : RoomDatabase(){
    abstract fun ReservaDAO() : ReservaDAO
}