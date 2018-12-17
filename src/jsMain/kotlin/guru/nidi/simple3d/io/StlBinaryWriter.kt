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
package guru.nidi.simple3d.io

import guru.nidi.simple3d.model.*
import org.khronos.webgl.ArrayBuffer
import org.khronos.webgl.DataView

class StlBinaryWriter(val buf: ArrayBuffer) {
    constructor(m: Model) : this(ArrayBuffer(84 + 50 * triangles(m)))

    private var bytes = DataView(buf)
    private var pos = 84

    companion object {
        @JsName("write")
        fun write(m: Model) = StlBinaryWriter(m).apply { writeModel(m) }.buf

        private fun triangles(m: Model) = m.csgs.sumBy { triangles(it) }
        private fun triangles(csg: Csg) = csg.polygons.sumBy { triangles(it) }
        private fun triangles(p: Polygon) = p.vertices.size - 2
    }

    init {
        bytes.setInt32(80, (buf.byteLength - 84) / 50, true)
    }

    fun writeModel(m: Model) = m.csgs.forEach { writeCsg(it) }
    fun writeCsg(csg: Csg) = writePolygons(csg.polygons)
    fun writePolygons(ps: List<Polygon>) = ps.forEach { writePolygon(it) }

    fun writePolygon(p: Polygon) {
        if (p.vertices.size > 3) writePolygons(p.toTriangles()) else {
            writeTriangle(p.vertices[0].pos, p.vertices[1].pos, p.vertices[2].pos)
        }
    }

    fun writeTriangle(a: Vector, b: Vector, c: Vector) {
        writeVector(origin)
        writeVector(a)
        writeVector(b)
        writeVector(c)
        pos += 2
    }

    private fun writeVector(a: Vector) {
        writeFloat(a.x)
        writeFloat(a.y)
        writeFloat(a.z)
    }

    private fun writeFloat(v: Double) {
        bytes.setFloat32(pos, v.toFloat(), true)
        pos += 4
    }
}

fun Model.writeBinaryStl() = StlBinaryWriter.write(this)
