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
        fun fromPoints(a: Vector, b: Vector, c: Vector): Plane {
            val n = ((b - a) x (c - a)).unit()
            return Plane(n, n * a)
        }
    }

    operator fun contains(point: Vector) = abs((normal * point) - w) < EPSILON

    operator fun unaryMinus() = Plane(-normal, -w)

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