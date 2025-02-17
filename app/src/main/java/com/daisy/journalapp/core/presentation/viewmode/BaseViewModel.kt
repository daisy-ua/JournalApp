package com.daisy.journalapp.core.presentation.viewmode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface UiState

interface UiAction

interface UiEffect

abstract class BaseViewModel<State : UiState, Action : UiAction, Effect : UiEffect>(
    initialState: State
) : ViewModel() {
    private val _state: MutableStateFlow<State> = MutableStateFlow(initialState)
    val state: StateFlow<State> get() = _state

    private val _action: MutableSharedFlow<Action> = MutableSharedFlow()
    val action: SharedFlow<Action> get() = _action

    private val _effect: Channel<Effect> = Channel(Channel.CONFLATED)
    val effect: Flow<Effect> get() = _effect.receiveAsFlow()

    val currentState: State
        get() = state.value

    protected fun updateState(update: State.() -> State) {
        _state.update(update)
    }

    protected fun setAction(action: Action) {
        val newAction = action
        viewModelScope.launch { _action.emit(newAction) }
    }

    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        viewModelScope.launch { _effect.send(effectValue) }
    }

    abstract fun onAction(action: Action)
}