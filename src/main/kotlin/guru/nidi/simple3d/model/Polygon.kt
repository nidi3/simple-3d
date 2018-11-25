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

data class Polygon(val vertices: List<Vertex>) {
    constructor(vararg vs: Vertex) : this(vs.asList())

    val plane = Plane.fromPoints(vertices[0].pos, vertices[1].pos, vertices[2].pos)

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

    //TODO support concave
    fun toTriangles(): List<Polygon> {
        val res = mutableListOf<Polygon>()
        for (i in 2 until vertices.size) {
            res.add(Polygon(vertices[0], vertices[i - 1], vertices[i]))
        }
        return res
    }
}