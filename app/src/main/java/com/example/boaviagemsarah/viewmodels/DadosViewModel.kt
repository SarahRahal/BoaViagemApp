package com.example.boaviagemsarah.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.boaviagemsarah.DAO.DadosDao
import com.example.boaviagemsarah.dataBase.AppDataBase
import com.example.boaviagemsarah.models.Dados
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DadosViewModelFactory(val db : AppDataBase) : ViewModelProvider.Factory{//tem que criar para usar o db
override fun <T : ViewModel> create(modelClass: Class<T>): T {
    return DadosViewModel(db.dadosDao) as T
}
}
class DadosViewModel (val dadosDao: DadosDao): ViewModel(){
    private val _uiState = MutableStateFlow(Dados())
    val uiState : StateFlow<Dados> = _uiState.asStateFlow()
    fun updateLogin(newLogin : String){
        _uiState.update { it.copy(login = newLogin) }
    }
    fun updateSenha(newSenha : String){
        _uiState.update { it.copy(senha = newSenha) }
    }
    fun updadeVisivel (newVisivel : Boolean){
        _uiState.update { it.copy(visivel = newVisivel) }
    }
    fun updateEmail (newEmail : String){
        _uiState.update { it.copy(email = newEmail) }
    }
    private fun updateId (id : Long){
        _uiState.update {
            it.copy(id = id)
        }
    }
    fun save(){
        viewModelScope.launch {
            val id = dadosDao.upsert(uiState.value)
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
            it.copy(id = 0, login = "", senha = "", visivel = false ,email = "")
        }
    }
    suspend fun findById(id: Long) : Dados? {
        val deferred : Deferred<Dados?> = viewModelScope.async {
            dadosDao.findById(id)
        }
        return deferred.await()
    }
    //user = deferred.await() if user.login == parameter && user.senha == parameter.senha then true
    suspend fun findByLogin(login: String, senha: String) : Long?{
        val deferred : Deferred<Dados?> = viewModelScope.async {
            dadosDao.findByLogin(login)
        }
        val user = deferred.await()
        if (login == user?.login && senha == user.senha){
            return user.id
        }else{
            return null
        }
    }
    fun setUiState(dados: Dados) {
        _uiState.value = uiState.value.copy(
            id = dados.id,
            login = dados.login,
            email = dados.email,
            senha = dados.senha,
        )
    }
}