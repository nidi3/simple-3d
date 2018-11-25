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
import java.io.*
import java.lang.Math.abs

class StlBinaryWriter(val file: File, val name: String) : AutoCloseable {
    private val out = DataOutputStream(BufferedOutputStream(FileOutputStream(file)))
    private var count = 0

    init {
        out.write(ByteArray(80))
        out.writeInt(0)
    }

    fun write(t: Csg, normals: Boolean = false) = write(t.polygons, normals)

    fun write(t: Polygon, normals: Boolean = false) {
        write(t.vertices[0].pos, t.vertices[1].pos, t.vertices[2].pos)
        if (normals) {
            t.vertices.forEach { v ->
                val n = v.normal.unit()
                val p = when {
                    abs(n.y) > .2 -> Vector(0.0, -n.z, -n.y)
                    abs(n.z) > .2 -> Vector(-n.z, 0.0, -n.x)
                    abs(n.x) > .2 -> Vector(-n.y, -n.x, 0.0)
                    else -> Vector(0.0, 0.0, 0.0)
                }
                write(v.pos, v.pos + n, v.pos + p)
            }
        }
    }

    fun write(t: List<Polygon>, normals: Boolean = false) = t.map { write(it, normals) }

    fun write(a: Vector, b: Vector, c: Vector) {
        wr(0.0)
        wr(0.0)
        wr(0.0)
        wr(a)
        wr(b)
        wr(c)
        out.writeShort(0)
        count++
    }

    private fun wr(a: Vector) {
        wr(a.x)
        wr(a.y)
        wr(a.z)
    }

    private fun wr(v: Double) {
        val i = java.lang.Float.floatToIntBits(v.toFloat())
        out.writeInt(java.lang.Integer.reverseBytes(i))
    }

    override fun close() {
        out.close()
        RandomAccessFile(file, "rw").use {
            it.seek(80)
            it.writeInt(java.lang.Integer.reverseBytes(count))
        }
    }
}