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
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

fun cube(center: Vector = unit, radius: Vector = unit) = Csg(
    listOf(
        listOf(listOf(0, 4, 6, 2), listOf(-1, 0, 0)),
        listOf(listOf(1, 3, 7, 5), listOf(+1, 0, 0)),
        listOf(listOf(0, 1, 5, 4), listOf(0, -1, 0)),
        listOf(listOf(2, 6, 7, 3), listOf(0, +1, 0)),
        listOf(listOf(0, 2, 3, 1), listOf(0, 0, -1)),
        listOf(listOf(4, 5, 7, 6), listOf(0, 0, +1))
    )
        .map { info ->
            Polygon(info[0].map { i ->
                val pos = Vector(
                    center.x + radius.x * (2 * (i and 1) - 1),
                    center.y + radius.y * (2 * ((i shr 1) and 1) - 1),
                    center.z + radius.z * (2 * ((i shr 2) and 1) - 1)
                )
                Vertex(pos, Vector(info[1][0].toDouble(), info[1][1].toDouble(), info[1][2].toDouble()))
            })
        })

fun prism(length: Double, rightHand: Boolean, vararg points: Vector) = prism(length, rightHand, points.toList())

fun prism(length: Double, rightHand: Boolean, points: List<Vector>): Csg {
    val p = Plane.fromPoints(points[0], points[1], points[2], rightHand)
    if (points.any { it !in p }) throw IllegalArgumentException("not all points in a plane")
    val n = p.normal
    val dn = n * length

    fun side(p1: Vector, p2: Vector): Polygon {
        val r = n x (p1 - p2).unit()
        return Polygon(Vertex(p1, r), Vertex(p2, r), Vertex(p2 + dn, r), Vertex(p1 + dn, r))
    }

    return Csg.ofPolygons {
        add(Polygon(points.reversed().map { Vertex(it, -n) }))
        add(Polygon(points.map { Vertex(it + dn, n) }))
        for (i in 0 until points.size) {
            add(side(points(i), points(i + 1)))
        }
    }
}

fun prismRing(width: Double, length: Double, rightHand: Boolean, vararg points: Vector) =
    prismRing(width, length, rightHand, points.toList())

@JsName("prismRing")
fun prismRing(width: Double, length: Double, rightHand: Boolean, points: List<Vector>) = prismRing(width, width, length, rightHand, points)

fun prismRing(w1: Double, w2: Double, length: Double, rightHand: Boolean, points: List<Vector>): Csg {
    val p = Plane.fromPoints(points[0], points[1], points[2], rightHand)
    if (points.any { it !in p }) throw IllegalArgumentException("not all points in a plane")
    val n = p.normal
    val dn = n * length

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
            Pair((Plane.fromVertex(Vertex(p1, n)) and (e0 and e1)).pos, (Plane.fromVertex(Vertex(p1, n)) and (f0 and f1)).pos)
        }
        val (p21, p22) = if (r1 == r2) Pair(p2 + r1 * w1, p2 - r1 * w2)
        else {
            val e2 = Plane.fromVertex(Vertex(p2 + r2 * w1, r2))
            val f2 = Plane.fromVertex(Vertex(p2 - r2 * w2, -r2))
            Pair((Plane.fromVertex(Vertex(p2, n)) and (e1 and e2)).pos, (Plane.fromVertex(Vertex(p2, n)) and (f1 and f2)).pos)
        }
        return listOf(
            Polygon(Vertex(p11, r1), Vertex(p11 + dn, r1), Vertex(p21 + dn, r1), Vertex(p21, r1)),
            Polygon(Vertex(p12, -r1), Vertex(p22, -r1), Vertex(p22 + dn, -r1), Vertex(p12 + dn, -r1)),
            Polygon(Vertex(p11, -n), Vertex(p21, -n), Vertex(p22, -n), Vertex(p12, -n)),
            Polygon(Vertex(p11 + dn, n), Vertex(p12 + dn, n), Vertex(p22 + dn, n), Vertex(p21 + dn, n))
        )
    }

    return Csg.ofPolygons {
        for (i in 0 until points.size) {
            addAll(side(points(i), points(i + 1), points(i + 2), points(i + 3)))
        }
    }
}

