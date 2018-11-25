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

import java.lang.Math.*

fun cube(center: Vector = unit, radius: Vector = unit) = Csg(
        listOf(
                listOf(listOf(0, 4, 6, 2), listOf(-1, 0, 0)),
                listOf(listOf(1, 3, 7, 5), listOf(+1, 0, 0)),
                listOf(listOf(0, 1, 5, 4), listOf(0, -1, 0)),
                listOf(listOf(2, 6, 7, 3), listOf(0, +1, 0)),
                listOf(listOf(0, 2, 3, 1), listOf(0, 0, -1)),
                listOf(listOf(4, 5, 7, 6), listOf(0, 0, +1)))
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

fun convexPrism(length: Double, vararg points: Vector) =
        convexPrism(length, points.toList())

fun convexPrism(length: Double, points: List<Vector>): Csg {
    val p = Plane.fromPoints(points[0], points[1], points[2])
    if (points.any { it !in p }) throw IllegalArgumentException("not all points in a plane")

    fun side(p1: Vector, p2: Vector) = Plane.fromPoints(p1, p2, p2 + p.normal * length).let { side ->
        Polygon(Vertex(p1, side.normal), Vertex(p2, side.normal),
                Vertex(p2 + p.normal * length, side.normal), Vertex(p1 + p.normal * length, side.normal))
    }

    return Csg.ofPolygons { sink ->
        sink.add(Polygon(points.reversed().map { Vertex(it, -p.normal) }))
        sink.add(Polygon(points.map { Vertex(it + p.normal * length, p.normal) }))
        sink.add(side(points[points.size - 1], points[0]))
        for (i in 0..points.size - 2) {
            sink.add(side(points[i], points[i + 1]))
        }
    }
}

fun sphere(center: Vector = unit, radius: Double = 1.0, slices: Int = 32, stacks: Int = 16,
           radiusFunc: ((Double, Double) -> Double)? = null): Csg {
    fun vertex(phi: Double, theta: Double): Vertex {
        val dir = Vector.ofSpherical(-1.0, theta * PI, phi * PI * 2)
        return Vertex(center + dir * (radiusFunc?.invoke(phi, theta) ?: radius), dir)
    }

    return Csg.ofPolygons { sink ->
        for (i in 0 until slices) {
            val id = i.toDouble()
            for (j in 0 until stacks) {
                val vertices = mutableListOf<Vertex>()
                val jd = j.toDouble()
                vertices.add(vertex(id / slices, jd / stacks))
                if (j > 0) vertices.add(vertex((id + 1) / slices, jd / stacks))
                if (j < stacks - 1) vertices.add(vertex((id + 1) / slices, (jd + 1) / stacks))
                vertices.add(vertex(id / slices, (jd + 1) / stacks))
                sink.add(Polygon(vertices))
            }
        }
    }
}

fun cylinder(start: Vector = Vector(0.0, -1.0, 0.0), end: Vector = Vector(0.0, 1.0, 0.0), radius: Double = 1.0,
             slices: Int = 32, stacks: Int = 16, radiusFunc: ((Double, Double) -> Double)? = null): Csg {
    val ray = end - start
    val axisZ = ray.unit()
    val isY = if (abs(axisZ.y) > 0.5) 1.0 else 0.0
    val axisX = (Vector(isY, 1.0 - isY, 0.0) x axisZ).unit()
    val axisY = (axisX x axisZ).unit()
    val s = Vertex(start, -axisZ)
    val e = Vertex(end, axisZ.unit())
    fun point(stack: Double, slice: Double, normalBlend: Double): Vertex {
        val angle = slice * PI * 2
        val out = axisX * cos(angle) + axisY * sin(angle)
        val pos = start + ray * stack + out * (radiusFunc?.invoke(angle, stack) ?: radius)
        val normal = out * (1 - abs(normalBlend)) + axisZ * normalBlend
        return Vertex(pos, normal)
    }

    return Csg.ofPolygons { sink ->
        for (i in 0 until slices) {
            val id = i.toDouble()
            val t0 = id / slices
            val t1 = (id + 1) / slices
            sink.add(Polygon(s, point(0.0, t0, -1.0), point(0.0, t1, -1.0)))
            for (j in 0 until stacks) {
                val jd = j.toDouble()
                val j0 = jd / stacks
                val j1 = (jd + 1) / stacks
                sink.add(Polygon(point(j0, t1, 0.0), point(j0, t0, 0.0), point(j1, t0, 0.0), point(j1, t1, 0.0)))
            }
            sink.add(Polygon(e, point(1.0, t1, 1.0), point(1.0, t0, 1.0)))
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
    val down = Vector(0.0, 0.0, 1.0)
    return Csg.ofPolygons { sink ->
        while (a < 2 * PI + da / 2) {
            var b = (PI - h / radius) / 2
            sink.add(Polygon(vertex(radius, a, b, down), vertex(radius + r, a, b, down), vertex(radius + r, a + da, b, down), vertex(radius, a + da, b, down)))
            val db = h / radius
            while (b < (PI + h / radius) / 2) {
                sink.add(Polygon(vertex(radius, a, b, -1), vertex(radius, a + da, b, -1), vertex(radius, a + da, b + db, -1), vertex(radius, a, b + db, -1)))
                sink.add(Polygon(vertex(radius + r, a, b, 1), vertex(radius + r, a, b + db, 1), vertex(radius + r, a + da, b + db, 1), vertex(radius + r, a + da, b, 1)))
                b += db
            }
            sink.add(Polygon(vertex(radius, a, b, -down), vertex(radius, a + da, b, -down), vertex(radius + r, a + da, b, -down), vertex(radius + r, a, b, -down)))
            a += da
        }
    }
}