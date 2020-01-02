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

import guru.nidi.simple3d.model.Csg
import guru.nidi.simple3d.model.Model
import guru.nidi.simple3d.model.Polygon
import guru.nidi.simple3d.model.Vector
import java.io.*

fun model(file: File, actions: Model.() -> Unit) =
    Model().also(actions).writeBinaryStl(file)

fun Model.writeBinaryStl(f: File) {
    StlBinaryWriter(f).use { out ->
        csgs.forEach { out.write(it) }
    }
}

class StlBinaryWriter(val file: File) : AutoCloseable {
    private val out = file.let {
        it.parentFile.mkdirs()
        DataOutputStream(BufferedOutputStream(FileOutputStream(it)))
    }
    private var count = 0

    init {
        out.write(ByteArray(80))
        out.writeInt(0)
    }

    fun write(csg: Csg) = write(csg.polygons)

    fun write(ps: List<Polygon>) = ps.forEach { write(it) }

    fun write(p: Polygon) {
        if (p.vertices.size > 3) write(p.toTriangles()) else {
            write(p.vertices[0].pos, p.vertices[1].pos, p.vertices[2].pos)
        }
    }

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
        out.writeInt(Integer.reverseBytes(i))
    }

    override fun close() {
        out.close()
        RandomAccessFile(file, "rw").use {
            it.seek(80)
            it.writeInt(Integer.reverseBytes(count))
        }
    }
}
