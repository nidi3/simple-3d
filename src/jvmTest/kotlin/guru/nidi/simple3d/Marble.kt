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
import java.lang.Math.PI

fun main() {
    val smallR = 1.95
    val bigR = 2.1
    val smallHole = cylinder().scale(smallR, 40, smallR)
    val bigHole = cylinder().scale(bigR, 40, bigR)
    val bigAngluarHole = bigHole - cube().scale(bigR, 20, bigR).translate(0, 0, bigR + .5)

    val stud = cube().scale(1.4, 1.4, 1).translate(0, 0, 1) +
            (cube().scale(2, 2, 1.25).translate(0, 0, 1.3) *
                    smallHole.translate(0, 0, 2.1) *
                    smallHole.rotateZ(90.deg).translate(0, 0, 2.1))

    fun base(len: Double) = cube().scale(7.5, 7.5 * len, 7.5).translate(0, 0, 0) -
            bigHole.translate(0, 0, 5.5) -
            bigHole.translate(0, 0, -5.5) -
            bigHole.translate(5.5, 0, 0) -
            bigHole.translate(-5.5, 0, 0) -
            bigHole.rotateZ(90.deg).translate(0, 7.5 * len - 2, 0) +
            stud.rotateX(90.deg).translate(0, -7.5 * len, 0)

    fun axle(len: Double) = cylinder(slices = 128) { angle, _ -> if ((angle) % (PI / 2) < 3 * PI / 8) 1.0 else .5 }
        .scale(smallR * 1.05, len, smallR * 1.05)

    fun profile(len: Int) = csg {
        val hole = bigHole + cube().scale(1.6, 10, 3.4)
        var base = cube().scale(7.5, 7.5 * len, 1).translate(0, 0, 1) +
                cube().scale(7.5, 7.5 * len, 1).rotateY(90.deg).translate(4.5, 0, 7.5) +
                cube().scale(1, 7.5 * len, 1).rotateY(90.deg).translate(6.5, 0, 14) +
                cube().scale(5.5, 7.5, 1).rotateX(90.deg).translate(-2, 7.5 * len - 1, 7.5) +
                cube().scale(5.5, 7.5, 1.6).rotateX(90.deg).translate(-2, -7.5 * len + 1.6, 7.5) +
                stud.rotateX(270.deg).translate(0, 7.5 * len, 7.5) -
                bigAngluarHole.rotateX(90.deg).rotateZ(180.deg).translate(0, -7.5 * len + 2, 25)
        for (i in 0 until len) {
            val y = i * 15 - 7.5 * (len - 1)
            base -= hole.rotateX(90.deg).translate(0, y, 0)
            base -= hole.rotateZ(90.deg).rotateX(90.deg).translate(0, y, 7.5)
        }
        base
    }

    fun bolt(len: Int) = cube().scale(4, 3, 1) +
            cylinder().scale(3.3, 1, 3.3).translate(0, 3.5, 0) +
            cylinder().scale(2.1, 5, 2.1).translate(0, 6.5, 0) +
            cube().scale(3.2, .8, 1.3).translate(0, 8.8, 0)

    model(
        File("target/m/base.stl"),
//        base(.5),
        base(.5).rotateX(270.deg).translate(20, 0, 0)
//        base(2.0).translate(20, 0, 0)
    )

    model(
        File("target/m/stud.stl"),
        cube().scale(7.5, 7.5, 1).translate(0, 0, 1) + stud.translate(0, 0, 2)
    )

    model(
        File("target/m/axle.stl"),
        cube().scale(5, 5, .5) + axle(15.0).rotateX(90.deg).translate(0, 0, 7.5)
    )

    model(File("target/m/profile1.stl"), profile(1))

    model(File("target/m/profile2.stl"), profile(2))

    model(File("target/m/profile4.stl"), profile(4))

    model(File("target/m/ball.stl"), sphere(radius = 5.5))

    model(File("target/m/bolt.stl")) {
        add(bolt(1).rotateX(45.deg))
//        add(bolt(1).rotateX(90.deg).translate(15,0,0))
        splitPolygons()
    }
}
