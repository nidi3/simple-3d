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

    fun write(csg: Csg) = csg.polygons.forEach { p ->
        p.toTriangles().forEach { t ->
            write(t)
        }
    }

    private fun write(p: Polygon) {
        write(0.0)
        write(0.0)
        write(0.0)
        for (v in p.vertices) {
            write(v.pos)
        }
        out.writeShort(0)
        count++
    }

    private fun write(a: Vector) {
        write(a.x)
        write(a.y)
        write(a.z)
    }

    private fun write(v: Double) {
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
