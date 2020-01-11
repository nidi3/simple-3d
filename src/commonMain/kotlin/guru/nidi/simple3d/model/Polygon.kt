/*
 * Copyright © 2018 Stefan Niederhauser (nidin@gmx.ch)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package guru.nidi.simple3d.model

import guru.nidi.simple3d.model.VertexType.*

data class Polygon(val vertices: List<Vertex>) {
    constructor(vararg vs: Vertex) : this(vs.asList())
    constructor(rightHand: Boolean, points: List<Vector>) : this(toVertices(rightHand, points))
    constructor(rightHand: Boolean, vararg points: Vector) : this(rightHand, points.asList())

    val plane = Plane.fromVertex(vertices[0])

    companion object {
        fun toVertices(rightHand: Boolean, points: List<Vector>): List<Vertex> {
            val n = (if (rightHand) 1 else -1) * Plane.fromPoints(points[0], points[1], points[2]).normal
            return points.map { Vertex(it, n) }
        }
    }

    init {
        val nonPlanar = vertices.filter { it.pos !in plane }
        if (nonPlanar.isNotEmpty())
            throw IllegalArgumentException("not all points in a plane: $nonPlanar")
    }

    operator fun unaryMinus() = Polygon(vertices.reversed().map { -it })

    val boundingBox: Box by lazy {
        var minX = Double.MAX_VALUE
        var maxX = Double.MIN_VALUE
        var minY = Double.MAX_VALUE
        var maxY = Double.MIN_VALUE
        var minZ = Double.MAX_VALUE
        var maxZ = Double.MIN_VALUE
        for (v in vertices) {
            val p = v.pos
            if (p.x < minX) minX = p.x
            if (p.x > maxX) maxX = p.x
            if (p.y < minY) minY = p.y
            if (p.y > maxY) maxY = p.y
            if (p.z < minZ) minZ = p.z
            if (p.z > maxZ) maxZ = p.z
        }
        Box(Vector(minX, minY, minZ), Vector(maxX, maxY, maxZ))
    }

    fun size() = boundingBox.size()

    fun toTriangles() = triangulate(vertices)

    //pos must be on a line of the polygon
    fun split(pos: Vector): Pair<Polygon, Polygon> {
        val intersect = vertices.indices.first { i -> pos in Segment(vertices[i].pos, vertices(i + 1).pos) }
        for (i in vertices.indices) {
            val ok = (i != intersect && i != vertices.succ(intersect)) && vertices.indices.all { j ->
                j == vertices.pred(i) || j == i || j == intersect ||
                        !plane.segmentsIntersect(pos, vertices[i].pos, vertices[j].pos, vertices(j + 1).pos)
            }
            if (ok) {
                val newVertex = Vertex(pos, vertices[0].normal)
                val (a, b, c, d) =
                    if (i < intersect) listOf(i, intersect + 1, i, intersect)
                    else listOf(intersect, i, intersect + 1, i)
                return Pair(
                    Polygon(vertices.subList(0, a + 1) + newVertex + vertices.subList(b, vertices.size)),
                    Polygon(vertices.subList(c, d + 1) + newVertex)
                )
            }
        }
        throw RuntimeException("Could not split polygon")
    }
}

private enum class VertexType { CONCAVE, FLAT, CONVEX }

private fun triangulate(points: List<Vertex>): List<Polygon> {
    val cs = points.toMutableList()
    val tris = mutableListOf<Polygon>()

    fun spannedAreaSign(p1: Vertex, p2: Vertex, p3: Vertex): VertexType {
        val t = ((p2.pos - p1.pos) x (p3.pos - p2.pos)) * p1.normal
        return when {
            t < 0 -> CONCAVE
            t == 0.0 -> FLAT
            t > 0 -> CONVEX
            else -> throw AssertionError()
        }
    }

    fun classify(n: Int) = spannedAreaSign(cs(n - 1), cs[n], cs(n + 1))

    fun addTriangle(n: Int) = try {
        tris += Polygon(cs(n - 1), cs[n], cs(n + 1))
    } catch (e: IllegalArgumentException) {
        //3 points in a line: ignore
    }

    val vertexType = cs.mapIndexedTo(mutableListOf()) { i, _ -> classify(i) }

    fun isEarTip(n: Int): Boolean {
        if (vertexType[n] == CONCAVE) return false
        val prev = cs.pred(n)
        val next = cs.succ(n)
        var i = cs.succ(next)
        while (i != prev) {
            if (vertexType[i] != CONVEX
                && spannedAreaSign(cs[next], cs[prev], cs[i]) != CONCAVE
                && spannedAreaSign(cs[prev], cs[n], cs[i]) != CONCAVE
                && spannedAreaSign(cs[n], cs[next], cs[i]) != CONCAVE
            ) return false
            i = cs.succ(i)
        }
        return true
    }

    fun findEarTip(): Int {
        for (i in cs.indices) if (isEarTip(i)) return i
        for (i in cs.indices) if (vertexType[i] != CONCAVE) return i
        return 0
    }

    fun cutEarTip(n: Int) {
        addTriangle(n)
        cs.removeAt(n)
        vertexType.removeAt(n)
    }

    while (cs.size > 3) {
        val n = findEarTip()
        cutEarTip(n)

        val prev = cs.pred(n)
        val next = if (n == cs.size) 0 else n
        vertexType[prev] = classify(prev)
        vertexType[next] = classify(next)
    }

    if (cs.size == 3) {
        addTriangle(1)
    }

    return tris
}
