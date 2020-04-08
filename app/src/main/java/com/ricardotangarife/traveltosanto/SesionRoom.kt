package com.ricardotangarife.traveltosanto

import android.app.Application
import androidx.room.Room
import com.ricardotangarife.traveltosanto.model.room.ReservaDataBase

class SesionRoom : Application() {
    companion object{
        lateinit var database: ReservaDataBase
    }

    override fun onCreate() {
        super.onCreate()
        SesionRoom.database= Room.databaseBuilder(
            this,
            ReservaDataBase::class.java,
            "reservas_Db"
        )
            .allowMainThreadQueries()
            .build()
    }
}