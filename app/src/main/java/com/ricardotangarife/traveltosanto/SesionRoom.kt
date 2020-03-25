package com.ricardotangarife.traveltosanto

import android.app.Application
import androidx.room.Room
import com.ricardotangarife.traveltosanto.model.room.UsuarioDataBase

class SesionRoom : Application() {
    companion object{
        lateinit var database: UsuarioDataBase
    }

    override fun onCreate() {
        super.onCreate()
        SesionRoom.database= Room.databaseBuilder(
            this,
            UsuarioDataBase::class.java,
            "usuario_Db"
        )
            .allowMainThreadQueries()
            .build()
    }
}