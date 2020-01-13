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

import kotlin.js.JsName
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

fun cube(center: Vector = origin, radius: Vector = unit) = Csg(
    listOf(
        listOf(listOf(0, 4, 6, 2), listOf(-1, 0, 0)),
        listOf(listOf(1, 3, 7, 5), listOf(+1, 0, 0)),
        listOf(listOf(0, 1, 5, 4), listOf(0, -1, 0)),
        listOf(listOf(2, 6, 7, 3), listOf(0, +1, 0)),
        listOf(listOf(0, 2, 3, 1), listOf(0, 0, -1)),
        listOf(listOf(4, 5, 7, 6), listOf(0, 0, +1))
    ).map { info ->
        Polygon(info[0].map { i ->
            val pos = Vector(
                center.x + radius.x * (2 * (i and 1) - 1),
                center.y + radius.y * (2 * ((i shr 1) and 1) - 1),
                center.z + radius.z * (2 * ((i shr 2) and 1) - 1)
            )
            Vertex(pos, Vector(info[1][0].toDouble(), info[1][1].toDouble(), info[1][2].toDouble()))
        })
    })

fun prism(height: Number, vararg points: Vector) = prism(height, points.toList())

fun prism(height: Number, points: List<Vector>): Csg {
    val n = Plane.fromPoints(points).normal
    val dn = n * height.toDouble()

    fun side(p1: Vector, p2: Vector): Polygon {
        val r = n x (p1 - p2).unit()
        return Polygon(Vertex(p1, r), Vertex(p2, r), Vertex(p2 + dn, r), Vertex(p1 + dn, r))
    }

    return Csg {
        add(Polygon(points.reversed().map { Vertex(it, -n) }))
        add(Polygon(points.map { Vertex(it + dn, n) }))
        for (i in points.indices) {
            add(side(points(i), points(i + 1)))
        }
    }
}

fun prismRing(width: Number, height: Number, vararg points: Vector) =
    prismRing(width, height, points.toList())

@JsName("prismRing")
fun prismRing(width: Number, height: Number, points: List<Vector>) =
    prismRing(width.toDouble() / 2, width.toDouble() / 2, height, points)

fun prismRing(width1: Number, width2: Number, height: Number, points: List<Vector>): Csg {
    val n = Plane.fromPoints(points).normal
    val dn = n * height.toDouble()
    val w1 = width1.toDouble()
    val w2 = width2.toDouble()

    fun side(p0: Vector, p1: Vector, p2: Vector, p3: Vector): List<Polygon> {
        val r0 = n x (p1 - p0).unit()
        val r1 = n x (p2 - p1).unit()
        val r2 = n x (p3 - p2).unit()
        val e1 = Plane.fromVertex(Vertex(p1 + r1 * w1, r1))
        val f1 = Plane.fromVertex(Vertex(p1 - r1 * w2, -r1))
        val (p11, p12) = if (r0 == r1) Pair(p1 + r1 * w1, p1 - r1 * w2)
        else {
            val e0 = Plane.fromVertex(Vertex(p0 + r0 * w1, r0))
            val f0 = Plane.fromVertex(Vertex(p0 - r0 * w2, -r0))
            Pair(
                (Plane.fromVertex(Vertex(p1, n)) and (e0 and e1)).pos,
                (Plane.fromVertex(Vertex(p1, n)) and (f0 and f1)).pos
            )
        }
        val (p21, p22) = if (r1 == r2) Pair(p2 + r1 * w1, p2 - r1 * w2)
        else {
            val e2 = Plane.fromVertex(Vertex(p2 + r2 * w1, r2))
            val f2 = Plane.fromVertex(Vertex(p2 - r2 * w2, -r2))
            Pair(
                (Plane.fromVertex(Vertex(p2, n)) and (e1 and e2)).pos,
                (Plane.fromVertex(Vertex(p2, n)) and (f1 and f2)).pos
            )
        }
        return listOf(
            Polygon(Vertex(p11, r1), Vertex(p11 + dn, r1), Vertex(p21 + dn, r1), Vertex(p21, r1)),
            Polygon(Vertex(p12, -r1), Vertex(p22, -r1), Vertex(p22 + dn, -r1), Vertex(p12 + dn, -r1)),
            Polygon(Vertex(p11, -n), Vertex(p21, -n), Vertex(p22, -n), Vertex(p12, -n)),
            Polygon(Vertex(p11 + dn, n), Vertex(p12 + dn, n), Vertex(p22 + dn, n), Vertex(p21 + dn, n))
        )
    }

    return Csg {
        for (i in points.indices) {
            addAll(side(points(i), points(i + 1), points(i + 2), points(i + 3)))
        }
    }
}

