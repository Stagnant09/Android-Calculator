package com.example.calculator.models

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

data class Node(
    val id: Int,
    val position: Offset,
)

data class Edge(
    val from: Int,
    val to: Int,
    val weight: Float,
    val color: Color = Color(220, 220, 220, 255)
)

data class GraphState(
    val nodes: List<Node> = emptyList(),
    val edges: List<Edge> = emptyList(),
    val selectedNodeId: Int? = null,
    val shortestPath: List<Int> = emptyList()
)

enum class GraphMode { EditNodes, EditEdges }

data class EdgePreview(
    val fromNodeId: Int,
    val currentPosition: Offset
)
