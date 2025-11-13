package com.example.calculator.ui.utils

import com.example.calculator.models.Edge
import com.example.calculator.models.Node

fun density(edges: Int, vertices: Int): Double {
    if (vertices <= 1) return 0.0
    val maxPossibleEdges = vertices * (vertices - 1)
    val density = edges.toDouble() / maxPossibleEdges
    return if (density.isFinite() && density > 0) density else 0.0
}

fun maxDegree(edges: List<Edge>): Int {
    if (edges.isEmpty()) return 0

    val degreeMap = mutableMapOf<Int, Int>()

    edges.forEach { edge ->
        degreeMap[edge.from] = (degreeMap[edge.from] ?: 0) + 1
        degreeMap[edge.to] = (degreeMap[edge.to] ?: 0) + 1
    }

    return degreeMap.values.maxOrNull() ?: 0
}

fun minDegree(edges: List<Edge>): Int {
    if (edges.isEmpty()) return 0

    val degreeMap = mutableMapOf<Int, Int>()

    edges.forEach { edge ->
        degreeMap[edge.from] = (degreeMap[edge.from] ?: 0) + 1
        degreeMap[edge.to] = (degreeMap[edge.to] ?: 0) + 1
    }

    return degreeMap.values.minOrNull() ?: 0
}

fun clusteringCoefficient(nodes: List<Node>, edges: List<Edge>): Double {
    // Build adjacency map
    val adjacency = mutableMapOf<Int, MutableSet<Int>>()
    for (edge in edges) {
        adjacency.getOrPut(edge.from) { mutableSetOf() }.add(edge.to)
        adjacency.getOrPut(edge.to) { mutableSetOf() }.add(edge.from) // assuming undirected
    }

    var totalCoefficient = 0.0
    var countedNodes = 0

    for (node in nodes) {
        val neighbors = adjacency[node.id] ?: continue
        val k = neighbors.size
        if (k < 2) continue // no possible triangles

        // Count actual edges between neighbors
        var neighborConnections = 0
        val neighborList = neighbors.toList()
        for (i in 0 until neighborList.size) {
            for (j in i + 1 until neighborList.size) {
                if (adjacency[neighborList[i]]?.contains(neighborList[j]) == true) {
                    neighborConnections++
                }
            }
        }

        val coefficient = (2.0 * neighborConnections) / (k * (k - 1))
        totalCoefficient += coefficient
        countedNodes++
    }

    return if (countedNodes > 0) totalCoefficient / countedNodes else 0.0
}
