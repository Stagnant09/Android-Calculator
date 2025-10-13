package com.example.calculator.ui.screens.main.graph

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import com.example.calculator.foundation.CustomViewModel
import com.example.calculator.models.Edge
import com.example.calculator.models.Node
import com.example.calculator.ui.utils.Dijkstra
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GraphViewModel :
    CustomViewModel<
            GraphScreenContract.State,
            GraphScreenContract.Event,
            GraphScreenContract.Effect
            >,
    ViewModel() {

    private val _uiState = MutableStateFlow(GraphScreenContract.State())
    val uiState = _uiState.asStateFlow()

    override fun setState(state: GraphScreenContract.State) {
        _uiState.value = state
    }

    override fun setEvent(event: GraphScreenContract.Event) {
        handleEvent(event)
    }

    override fun handleEvent(event: GraphScreenContract.Event) {
        when (event) {
            is GraphScreenContract.Event.AddNode -> addNode(event.position)
            is GraphScreenContract.Event.RemoveNode -> removeNode(event.nodeId)
            is GraphScreenContract.Event.AddEdge -> addEdge(event.from, event.to, event.weight)
            is GraphScreenContract.Event.DragNode -> dragNode(event.nodeId, event.newPosition)
            is GraphScreenContract.Event.CalculateShortestPath ->
                calculateShortestPath(event.startId, event.endId)
            GraphScreenContract.Event.ClearGraph -> clearGraph()
            is GraphScreenContract.Event.SelectNode -> selectNode(event.nodeId)
        }
    }

    private fun addNode(position: Offset) {
        val newId = (_uiState.value.nodes.maxOfOrNull { it.id } ?: 0) + 1
        _uiState.value = _uiState.value.copy(
            nodes = _uiState.value.nodes + Node(newId, position)
        )
    }

    private fun removeNode(id: Int) {
        _uiState.value = _uiState.value.copy(
            nodes = _uiState.value.nodes.filterNot { it.id == id },
            edges = _uiState.value.edges.filterNot { it.from == id || it.to == id }
        )
    }

    private fun dragNode(id: Int, newPosition: Offset) {
        _uiState.value = _uiState.value.copy(
            nodes = _uiState.value.nodes.map {
                if (it.id == id) it.copy(position = newPosition) else it
            }
        )
    }

    private fun addEdge(from: Int, to: Int, weight: Float) {
        _uiState.value = _uiState.value.copy(
            edges = _uiState.value.edges + Edge(from, to, weight)
        )
    }

    private fun calculateShortestPath(startId: Int, endId: Int) {
        val path = Dijkstra.shortestPath(_uiState.value.nodes, _uiState.value.edges, startId, endId)
        _uiState.value = _uiState.value.copy(shortestPath = path)
    }

    private fun clearGraph() {
        _uiState.value = _uiState.value.copy(
            nodes = emptyList(),
            edges = emptyList(),
            shortestPath = emptyList(),
            selectedNodeId = null,
            errorMessage = null
        )
    }

    private fun selectNode(nodeId: Int?) {
        // Toggle selection if the same node is tapped again
        val newSelection = if (_uiState.value.selectedNodeId == nodeId) null else nodeId
        _uiState.value = _uiState.value.copy(selectedNodeId = newSelection)
    }
}