fun sphere(
    center: Vector = origin, radius: Number = 1.0, slices: Int = 32, stacks: Int = 16,
    radiusFunc: ((phi: Double, theta: Double) -> Double)? = null
): Csg {
    fun vertex(phi: Double, theta: Double): Vector {
        val dir = Vector.ofSpherical(-1.0, theta * PI, phi * PI * 2)
        return center + dir * (radiusFunc?.invoke(phi, theta) ?: radius.toDouble())
    }

    return Csg {
        for (i in 0 until slices) {
            val id = i.toDouble()
            for (j in 0 until stacks) {
                val vectors = mutableListOf<Vector>()
                val jd = j.toDouble()
                vectors.add(vertex(id / slices, jd / stacks))
                if (j > 0) vectors.add(vertex((id + 1) / slices, jd / stacks))
                if (j < stacks - 1) vectors.add(vertex((id + 1) / slices, (jd + 1) / stacks))
                vectors.add(vertex(id / slices, (jd + 1) / stacks))
                add(Polygon.ofVectors(vectors))
            }
        }
    }
}

fun ellipseFunc(a: Number, b: Number): (Double) -> Double {
    val al = a.toDouble()
    val bl = b.toDouble()
    return { angle ->
        val n1 = al * cos(angle)
        val n2 = bl * sin(angle)
        al * bl / (sqrt(n1 * n1 + n2 * n2))
    }
}

fun cylinder(
    center: Vector = origin, radius: Number = 1.0, height: Number = 1.0,
    slices: Int = 32, stacks: Int = 16, radiusFunc: ((angle: Double, stack: Double) -> Double)? = null
): Csg {
    val ray = height.toDouble() * yUnit
    val start = center - .5 * ray
    val end = center + .5 * ray

    fun point(stack: Double, slice: Double): Vector {
        val angle = slice * PI * 2
        val out = xUnit * cos(angle) + zUnit * sin(angle)
        return start + ray * stack + out * (radiusFunc?.invoke(angle, stack) ?: radius.toDouble())
    }

    return Csg {
        for (i in 0 until slices) {
            val id = i.toDouble()
            val t0 = id / slices
            val t1 = (id + 1) / slices
            add(Polygon.ofVectors(start, point(0.0, t0), point(0.0, t1)))
            for (j in 0 until stacks) {
                val jd = j.toDouble()
                val j0 = jd / stacks
                val j1 = (jd + 1) / stacks
                add(Polygon.ofVectors(point(j0, t1), point(j0, t0), point(j1, t0), point(j1, t1)))
            }
            add(Polygon.ofVectors(end, point(1.0, t1), point(1.0, t0)))
        }
    }
}

fun heightModel(height: List<List<Double>>): Csg {
    val xd = height.size - 1
    val yd = height[0].size - 1
    return Csg {
        add(
            Polygon(
                Vertex(v(0, 0, 0), v(0, 0, -1)),
                Vertex(v(0, yd, 0), v(0, 0, -1)),
                Vertex(v(xd, yd, 0), v(0, 0, -1)),
                Vertex(v(xd, 0, 0), v(0, 0, -1))
            )
        )
        for (x in 0 until xd) {
            add(
                Polygon(
                    Vertex(v(x, 0, 0), v(0, -1, 0)),
                    Vertex(v(x + 1, 0, 0), v(0, -1, 0)),
                    Vertex(v(x + 1, 0, height[x + 1][0]), v(0, -1, 0)),
                    Vertex(v(x, 0, height[x][0]), v(0, -1, 0))
                )
            )
            add(
                Polygon(
                    Vertex(v(x, yd, 0), v(0, 1, 0)),
                    Vertex(v(x, yd, height[x][yd]), v(0, 1, 0)),
                    Vertex(v(x + 1, yd, height[x + 1][yd]), v(0, 1, 0)),
                    Vertex(v(x + 1, yd, 0), v(0, 1, 0))
                )
            )
        }
        for (y in 0 until yd) {
            add(
                Polygon(
                    Vertex(v(0, y, 0), v(-1, 0, 0)),
                    Vertex(v(0, y, height[0][y]), v(-1, 0, 0)),
                    Vertex(v(0, y + 1, height[0][y + 1]), v(-1, 0, 0)),
                    Vertex(v(0, y + 1, 0), v(-1, 0, 0))
                )
            )
            add(
                Polygon(
                    Vertex(v(xd, y, 0), v(1, 0, 0)),
                    Vertex(v(xd, y + 1, 0), v(1, 0, 0)),
                    Vertex(v(xd, y + 1, height[xd][y + 1]), v(1, 0, 0)),
                    Vertex(v(xd, y, height[xd][y]), v(1, 0, 0))
                )
            )
        }
        for (x in 0 until xd) {
            for (y in 0 until yd) {
                add(
                    Polygon.ofVectors(
                        v(x, y, height[x][y]),
                        v(x + 1, y, height[x + 1][y]),
                        v(x + 1, y + 1, height[x + 1][y + 1])
                    )
                )
                add(
                    Polygon.ofVectors(
                        v(x + 1, y + 1, height[x + 1][y + 1]),
                        v(x, y + 1, height[x][y + 1]),
                        v(x, y, height[x][y])
                    )
                )
            }
        }
    }
}
