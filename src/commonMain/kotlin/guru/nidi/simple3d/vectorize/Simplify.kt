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
package guru.nidi.simple3d.vectorize

import kotlin.js.JsName
import kotlin.math.abs
import kotlin.math.sqrt

fun List<Point>.simplify(epsilon: Double) = simplifyPolygon(this, epsilon)

@JsName("simplifyPolygon")
fun simplifyPolygon(points: List<Point>, epsilon: Double): List<Point> {
    fun dist(a: Point, b: Point, c: Point) =
        abs((c.y - a.y) * b.x - (c.x - a.x) * b.y + c.x * a.y - c.y * a.x) / sqrt((c.x - a.x).toDouble() * (c.x - a.x) + (c.y - a.y) * (c.y - a.y))

    var maxI = 0
    var maxD = 0.0
    for (i in 1..points.size - 2) {
        val d = dist(points.first(), points[i], points.last())
        if (d > maxD) {
            maxD = d
            maxI = i
        }
    }
    val res = if (maxD < epsilon) listOf(points.first(), points.last())
    else {
        val s1 = simplifyPolygon(points.subList(0, maxI + 1), epsilon)
        val s2 = simplifyPolygon(points.subList(maxI, points.size), epsilon)
        s1.subList(0, s1.size - 1) + s2
    }
    return res
}