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

    fun base2(): Csg {
        return cube(center = origin).scale(v(7.5, 15, 7.5)).translate(v(0, 30, 7.5)) -
                cylinder().scale(v(2, 20, 2)).translate(v(0, 30, 14)) -
                cylinder().scale(v(2, 20, 2)).translate(v(0, 30, 1)) -
                cylinder().scale(v(2, 20, 2)).translate(v(6.5, 30, 7.5)) -
                cylinder().scale(v(2, 20, 2)).translate(v(-6.5, 30, 7.5))
    }

    fun stud(): Csg {
        return cube(center = origin).scale(v(1.5, 1.5, 1.5)).translate(v(0, 0, .5)) +
                (cube(center = origin).scale(v(2, 2, 1.25)).translate(v(0, 0, .75)) *
                        cylinder().scale(v(2, 10, 2)).translate(v(0, 0, 2)) *
                        cylinder().scale(v(2, 10, 2)).rotateZ(90.deg()).translate(v(0, 0, 2)))
    }

    model(File("target/m/base2.stl")) {
        add(base2())
    }

    model(File("target/m/stud.stl")) {
        add(cube(center = origin).scale(v(7.5, 7.5, 1)).translate(v(0, 0, 1)) + stud().translate(v(0, 0, 2)))
    }
}
