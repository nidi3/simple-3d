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
        val mmm = cube(center = v(-2, -2, -2))
        val mmo = cube(center = v(-2, -2, 0))
        val mmp = cube(center = v(-2, -2, 2))
        val mom = cube(center = v(-2, 0, -2))
        val moo = cube(center = v(-2, 0, 0))
        val mop = cube(center = v(-2, 0, 2))
        val mpm = cube(center = v(-2, 2, -2))
        val mpo = cube(center = v(-2, 2, 0))
        val mpp = cube(center = v(-2, 2, 2))
        val omm = cube(center = v(0, -2, -2))
        val omo = cube(center = v(0, -2, 0))
        val omp = cube(center = v(0, -2, 2))
        val oom = cube(center = v(0, 0, -2))
        val ooo = cube(center = v(0, 0, 0))
        val oop = cube(center = v(0, 0, 2))
        val opm = cube(center = v(0, 2, -2))
        val opo = cube(center = v(0, 2, 0))
        val opp = cube(center = v(0, 2, 2))
        val pmm = cube(center = v(2, -2, -2))
        val pmo = cube(center = v(2, -2, 0))
        val pmp = cube(center = v(2, -2, 2))
        val pom = cube(center = v(2, 0, -2))
        val poo = cube(center = v(2, 0, 0))
        val pop = cube(center = v(2, 0, 2))
        val ppm = cube(center = v(2, 2, -2))
        val ppo = cube(center = v(2, 2, 0))
        val ppp = cube(center = v(2, 2, 2))

        fun mosely(level: Int) {
            if (level == 0) {
                add(ppo)
                add(mmo)
                add(pmo)
                add(mpo)

                add(pop)
                add(mom)
                add(mop)
                add(pom)

                add(opp)
                add(omm)
                add(omp)
                add(opm)

                add(poo)
                add(opo)
                add(oop)
                add(moo)
                add(omo)
                add(oom)
            } else {
                transform(scale(unit / 3.0)) {
                    transform(translate(6, 6, 0)) { mosely(level - 1) }
                    transform(translate(-6, -6, 0)) { mosely(level - 1) }
                    transform(translate(6, -6, 0)) { mosely(level - 1) }
                    transform(translate(-6, 6, 0)) { mosely(level - 1) }

                    transform(translate(6, 0, 6)) { mosely(level - 1) }
                    transform(translate(-6, 0, -6)) { mosely(level - 1) }
                    transform(translate(-6, 0, 6)) { mosely(level - 1) }
                    transform(translate(6, 0, -6)) { mosely(level - 1) }

                    transform(translate(0, 6, 6)) { mosely(level - 1) }
                    transform(translate(0, -6, -6)) { mosely(level - 1) }
                    transform(translate(0, -6, 6)) { mosely(level - 1) }
                    transform(translate(0, 6, -6)) { mosely(level - 1) }

                    transform(translate(6, 0, 0)) { mosely(level - 1) }
                    transform(translate(0, 6, 0)) { mosely(level - 1) }
                    transform(translate(0, 0, 6)) { mosely(level - 1) }

                    transform(translate(-6, 0, 0)) { mosely(level - 1) }
                    transform(translate(0, -6, 0)) { mosely(level - 1) }
                    transform(translate(0, 0, -6)) { mosely(level - 1) }
                }
            }
        }

        fun menger(level: Int) {
            if (level == 0) {
                add(ppo)
                add(mmo)
                add(pmo)
                add(mpo)

                add(pop)
                add(mom)
                add(mop)
                add(pom)

                add(opp)
                add(omm)
                add(omp)
                add(opm)

                add(ppp)
                add(mmm)

                add(mpp)
                add(pmp)
                add(ppm)
                add(pmm)
                add(mpm)
                add(mmp)
            } else {
                transform(scale(unit / 3.0)) {
                    transform(translate(6, 6, 0)) { menger(level - 1) }
                    transform(translate(6, -6, 0)) { menger(level - 1) }
                    transform(translate(-6, 6, 0)) { menger(level - 1) }
                    transform(translate(-6, -6, 0)) { menger(level - 1) }

                    transform(translate(6, 0, 6)) { menger(level - 1) }
                    transform(translate(6, 0, -6)) { menger(level - 1) }
                    transform(translate(-6, 0, 6)) { menger(level - 1) }
                    transform(translate(-6, 0, -6)) { menger(level - 1) }

                    transform(translate(0, 6, 6)) { menger(level - 1) }
                    transform(translate(0, 6, -6)) { menger(level - 1) }
                    transform(translate(0, -6, 6)) { menger(level - 1) }
                    transform(translate(0, -6, -6)) { menger(level - 1) }

                    transform(translate(6, 6, 6)) { menger(level - 1) }
                    transform(translate(-6, -6, -6)) { menger(level - 1) }

                    transform(translate(-6, 6, 6)) { menger(level - 1) }
                    transform(translate(6, -6, 6)) { menger(level - 1) }
                    transform(translate(6, 6, -6)) { menger(level - 1) }

                    transform(translate(6, -6, -6)) { menger(level - 1) }
                    transform(translate(-6, 6, -6)) { menger(level - 1) }
                    transform(translate(-6, -6, 6)) { menger(level - 1) }
                }
            }
        }

        val size = 10
        val level = 1
        transform(scale(unit * size.toDouble())) {
            menger(level)
            writeBinaryStl(File("target/menger-$level-$size.stl"))

//                    mosely(level)
//                    write(File("target/mosely-$level-$size.stl"), "mosely")
        }

    }
}
