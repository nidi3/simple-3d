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
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

fun main() {
    val h = 3.0
    val len = 30.0
    val r1 = .1 * len
    val rs1 = .2 * len
    val rs2 = .12 * len
    val rt1 = .27 * len
    val rt2 = .1 * len
    val holeScale = v(1.03, 1.03, 1.03)

    fun ellipseFunc(min: Double, max: Double): (Double, Double) -> Double {
        return { angle, _ ->
            val a = min * sin(angle)
            val b = max * cos(angle)
            min * max / (sqrt(a * a + b * b))
        }
    }

    val cs = cylinder(slices = 64, radiusFunc = ellipseFunc(rs1, rs2)).rotateX(90.deg())
        .scale(v(1, 1, h / 2))
    val ct = cylinder(slices = 64, radiusFunc = ellipseFunc(rt1, rt2)).rotateX(90.deg())
        .scale(v(1, 1, h / 2))


    fun round(len: Double, r: Double): Csg {
        val corner = (cube(center = origin) -
                cylinder(end = 2.0 * yUnit, radius = 2.0, slices = 64).rotateX(90.deg()).translate(v(-1, -1, 0)))
            .scale(v(r, r, h * 2))
        return cube().scale(v(len / 2, len / 2, h / 2)) -
                corner.rotateZ(0.deg()).translate(v(len - r, len - r, 0)) -
                corner.rotateZ(180.deg()).translate(v(r, r, 0)) -
                corner.rotateZ(90.deg()).translate(v(len - r, r, 0)) -
                corner.rotateZ(270.deg()).translate(v(r, len - r, 0))
    }

    fun round(): Csg = round(len, r1)

    fun piece1(): Csg {
        return round() +
                cs.translate(v(0, len / 2, h / 2)) -
                cs.scale(holeScale).rotateZ(90.deg()).translate(v(len / 2, len, h / 2)) -
                cs.scale(holeScale).translate(v(len, len / 2, h / 2))
    }

    fun piece2(): Csg {
        return round() +
                cs.translate(v(0, len / 2, h / 2)) -
                cs.scale(holeScale).rotateZ(90.deg()).translate(v(len / 2, len, h / 2)) -
                ct.scale(holeScale).rotateZ(90.deg()).translate(v(len, len / 2, h / 2))
    }

    fun piece3(): Csg {
        return round() +
                ct.rotateZ(90.deg()).translate(v(0, len / 2, h / 2)) -
                cs.scale(holeScale).rotateZ(90.deg()).translate(v(len / 2, len, h / 2))
    }

    fun piece4(): Csg {
        return round() +
                ct.rotateZ(90.deg()).translate(v(0, len / 2, h / 2)) +
                ct.translate(v(len / 2, 0, h / 2))
    }

    fun base(): Csg {
        val l1 = 3.02 * len
        val l2 = 3.2 * len
        return round(l2, r1).scale(v(1, 1, 1)) - round(l1, r1).translate(v((l2 - l1) / 2, (l2 - l1) / 2, h / 2))
    }

    model(File("target/p1/piece1.stl")) {
        add(piece1())
    }
    model(File("target/p1/piece2.stl")) {
        add(piece2())
    }
    model(File("target/p1/piece3.stl")) {
        add(piece3())
    }
    model(File("target/p1/piece4.stl")) {
        add(piece4())
    }
    model(File("target/p1/base.stl")) {
        add(base())
    }

}
