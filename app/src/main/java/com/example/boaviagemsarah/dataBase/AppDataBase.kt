package com.example.boaviagemsarah.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.boaviagemsarah.DAO.DadosDao
import com.example.boaviagemsarah.DAO.DestinoDao
import com.example.boaviagemsarah.models.Dados
import com.example.boaviagemsarah.models.Destino

@Database(entities = [Dados::class, Destino::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract val dadosDao: DadosDao
    abstract val destinoDao : DestinoDao
    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null
        fun getDatabase(context : Context): AppDataBase = INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context,
                AppDataBase::class.java,
                "appdata-db"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}