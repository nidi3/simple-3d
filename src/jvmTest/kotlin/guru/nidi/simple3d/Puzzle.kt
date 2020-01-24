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
 * A little puzzle seen here: https://youtu.be/s4EcAQGNn6M
 */
fun main() {
    val h = 3.0
    val len = 30.0
    val r1 = .1 * len
    val rs1 = .2 * len
    val rs2 = .12 * len
    val rt1 = .27 * len
    val rt2 = .1 * len
    val holeScale = v(1.03, 1.03, 1.03)

    val cs = cylinder(height = 2, slices = 64) { angle, _ -> ellipseFunc(rs1, rs2)(angle) }
        .rotateX(90.deg).scale(1, 1, h / 2)
    val ct = cylinder(height = 2, slices = 64) { angle, _ -> ellipseFunc(rt1, rt2)(angle) }
        .rotateX(90.deg).scale(1, 1, h / 2)

    fun round(len: Double, r: Double) = csg {
        val corner = (cube().scale(2, 2, 2) -
                cylinder(height = 4, radius = 2, slices = 64).rotateX(90.deg).translate(-1, -1, 0))
            .scale(r, r, h * 2)
        val d = len / 2 - r
        cube().scale(len, len, h) -
                corner.rotateZ(0.deg).translate(d, d, 0) -
                corner.rotateZ(180.deg).translate(-d, -d, 0) -
                corner.rotateZ(90.deg).translate(d, -d, 0) -
                corner.rotateZ(270.deg).translate(-d, d, 0)
    }

    fun round() = round(len, r1)

    fun piece1() = round() +
            cs.translate(-len / 2, 0, 0) -
            cs.scale(holeScale).rotateZ(90.deg).translate(0, len / 2, 0) -
            cs.scale(holeScale).translate(len / 2, 0, 0)

    fun piece2() = round() +
            cs.translate(-len / 2, 0, 0) -
            cs.scale(holeScale).rotateZ(90.deg).translate(0, len / 2, 0) -
            ct.scale(holeScale).rotateZ(90.deg).translate(len / 2, 0, 0)

    fun piece3() = round() +
            ct.rotateZ(90.deg).translate(-len / 2, 0, 0) -
            cs.scale(holeScale).rotateZ(90.deg).translate(0, len / 2, 0)

    fun piece4() = round() +
            ct.rotateZ(90.deg).translate(-len / 2, 0, 0) +
            ct.translate(0, -len / 2, 0)

    fun base() = csg {
        val l1 = 3.02 * len
        val l2 = 3.2 * len
        round(l2, r1) - round(l1, r1).translate(0, 0, h / 2)
    }

    val a = 1.3 * len
    model(
        File("target/puzzle-pieces.stl"),
        piece1().translate(0, 0, 0),
        piece1().translate(a, 0, 0),
        piece2().translate(2 * a, 0, 0),
        piece2().translate(0, a, 0),
        piece2().translate(a, a, 0),
        piece2().translate(2 * a, a, 0),
        piece3().translate(0, 2 * a, 0),
        piece3().translate(a, 2 * a, 0),
        piece4().translate(2 * a, 2 * a, 0)
    )

    model(File("target/puzzle-base.stl"), base())
}
