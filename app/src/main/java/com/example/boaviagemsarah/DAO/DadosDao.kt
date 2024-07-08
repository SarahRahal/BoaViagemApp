package com.example.boaviagemsarah.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.boaviagemsarah.models.Dados
import kotlinx.coroutines.flow.Flow

@Dao
interface DadosDao {
    @Insert
    fun insert(dados: Dados) : Long
    @Update
    fun update(dados: Dados)
    @Upsert
    suspend fun upsert(dados: Dados) : Long
    @Query("select * from dados p order by p.id")
    fun getAll() : Flow<List<Dados>>
    @Query("select * from dados p where p.id = :id")
    suspend fun findById(id : Long) : Dados?
    @Query("select * from dados p where p.login = :login")
    suspend fun findByLogin(login : String) : Dados?
    @Delete
    fun delete (dados: Dados)
}