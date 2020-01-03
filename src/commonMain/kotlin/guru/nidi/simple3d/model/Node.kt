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

class Node private constructor(
    private val polygons: MutableList<Polygon>, private var plane: Plane?,
    private var front: Node?, private var back: Node?
) {
    constructor() : this(mutableListOf<Polygon>(), null, null, null)
    constructor(polygons: List<Polygon>) : this() {
        build(polygons.toMutableList())
    }

    fun copy(): Node = Node(polygons.toMutableList(), plane?.copy(), front?.copy(), back?.copy())

    operator fun unaryMinus(): Node =
        Node(polygons.mapTo(mutableListOf()) { -it }, plane?.let { -it }, back?.let { -it }, front?.let { -it })

    private fun clipPolygons(polygons: List<Polygon>): MutableList<Polygon> {
        if (plane == null) return polygons.toMutableList()
        var f = mutableListOf<Polygon>()
        var b = mutableListOf<Polygon>()
        polygons.forEach {
            plane!!.splitPolygon(it, f, b, f, b)
        }
        if (front != null) f = front!!.clipPolygons(f)
        b = back?.clipPolygons(b) ?: mutableListOf()
        f.addAll(b)
        return f
    }

    fun clipTo(bsp: Node): Node = Node(bsp.clipPolygons(polygons), plane, front?.clipTo(bsp), back?.clipTo(bsp))

    fun allPolygons(): List<Polygon> =
        polygons + (front?.allPolygons() ?: listOf()) + (back?.allPolygons() ?: listOf())

    fun combine(node: Node) = combine(node.allPolygons())

    fun combine(polygons: List<Polygon>) = copy().build(polygons)

    private fun build(polygons: List<Polygon>): Node {
        plane = plane ?: polygons[0].plane
        val f = mutableListOf<Polygon>()
        val b = mutableListOf<Polygon>()
        polygons.forEach {
            plane!!.splitPolygon(it, this.polygons, this.polygons, f, b)
        }
        if (f.isNotEmpty()) {
            if (front == null) front = Node()
            front!!.build(f)
        }
        if (b.isNotEmpty()) {
            if (back == null) back = Node()
            back!!.build(b)
        }
        return this
    }
}
