package com.example.core.viewmodel

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent

abstract class BaseViewModel<Event : UiEvent, State : UiState, SideEffect : UiSideEffect> :
    ViewModel(), KoinComponent {

    private val initialState: State by lazy { createInitialState() }

    abstract fun createInitialState(): State

    val currentState: State
        get() = uiState.value

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState: StateFlow<State>
        get() = _uiState

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    val event: SharedFlow<Event>
        get() = _event

    private val _effect: MutableSharedFlow<SideEffect> = MutableSharedFlow()
    val effect: SharedFlow<SideEffect>
        get() = _effect

    /**
     * Start listening to Event
     */
    protected suspend fun subscribeEvents() {
        event.collect {
            handleEvent(it)
        }
    }

    /**
     * Handle each event
     */
    abstract suspend fun handleEvent(event: Event)


    /**
     * Set new Event
     */
    suspend fun setEvent(event: Event) {
        val newEvent = event
        _event.emit(newEvent)
    }


    /**
     * Set new Ui State
     */
    protected suspend fun setState(reduce: State.() -> State) {
        val newState = currentState.reduce()
        _uiState.emit(newState)
    }

    /**
     * Set new Effect
     */
    protected suspend fun setEffect(builder: () -> SideEffect) {
        val effectValue = builder()
        _effect.emit(effectValue)
    }

}