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

internal const val EPSILON = 1e-5

class Model {
    internal val csgs = mutableListOf<Csg>()
    private var transform = AffineTransform()

    fun <T> transform(a: AffineTransform, block: Model.() -> T): T {
        val orig = transform
        try {
            transform = transform.applyTo(a)
            return block()
        } finally {
            transform = orig
        }
    }

    @JsName("add")
    fun add(csg: Csg) = csgs.add(transform.applyTo(csg))

    fun mergeVertices() {
        for (csg1 in csgs) {
            for (csg2 in csgs) {
                for (p1 in csg1.polygons) {
                    for (p2 in csg2.polygons) {
                        for (v1 in p1.vertices.indices) {
                            for (v2 in p2.vertices.indices) {
                                val d = (p1.vertices[v1].pos - p2.vertices[v2].pos).length
                                if (d > 0 && d < EPSILON) {
                                    (p2.vertices as MutableList)[v2] = p1.vertices[v1]
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun splitPolygons() {
        for (csg1 in csgs) {
            for (p1 in csg1.polygons.indices) {
                val b1 = csg1.polygons[p1].boundingBox
                for (v1 in csg1.polygons[p1].vertices) {
                    for (csg2 in csgs) {
                        var p2 = 0
                        while (p2 < csg2.polygons.size) {
                            var poly = csg2.polygons[p2]
                            if (b1.intersect(poly.boundingBox)) {
                                var v2 = 0
                                while (v2 < poly.vertices.size) {
                                    val a = poly.vertices(v2).pos
                                    val b = poly.vertices(v2 + 1).pos
                                    if (v1.pos.inSegment(a, b)) {
                                        val parts = poly.split(v1.pos)
                                        poly = parts.first
                                        (csg2.polygons as MutableList)[p2] = parts.first
                                        csg2.polygons.add(parts.second)
                                    }
                                    v2++
                                }
                            }
                            p2++
                        }
                    }
                }
            }
        }
    }
}

@JsName("model")
fun model(actions: Model.() -> Unit) = Model().apply(actions)

val Int.deg get() = this * PI / 180
val Double.deg get() = this * PI / 180
val Double.rad get() = this

operator fun <T> List<T>.invoke(i: Int) = get(((i % size) + size) % size)
fun <T> List<T>.pred(i: Int) = if (i == 0) lastIndex else i - 1
fun <T> List<T>.succ(i: Int) = if (i == lastIndex) 0 else i + 1
