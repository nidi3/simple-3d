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

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PlaneTest {
    @Test
    fun splitPolygonSimple() {
        val xyPlane = Plane.fromPoints(Vector(0.0, 0.0, 0.0), Vector(1.0, 0.0, 0.0), Vector(0.0, 1.0, 0.0))
        val n = Vector(0.0, 0.0, 1.0)
        val high = Polygon(Vertex(Vector(0.0, 0.0, 1.0), n), Vertex(Vector(1.0, 0.0, 1.0), n), Vertex(Vector(0.0, 1.0, 1.0), n))
        val low = Polygon(Vertex(Vector(0.0, 0.0, -1.0), n), Vertex(Vector(1.0, 0.0, -1.0), n), Vertex(Vector(0.0, 1.0, -1.0), n))
        val zero = Polygon(Vertex(Vector(0.0, 0.0, 0.0), n), Vertex(Vector(1.0, 0.0, 0.0), n), Vertex(Vector(0.0, 1.0, 0.0), n))
        val mzero = Polygon(Vertex(Vector(0.0, 0.0, 0.0), n), Vertex(Vector(0.0, 1.0, 0.0), n), Vertex(Vector(1.0, 0.0, 0.0), n))
        test(xyPlane, high, listOf(listOf(), listOf(), listOf(high), listOf()))
        test(xyPlane, low, listOf(listOf(), listOf(), listOf(), listOf(low)))
        test(xyPlane, zero, listOf(listOf(zero), listOf(), listOf(), listOf()))
        test(xyPlane, mzero, listOf(listOf(), listOf(mzero), listOf(), listOf()))
    }

    @Test
    fun splitPolygon() {
        val xyPlane = Plane.fromPoints(Vector(0.0, 0.0, 0.0), Vector(1.0, 0.0, 0.0), Vector(0.0, 1.0, 0.0))
        val n = Vector(0.0, 0.0, 1.0)
        val p = Polygon(Vertex(Vector(0.0, 0.0, 1.0), n), Vertex(Vector(1.0, 0.0, 1.0), n), Vertex(Vector(0.0, 1.0, -1.0), n))
        val up = Polygon(Vertex(Vector(0.0, 0.0, 1.0), n), Vertex(Vector(1.0, 0.0, 1.0), n), Vertex(Vector(0.5, 0.5, 0.0), n), Vertex(Vector(0.0, 0.5, 0.0), n))
        val down = Polygon(Vertex(Vector(0.5, 0.5, 0.0), n), Vertex(Vector(0.0, 1.0, -1.0), n), Vertex(Vector(0.0, 0.5, 0.0), n))
        test(xyPlane, p, listOf(listOf(), listOf(), listOf(up), listOf(down)))
    }
}

fun test(p: Plane, po: Polygon, expected: List<List<Polygon>>) {
    val res = listOf<MutableList<Polygon>>(mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf())
    p.splitPolygon(po, res[0], res[1], res[2], res[3])
    res.forEachIndexed { i, _ ->
        assertEquals(expected[i], res[i])
    }
}