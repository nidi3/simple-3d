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

/**
 * Show the different CSG operators
 */
fun main() {
    model(File("target/csg.obj")) {
        val red = material("red", Color(1.0, 0.0, 0.0))
        val green = material("green", Color(0.0, 1.0, 0.0))
        val blue = material("blue", Color(0.0, 0.0, 1.0))

        val cy = cylinder(height = 2)
        val ring = (cylinder().scale(3, 2, 3) - cylinder().scale(2, 2, 2))
            .material(blue).rotateX(90.deg).translate(2, 2, 0)
        val s = sphere(center = v(0, 8, 0), radius = 2, material = red)
        val c = cube(center = unit, length = v(4, 6, 10), material = green)
        val c2 = cube(center = v(3, 4, 2), length = v(6, 8, 4))

        add(cy.translate(0, 0, 10), ring, s, c, c2, (c2 + c + s).translate(10, 0, 0))

        fun ops(a: Csg, b: Csg) = add(
            (a * b).translate(20, 0, 0),
            (a - b).translate(30, 0, 0),
            (b - a).translate(40, 0, 0)
        )

        ops(c2, c)

        transformed(translate(0, 10, 0)) {
            ops(c2, s)
        }

        transformed(translate(0, 25, 0)) {
            ops(c, ring)
        }
    }
}
