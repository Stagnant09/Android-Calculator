package com.example.calculator.models

import androidx.compose.ui.geometry.Offset

data class Node(
    val id: Int,
    val position: Offset,
)

data class Edge(
    val from: Int,
    val to: Int,
    val weight: Float
)

data class GraphState(
    val nodes: List<Node> = emptyList(),
    val edges: List<Edge> = emptyList(),
    val selectedNodeId: Int? = null,
    val shortestPath: List<Int> = emptyList()
)
