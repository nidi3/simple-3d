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

import kotlin.math.sign

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
        if (nonPlanar.isNotEmpty()) throw IllegalArgumentException("not all points in a plane: $nonPlanar")
    }

    operator fun unaryMinus() = Polygon(vertices.reversed().map { -it })

    fun boundingBox(): Pair<Vector, Vector> {
        var minX = Double.MAX_VALUE
        var maxX = Double.MIN_VALUE
        var minY = Double.MAX_VALUE
        var maxY = Double.MIN_VALUE
        var minZ = Double.MAX_VALUE
        var maxZ = Double.MIN_VALUE
        for (v in vertices) {
            if (v.pos.x < minX) minX = v.pos.x
            if (v.pos.x > maxX) maxX = v.pos.x
            if (v.pos.y < minY) minY = v.pos.y
            if (v.pos.y > maxY) maxY = v.pos.y
            if (v.pos.z < minZ) minZ = v.pos.z
            if (v.pos.z > maxZ) maxZ = v.pos.z
        }
        return Pair(Vector(minX, minY, minZ), Vector(maxX, maxY, maxZ))
    }

    fun size() = boundingBox().let { (it.second - it.first).abs() }

    fun toTriangles(): List<Polygon> = triangulate(vertices)
}

private const val CONCAVE = -1
private const val CONVEX = 1

private fun triangulate(points: List<Vertex>): List<Polygon> {
    val cs = points.toMutableList()
    val tris = mutableListOf<Polygon>()

    fun pred(i: Int) = if (i == 0) cs.lastIndex else i - 1
    fun succ(i: Int) = if (i == cs.lastIndex) 0 else i + 1

    fun spannedAreaSign(p1: Vertex, p2: Vertex, p3: Vertex): Int {
        val t = (p2.pos - p1.pos) x (p3.pos - p2.pos)
        return sign(t * p1.normal).toInt()
    }

    fun classify(n: Int) = spannedAreaSign(cs[pred(n)], cs[n], cs[succ(n)])

    fun addTriangle(n: Int) = try {
        tris += Polygon(cs[pred(n)], cs[n], cs[succ(n)])
    } catch (e: IllegalArgumentException) {
        //3 points in a line: ignore
    }

    val vertexType = cs.mapIndexedTo(mutableListOf()) { i, _ -> classify(i) }

    fun isEarTip(n: Int): Boolean {
        if (vertexType[n] == CONCAVE) return false
        val prev = pred(n)
        val next = succ(n)
        var i = succ(next)
        while (i != prev) {
            if (vertexType[i] != CONVEX
                    && spannedAreaSign(cs[next], cs[prev], cs[i]) >= 0
                    && spannedAreaSign(cs[prev], cs[n], cs[i]) >= 0
                    && spannedAreaSign(cs[n], cs[next], cs[i]) >= 0) return false
            i = succ(i)
        }
        return true
    }

    fun findEarTip(): Int {
        for (i in 0 until cs.size) if (isEarTip(i)) return i
        for (i in 0 until cs.size) if (vertexType[i] != CONCAVE) return i
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

        val prev = pred(n)
        val next = if (n == cs.size) 0 else n
        vertexType[prev] = classify(prev)
        vertexType[next] = classify(next)
    }

    if (cs.size == 3) {
        addTriangle(1)
    }

    return tris
}
