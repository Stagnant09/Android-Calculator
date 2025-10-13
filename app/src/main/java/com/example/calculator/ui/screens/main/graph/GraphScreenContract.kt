package com.example.calculator.ui.screens.main.graph

import androidx.compose.ui.geometry.Offset
import com.example.calculator.foundation.CustomEffect
import com.example.calculator.foundation.CustomEvent
import com.example.calculator.foundation.CustomState
import com.example.calculator.models.Edge
import com.example.calculator.models.Node

data class GraphScreenContract(
    val dummy: String = "" // placeholder, see below
) {
    data class State(
        val nodes: List<Node> = emptyList(),
        val edges: List<Edge> = emptyList(),
        val selectedNodeId: Int? = null,
        val shortestPath: List<Int> = emptyList(),
        val isCalculating: Boolean = false,
        val errorMessage: String? = null
    ) : CustomState

    sealed interface Event : CustomEvent {
        data class AddNode(val position: Offset) : Event
        data class RemoveNode(val nodeId: Int) : Event
        data class DragNode(val nodeId: Int, val newPosition: Offset) : Event
        data class AddEdge(val from: Int, val to: Int, val weight: Float) : Event
        data class CalculateShortestPath(val startId: Int, val endId: Int) : Event

        // Optional UX-related events
        data object ClearGraph : Event
        data class SelectNode(val nodeId: Int?) : Event
    }

    sealed interface Effect : CustomEffect {
        data class ShowError(val message: String) : Effect
        data class ShowMessage(val message: String) : Effect
        data object PathCalculated : Effect
    }
}