fun sphere(
    center: Vector = unit, radius: Double = 1.0, slices: Int = 32, stacks: Int = 16,
    radiusFunc: ((Double, Double) -> Double)? = null
): Csg {
    fun vertex(phi: Double, theta: Double): Vertex {
        val dir = Vector.ofSpherical(-1.0, theta * PI, phi * PI * 2)
        return Vertex(center + dir * (radiusFunc?.invoke(phi, theta) ?: radius), dir)
    }

    return Csg.ofPolygons {
        for (i in 0 until slices) {
            val id = i.toDouble()
            for (j in 0 until stacks) {
                val vertices = mutableListOf<Vertex>()
                val jd = j.toDouble()
                vertices.add(vertex(id / slices, jd / stacks))
                if (j > 0) vertices.add(vertex((id + 1) / slices, jd / stacks))
                if (j < stacks - 1) vertices.add(vertex((id + 1) / slices, (jd + 1) / stacks))
                vertices.add(vertex(id / slices, (jd + 1) / stacks))
                add(Polygon(vertices))
            }
        }
    }
}

fun cylinder(
    start: Vector = -yUnit, end: Vector = yUnit, radius: Double = 1.0,
    slices: Int = 32, stacks: Int = 16, radiusFunc: ((Double, Double) -> Double)? = null
): Csg {
    val ray = end - start
    val axisZ = ray.unit()
    val isY = if (abs(axisZ.y) > 0.5) 1.0 else 0.0
    val axisX = (Vector(isY, 1.0 - isY, 0.0) x axisZ).unit()
    val axisY = (axisX x axisZ).unit()

    fun point(stack: Double, slice: Double): Vector {
        val angle = slice * PI * 2
        val out = axisX * cos(angle) + axisY * sin(angle)
        return start + ray * stack + out * (radiusFunc?.invoke(angle, stack) ?: radius)
    }

    return Csg.ofPolygons {
        for (i in 0 until slices) {
            val id = i.toDouble()
            val t0 = id / slices
            val t1 = (id + 1) / slices
            add(Polygon(true, start, point(0.0, t0), point(0.0, t1)))
            for (j in 0 until stacks) {
                val jd = j.toDouble()
                val j0 = jd / stacks
                val j1 = (jd + 1) / stacks
                add(Polygon(true, point(j0, t1), point(j0, t0), point(j1, t0), point(j1, t1)))
            }
            add(Polygon(true, end, point(1.0, t1), point(1.0, t0)))
        }
    }
}

fun ring(center: Vector = unit, radius: Double = 1.0, r: Double = 1.0, h: Double = 1.0, slices: Int = 32): Csg {
    fun vertex(r: Double, a: Double, b: Double, norm: Vector) =
        Vertex(center + Vector.ofSpherical(r, b, a), norm)

    fun vertex(r: Double, a: Double, b: Double, dir: Int): Vertex {
        val v = Vector.ofSpherical(r, b, a)
        return Vertex(v + center, v * dir.toDouble())
    }

    val da = 2 * PI / slices
    var a = 0.0
    return Csg.ofPolygons {
        while (a < 2 * PI + da / 2) {
            var b = (PI - h / radius) / 2
            add(
                Polygon(
                    vertex(radius, a, b, zUnit),
                    vertex(radius + r, a, b, zUnit),
                    vertex(radius + r, a + da, b, zUnit),
                    vertex(radius, a + da, b, zUnit)
                )
            )
            val db = h / radius
            while (b < (PI + h / radius) / 2) {
                add(
                    Polygon(
                        vertex(radius, a, b, -1),
                        vertex(radius, a + da, b, -1),
                        vertex(radius, a + da, b + db, -1),
                        vertex(radius, a, b + db, -1)
                    )
                )
                add(
                    Polygon(
                        vertex(radius + r, a, b, 1),
                        vertex(radius + r, a, b + db, 1),
                        vertex(radius + r, a + da, b + db, 1),
                        vertex(radius + r, a + da, b, 1)
                    )
                )
                b += db
            }
            add(
                Polygon(
                    vertex(radius, a, b, -zUnit),
                    vertex(radius, a + da, b, -zUnit),
                    vertex(radius + r, a + da, b, -zUnit),
                    vertex(radius + r, a, b, -zUnit)
                )
            )
            a += da
        }
    }
}