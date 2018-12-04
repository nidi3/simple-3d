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

import guru.nidi.simple3d.model.Plane.Type.*
import kotlin.math.abs

private const val EPSILON = 1e-5

data class Plane private constructor(val normal: Vector, private val w: Double) {
    private enum class Type(val i: Int) {
        COPLANAR(0), FRONT(1), BACK(2), SPANNING(3);

        infix fun or(t: Type) = values().find { it.i == i or t.i }!!
    }

    companion object {
        fun fromPoints(a: Vector, b: Vector, c: Vector, rightHand: Boolean = true): Plane {
            val n = (if (rightHand) 1 else -1) * ((b - a) x (c - a)).unit()
            return Plane(n, n * a)
        }

        fun fromVertex(a: Vertex) = Plane(a.normal, a.normal * a.pos)
    }

    operator fun contains(point: Vector) = abs((normal * point) - w) < EPSILON

    operator fun unaryMinus() = Plane(-normal, -w)

    infix fun isParallel(p: Plane) = (normal x p.normal).length() < EPSILON

    infix fun intersect(p: Plane): Vertex {
        val n = normal x p.normal
        return when {
            n.x != 0.0 -> {
                val y = (w * p.normal.z - p.w * normal.z) / (normal.y * p.normal.z - normal.z * p.normal.y)
                val z = (w * p.normal.y - p.w * normal.y) / (normal.z * p.normal.y - normal.y * p.normal.z)
                Vertex(v(0, y, z), n)
            }
            n.y != 0.0 -> {
                val x = (w * p.normal.z - p.w * normal.z) / (normal.x * p.normal.z - normal.z * p.normal.x)
                val z = (w * p.normal.x - p.w * normal.x) / (normal.z * p.normal.x - normal.x * p.normal.z)
                Vertex(v(x, 0, z), n)
            }
            n.z != 0.0 -> {
                val x = (w * p.normal.y - p.w * normal.y) / (normal.x * p.normal.y - normal.y * p.normal.x)
                val y = (w * p.normal.x - p.w * normal.x) / (normal.y * p.normal.x - normal.x * p.normal.y)
                Vertex(v(x, y, 0), n)
            }
            else -> throw IllegalArgumentException("Parallel planes")
        }
    }

    infix fun intersect(v: Vertex): Vertex {
        val t = (w - v.pos * normal) / (v.normal * normal)
        return Vertex(v(v.pos.x + t * v.normal.x, v.pos.y + t * v.normal.y, v.pos.z + t * v.normal.z), v.normal)
    }

    infix fun and(p: Plane) = this intersect p
    infix fun and(v: Vertex) = this intersect v

    fun splitPolygon(polygon: Polygon,
                     coplanarFront: MutableList<Polygon>, coplanarBack: MutableList<Polygon>,
                     front: MutableList<Polygon>, back: MutableList<Polygon>) {
        var polygonType = COPLANAR
        val types = polygon.vertices.map { v ->
            val t = (normal * v.pos) - w
            val type = when {
                (t < -EPSILON) -> BACK
                (t > EPSILON) -> FRONT
                else -> COPLANAR
            }
            polygonType = polygonType or type
            type
        }

        when (polygonType) {
            COPLANAR -> {
                val co = if (normal * polygon.plane.normal > 0) coplanarFront else coplanarBack
                co.add(polygon)
            }
            FRONT -> front.add(polygon)
            BACK -> back.add(polygon)
            SPANNING -> {
                val f = mutableListOf<Vertex>()
                val b = mutableListOf<Vertex>()
                polygon.vertices.forEachIndexed { i, _ ->
                    val j = (i + 1) % polygon.vertices.size
                    val ti = types[i]
                    val tj = types[j]
                    val vi = polygon.vertices[i]
                    val vj = polygon.vertices[j]
                    if (ti != BACK) f.add(vi)
                    if (ti != FRONT) b.add(vi)
                    if (ti or tj == SPANNING) {
                        val t = (w - (normal * vi.pos)) / (normal * (vj.pos - vi.pos))
                        val v = vi.interpolate(vj, t)
                        f.add(v)
                        b.add(v)
                    }
                }
                if (f.size >= 3) front.add(Polygon(f))
                if (b.size >= 3) back.add(Polygon(b))
            }
        }
    }
}