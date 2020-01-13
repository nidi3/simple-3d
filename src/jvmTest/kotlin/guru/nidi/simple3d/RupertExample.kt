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
import kotlin.math.sqrt

fun main() {
    model(File("target/rupert.stl")) {
        val diff = .00
        transformed(scale(10, 10, 10)) {
            val s = 1.5 * sqrt(2.0) - 2 - diff
            val q = cube(2.0 * unit, 2.0 * unit)
            val a = v(1 + s, 4 - s, 4)
            val b = v(4 - s, 1 + s, 4)
            val c = v(3 - s, 0 + s, 0)
            val d = v(0 + s, 3 - s, 0)
            val n = -4.0 * Plane.fromPoints(a, b, c).normal
            add((q - prism(8, listOf(a + n, b + n, c + n, d + n))).rotateX(0.deg))
            add((q and prism(8, listOf(a + n, b + n, c + n, d + n))).translate(10, 0, 0))
            add(q.translate(5, 0, 0))
        }
    }
}
