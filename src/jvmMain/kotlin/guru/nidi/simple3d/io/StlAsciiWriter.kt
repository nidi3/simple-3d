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
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.io.PrintWriter

fun Model.writeAsciiStl(f: File, name: String) {
    StlAsciiWriter(f, name).use { out ->
        csgs.forEach { out.write(it) }
    }
}

class StlAsciiWriter(file: File, private val name: String) : AutoCloseable {
    private val out = file.let {
        it.parentFile.mkdirs()
        PrintWriter(OutputStreamWriter(FileOutputStream(file)))
    }

    init {
        out.println("solid $name")
    }

    fun write(csg: Csg) = csg.polygons.forEach { p ->
        p.toTriangles().forEach { t ->
            write(t)
        }
    }

    fun write(p: Polygon) {
        out.println("facet normal 0 0 0")
        out.println("outer loop")
        for (v in p.vertices) {
            write(v.pos)
        }
        out.println("endloop")
        out.println("endfacet")
    }

    fun write(v: Vector) {
        out.println("vertex %.8f %.8f %.8f".format(v.x, v.y, v.z))
    }

    override fun close() {
        out.println("endsolid $name")
        out.close()
    }
}
