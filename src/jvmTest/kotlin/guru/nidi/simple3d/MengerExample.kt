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

import guru.nidi.simple3d.io.writeBinaryStl
import guru.nidi.simple3d.model.*
import java.io.File

fun main() {
    model {

        fun menger(r: Double, level: Int): Csg {
            var c = cube(center = unit * 30.0)
//                    var c = cube(center = origin, radius = unit * r)

            fun step(r: Double, level: Int) {
                c += cube(center = origin, radius = v(r / 3, r / 3, 1.1 * r))
                c += cube(center = origin, radius = v(r / 3, 1.1 * r, r / 3))
                c += cube(center = origin, radius = v(1.1 * r, r / 3, r / 3))
//                        addToModel(cube(center = origin, radius = v(r / 3, r / 3, 1.1 * r)))
//                        addToModel(cube(center = origin, radius = v(r / 3, 1.1 * r, r / 3)))
//                        addToModel(cube(center = origin, radius = v(1.1 * r, r / 3, r / 3)))
                if (level > 0) {
                    transform(scale(unit / 3.0)) {
                        val d = 2 * r / 3
                        transform(translate(v(d, d, 0))) { step(r, level - 1) }
                        transform(translate(v(-d, -d, 0))) { step(r, level - 1) }
                        transform(translate(v(d, -d, 0))) { step(r, level - 1) }
                        transform(translate(v(-d, d, 0))) { step(r, level - 1) }

                        transform(translate(v(d, 0, d))) { step(r, level - 1) }
                        transform(translate(v(-d, 0, -d))) { step(r, level - 1) }
                        transform(translate(v(-d, 0, d))) { step(r, level - 1) }
                        transform(translate(v(d, 0, -d))) { step(r, level - 1) }

                        transform(translate(v(0, d, d))) { step(r, level - 1) }
                        transform(translate(v(0, -d, -d))) { step(r, level - 1) }
                        transform(translate(v(0, -d, d))) { step(r, level - 1) }
                        transform(translate(v(0, d, -d))) { step(r, level - 1) }

                        transform(translate(v(d, d, d))) { step(r, level - 1) }
                        transform(translate(v(-d, -d, -d))) { step(r, level - 1) }

                        transform(translate(v(-d, d, d))) { step(r, level - 1) }
                        transform(translate(v(d, -d, d))) { step(r, level - 1) }
                        transform(translate(v(d, d, -d))) { step(r, level - 1) }

                        transform(translate(v(-d, -d, d))) { step(r, level - 1) }
                        transform(translate(v(-d, d, -d))) { step(r, level - 1) }
                        transform(translate(v(d, -d, -d))) { step(r, level - 1) }
                    }
                }
            }

            step(r, level)
            add(c)
            return c
        }

        menger(15.0, 3)

        writeBinaryStl(File("target/menger.stl"))
    }
}