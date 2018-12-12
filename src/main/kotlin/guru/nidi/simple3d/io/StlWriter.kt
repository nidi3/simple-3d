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

class StlWriter(file: File, private val name: String) : AutoCloseable {
    private val out = PrintWriter(OutputStreamWriter(FileOutputStream(file)))

    init {
        out.println("solid $name")
    }

    fun write(csg: Csg) = write(csg.polygons)

    fun write(ps: List<Polygon>) = ps.forEach { write(it) }

    fun write(p: Polygon) {
        if (p.vertices.size > 3) write(p.toTriangles()) else {
            write(p.vertices[0].pos, p.vertices[1].pos, p.vertices[2].pos)
        }
    }

    fun write(a: Vector, b: Vector, c: Vector) {
        out.println("facet normal 0 0 0")
        out.println("outer loop")
        out.println("vertex ${a.x} ${a.y} ${a.z}")
        out.println("vertex ${b.x} ${b.y} ${b.z}")
        out.println("vertex ${c.x} ${c.y} ${c.z}")
        out.println("endloop")
        out.println("endfacet")
    }

    override fun close() {
        out.println("endsolid $name")
        out.close()
    }
}

fun Model.writeStl(f: File, name: String) {
    StlWriter(f, name).use { out ->
        csgs.forEach { out.write(it) }
    }
}
