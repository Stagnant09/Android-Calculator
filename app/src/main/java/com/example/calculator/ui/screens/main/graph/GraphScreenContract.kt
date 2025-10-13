package com.example.calculator.ui.screens.main.graph

import androidx.compose.ui.geometry.Offset
import com.example.calculator.foundation.CustomEffect
import com.example.calculator.foundation.CustomEvent
import com.example.calculator.foundation.CustomState
import com.example.calculator.models.Edge
import com.example.calculator.models.GraphMode
import com.example.calculator.models.Node

data class GraphScreenContract(
    val dummy: String = "" // placeholder, see below
) {
    data class State(
        val nodes: List<Node> = emptyList(),
        val edges: List<Edge> = emptyList(),
        val selectedNodeId: Int? = null,
        val selectedEdge: Edge? = null,
        val mode: GraphMode = GraphMode.EditNodes,
        val draggingEdgeFrom: Int? = null,
        val draggingEdgePosition: Offset? = null,
        val shortestPath: List<Int> = emptyList(),
        val errorMessage: String? = null,
        val isEdgeBeingModified: Boolean = false
    ) : CustomState

    sealed interface Event : CustomEvent {
        data class AddNode(val position: Offset) : Event
        data class RemoveNode(val nodeId: Int) : Event
        data class DragNode(val nodeId: Int, val newPosition: Offset) : Event
        data class AddEdge(val from: Int, val to: Int, val weight: Float) : Event
        data class RemoveEdge(val edge: Edge) : Event
        data class CalculateShortestPath(val startId: Int, val endId: Int) : Event

        // UX events
        data object ClearGraph : Event
        data class SelectNode(val nodeId: Int?) : Event
        data class SelectEdge(val edge: Edge?) : Event
        data class SwitchMode(val mode: GraphMode) : Event

        data class StartDragNode(val nodeId: Int) : Event
        data class StartEdge(val nodeId: Int, val position: Offset) : Event
        data class UpdateDraggingEdge(val position: Offset) : Event
        data class UpdateEdgeDrag(val fromNodeId: Int, val position: Offset) : Event
        data object EndEdgeDrag : Event
        data object TappedPencilButon : Event
        data object DismissDialog : Event
        data class UpdateEdgeWeight(val edge: Edge?, val newWeight: Float) : Event
        data class ConfirmEdgeWeight(val edge: Edge?, val newWeight: Float) : Event
    }

    sealed interface Effect : CustomEffect {
        data class ShowError(val message: String) : Effect
        data class ShowMessage(val message: String) : Effect
        data object PathCalculated : Effect
    }
}