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
    val hole = cylinder().scale(v(2, 20, 2))
    val stud = cube(center = origin).scale(v(1.5, 1.5, 1.5)).translate(v(0, 0, .5)) +
            (cube(center = origin).scale(v(2, 2, 1.25)).translate(v(0, 0, .75)) *
                    hole.translate(v(0, 0, 2)) *
                    hole.rotateZ(90.deg).translate(v(0, 0, 2)))

    fun base(len: Int): Csg {
        return cube(center = origin).scale(v(7.5, 7.5 * len, 7.5)).translate(v(0, 0, 7.5)) -
                hole.translate(v(0, 0, 14)) -
                hole.translate(v(0, 0, 1)) -
                hole.translate(v(6.5, 0, 7.5)) -
                hole.translate(v(-6.5, 0, 7.5)) -
                hole.rotateZ(90.deg).translate(v(0, 7.5 * len - 1, 7.5)) +
                stud.rotateX(90.deg).translate(v(0, -7.5 * len, 7.5))
    }

    model(File("target/m/base.stl")) {
        add(base(1).rotateX(90.deg))
//        add(base(2).translate(v(20, 0, 0)))
    }

    model(File("target/m/stud.stl")) {
        add(cube(center = origin).scale(v(7.5, 7.5, 1)).translate(v(0, 0, 1)) + stud.translate(v(0, 0, 2)))
    }
}