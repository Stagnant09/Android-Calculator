package com.example.calculator.ui.utils

import com.example.calculator.models.Edge
import com.example.calculator.models.Node

object Dijkstra {
    fun shortestPath(nodes: List<Node>, edges: List<Edge>, start: Int, end: Int): List<Int> {
        val dist = nodes.associate { it.id to Float.POSITIVE_INFINITY }.toMutableMap()
        val prev = mutableMapOf<Int, Int?>()
        val unvisited = nodes.map { it.id }.toMutableSet()

        dist[start] = 0f

        while (unvisited.isNotEmpty()) {
            val current = unvisited.minByOrNull { dist[it] ?: Float.POSITIVE_INFINITY } ?: break
            if (current == end) break
            unvisited.remove(current)

            val neighbors = edges.filter { it.from == current }
            for (edge in neighbors) {
                val alt = dist[current]!! + edge.weight
                if (alt < dist[edge.to]!!) {
                    dist[edge.to] = alt
                    prev[edge.to] = current
                }
            }
        }

        // reconstruct path
        val path = mutableListOf<Int>()
        var u: Int? = end
        while (u != null) {
            path.add(0, u)
            u = prev[u]
        }
        return path
    }
}
