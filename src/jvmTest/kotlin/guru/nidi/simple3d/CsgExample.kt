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

import guru.nidi.simple3d.io.model
import guru.nidi.simple3d.model.*
import java.io.File

fun main() {
    model(File("target/csg.stl")) {
        val cy = cylinder()
//        val r = ring(center = v(2, 2, 0), radius = 2.0)
        val s = sphere(center = v(0, 8, 0), radius = 2.0)
        val c = cube(center = unit, radius = v(2, 3, 5))
        val c2 = cube(center = v(3, 4, 2), radius = v(3, 4, 2))
        add(cy.translate(0, 0, 10))
//        add(r)
        add(s)
        add(c)
        add(c2)

        add((c2 + c + s).translate(10, 0, 0))

        fun ops(a: Csg, b: Csg) {
            add((a * b).translate(20, 0, 0))
            add((a - b).translate(30, 0, 0))
            add((b - a).translate(40, 0, 0))
        }

        ops(c2, c)

        transformed(translate(0, 10, 0)) {
            ops(c2, s)
        }

        transformed(translate(0, 25, 0)) {
//            ops(c, r)
        }
    }
}
