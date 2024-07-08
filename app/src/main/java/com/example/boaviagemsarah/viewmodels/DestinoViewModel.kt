package com.example.boaviagemsarah.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.boaviagemsarah.DAO.DestinoDao
import com.example.boaviagemsarah.dataBase.AppDataBase
import com.example.boaviagemsarah.models.Destino
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DestinoViewModelFactory(val db : AppDataBase) : ViewModelProvider.Factory{//tem que criar para usar o db
override fun <T : ViewModel> create(modelClass: Class<T>): T {
    return DestinoViewModel(db.destinoDao) as T
}
}
class DestinoViewModel(val destinoDao: DestinoDao) : ViewModel(){
    private val _uiState = MutableStateFlow(Destino())
    val uiState : StateFlow<Destino> = _uiState.asStateFlow()
    fun updateDestino(newDestino : String){
        _uiState.update { it.copy(destino = newDestino) }
    }
    fun updateInicio(newInicio : String){
        _uiState.update { it.copy(inicio = newInicio) }
    }
    fun updadeFim (newFim : String){
        _uiState.update { it.copy(fim = newFim) }
    }
    fun updateValor (newValor : Double){
        _uiState.update { it.copy(valor = newValor) }
    }
    fun updadeFinalidade (newFinalidade : String){
        _uiState.update { it.copy(finalidade = newFinalidade) }
    }
    private fun updateId (id : Long){
        _uiState.update {
            it.copy(id = id)
        }
    }
    fun save(){
        viewModelScope.launch {
            val id = destinoDao.upsert(uiState.value)
            if (id > 0){
                updateId(id)
            }
        }
    }
    fun saveNew() {
        save()
        new()
    }
    private fun new() {
        _uiState.update {
            it.copy(id = 0, destino = "", inicio = "", fim = "" , valor = 0.00, finalidade = "")
        }
    }
    fun getAll() = destinoDao.getAll()
    fun delet(destino: Destino) {
        viewModelScope.launch {
            destinoDao.delete(destino)
        }
    }
    suspend fun findById(id: Long) : Destino? {
        val deferred : Deferred<Destino?> = viewModelScope.async {
            destinoDao.findById(id)
        }
        return deferred.await()
    }
    fun setUiState(destino: Destino) {
        _uiState.value = uiState.value.copy(
            id = destino.id,
            destino = destino.destino,
            inicio = destino.inicio,
            fim = destino.fim,
            finalidade = destino.finalidade,
            valor = destino.valor
        )
    }
}