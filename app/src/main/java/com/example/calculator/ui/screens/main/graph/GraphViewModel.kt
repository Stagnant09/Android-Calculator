package com.example.calculator.ui.screens.main.graph

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import com.example.calculator.foundation.CustomViewModel
import com.example.calculator.models.Edge
import com.example.calculator.models.GraphMode
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
            is GraphScreenContract.Event.DragNode -> dragNode(event.nodeId, event.newPosition)
            is GraphScreenContract.Event.AddEdge -> addEdge(event.from, event.to, event.weight)
            is GraphScreenContract.Event.RemoveEdge -> removeEdge(event.edge)
            is GraphScreenContract.Event.CalculateShortestPath ->
                calculateShortestPath(event.startId, event.endId)

            GraphScreenContract.Event.ClearGraph -> clearGraph()
            is GraphScreenContract.Event.SelectNode -> selectNode(event.nodeId)
            is GraphScreenContract.Event.SelectEdge -> selectEdge(event.edge)
            is GraphScreenContract.Event.SwitchMode -> switchMode(event.mode)

            is GraphScreenContract.Event.StartDragNode -> startDragNode(event.nodeId)
            is GraphScreenContract.Event.StartEdge -> startEdge(event.nodeId, event.position)
            is GraphScreenContract.Event.UpdateDraggingEdge -> updateDraggingEdge(event.position)

            is GraphScreenContract.Event.UpdateEdgeDrag -> {
                _uiState.value = _uiState.value.copy(
                    draggingEdgeFrom = event.fromNodeId,
                    draggingEdgePosition = event.position
                )
            }

            GraphScreenContract.Event.EndEdgeDrag -> {
                _uiState.value = _uiState.value.copy(
                    draggingEdgeFrom = null,
                    draggingEdgePosition = null
                )
            }

            GraphScreenContract.Event.TappedPencilButon -> {
                _uiState.value = _uiState.value.copy(
                    isEdgeBeingModified = true
                )
            }
            is GraphScreenContract.Event.UpdateEdgeWeight -> {
                // Update the weight of the specific edge
                val updatedEdges = _uiState.value.edges.map {
                    if (it == event.edge) it.copy(weight = event.newWeight) else it
                }
                setState(
                    _uiState.value.copy(
                        edges = updatedEdges
                    )
                )
            }
            is GraphScreenContract.Event.ConfirmEdgeWeight -> {
                val updatedEdges = _uiState.value.edges.map {
                    if (it == event.edge) it.copy(weight = event.newWeight, color = event.newColor) else it
                }
                setState(
                    _uiState.value.copy(
                        edges = updatedEdges,
                        isEdgeBeingModified = false,
                        selectedEdge = null
                    )
                )
            }

            is GraphScreenContract.Event.DismissDialog -> {
                setState(_uiState.value.copy(isEdgeBeingModified = false))
            }

            is GraphScreenContract.Event.EnableBottomSheet -> {
                setState(_uiState.value.copy(isBottomSheetEnabled = true))
            }
            is GraphScreenContract.Event.DisableBottomSheet -> {
                setState(_uiState.value.copy(isBottomSheetEnabled = false))
            }


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

    private fun switchMode(mode: GraphMode) {
        _uiState.value = _uiState.value.copy(
            mode = mode,
            selectedNodeId = null,
            selectedEdge = null,
            draggingEdgeFrom = null,
            draggingEdgePosition = null
        )
    }

    private fun addEdge(from: Int, to: Int, weight: Float) {
        // Avoid duplicate edges
        if (_uiState.value.edges.any { it.from == from && it.to == to }) return
        _uiState.value = _uiState.value.copy(
            edges = _uiState.value.edges + Edge(from, to, weight),
            draggingEdgeFrom = null,
            draggingEdgePosition = null
        )
    }

    private fun removeEdge(edge: Edge) {
        _uiState.value = _uiState.value.copy(
            edges = _uiState.value.edges.filterNot { it == edge },
            selectedEdge = if (_uiState.value.selectedEdge == edge) null else _uiState.value.selectedEdge
        )
    }

    private fun selectEdge(edge: Edge?) {
        val newSelection = if (_uiState.value.selectedEdge == edge) null else edge
        _uiState.value = _uiState.value.copy(selectedEdge = newSelection)
    }

    private fun startEdge(fromNodeId: Int, position: Offset) {
        _uiState.value = _uiState.value.copy(
            draggingEdgeFrom = fromNodeId,
            draggingEdgePosition = position
        )
    }

    private fun startDragNode(nodeId: Int) {
        _uiState.value = _uiState.value.copy(selectedNodeId = nodeId)
    }

    private fun updateDraggingEdge(position: Offset) {
        _uiState.value = _uiState.value.copy(draggingEdgePosition = position)
    }

    private fun endEdgeDrag() {
        _uiState.value = _uiState.value.copy(
            draggingEdgeFrom = null,
            draggingEdgePosition = null
        )
    }

}