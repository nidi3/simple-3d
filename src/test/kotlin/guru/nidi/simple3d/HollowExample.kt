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
package guru.nidi.simple3d

import guru.nidi.simple3d.model.*
import java.io.File

fun main() {
    model {
        val r = 15
        val b = 1.0
        val base = cube(center = v(r, r, b / 2), radius = v(r, r, b / 2))
        add(base)
        add(base.rotateX(90.deg()).translate(v(0, b, 0)))
        add(base.rotateX(90.deg()).translate(v(0, 2 * r, 0)))
        add(base.rotateY(90.deg()).translate(v(b, 0, 0)))
        add(base.rotateY(90.deg()).translate(v(2 * r, 0, 0)))

        val b2 = 1.0
        transform(translate(v(2.5 * r, 0, 0))) {
            val inner = cube(center = v(r, b2 / 2, b2 / 2), radius = v(r - b2, b2 / 2, b2))
            val innerC = cylinder(slices = 24, start = v(r, r, 0), end = v(r, r, 10)) { _, z -> 4.75 - .5 * z }
            val outerC = cylinder(start = v(r, r, 0), end = v(r, r, 10)) { _, z -> 5.75 - .5 * z }
            add(base + outerC - innerC)
            add(inner.translate(v(0, b2, b2)))
            add(inner.translate(v(0, 2 * r - 2 * b2, b2)))
            add(inner.rotateZ(90.deg()).translate(v(2 * r - 2 * b2, 2 * r, b2)))
            add(inner.rotateZ(90.deg()).translate(v(b2, 2 * r, b2)))
        }
        write(File("target/cube.stl"), "csg")
    }
}