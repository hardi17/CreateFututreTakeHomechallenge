package com.createfuture.takehome.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.createfuture.takehome.data.models.ApiCharacter
import com.createfuture.takehome.data.repository.CharacterRepository
import com.createfuture.takehome.utils.DispatcherProvider
import com.createfuture.takehome.utils.Uistate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repository: CharacterRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {


    private val _uiState = MutableStateFlow<Uistate<List<ApiCharacter>>>(Uistate.Loading)
    val uiState: StateFlow<Uistate<List<ApiCharacter>>> = _uiState

    private val _characterName = MutableStateFlow("")
    val characterName: StateFlow<String> = _characterName

    private val _filterCharacterList = MutableStateFlow<List<ApiCharacter>>(emptyList())
    val filterCharacterList: StateFlow<List<ApiCharacter>> = _filterCharacterList

    init {
        loadCharacters()
    }

    fun filterCharacterListOnSearch(query: String = _characterName.value) {
        _characterName.value = query

        val currentCharacterList = (uiState.value as? Uistate.Success)?.result ?: emptyList()

        _filterCharacterList.value =
            if (query.isNotEmpty()) {
                currentCharacterList.filter { it.name.startsWith(query, ignoreCase = true) }
            } else {
                currentCharacterList
            }
    }

    private fun loadCharacters(){
        viewModelScope.launch (dispatcherProvider.main){
            repository.fetchCharacters()
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    _uiState.value = Uistate.Error(e.message.toString())
                }
                .collect {
                    _uiState.value = Uistate.Success(it)
                    _filterCharacterList.value = it
                }
        }
    }
}