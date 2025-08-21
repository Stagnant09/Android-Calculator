package com.example.calculator.foundation

interface CustomViewModel<T: CustomState, R: CustomEvent> {
    fun setState(state: T) : Unit

    fun setEvent(event: R) : Unit

    fun handleEvent(event: R)
}