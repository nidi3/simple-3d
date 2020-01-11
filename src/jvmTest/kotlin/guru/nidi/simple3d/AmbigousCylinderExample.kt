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
import guru.nidi.simple3d.model.Vector
import guru.nidi.simple3d.model.deg
import guru.nidi.simple3d.model.heightModel
import guru.nidi.simple3d.model.prismRing
import java.io.File
import kotlin.math.*

fun main() {
    model(File("target/ambCylinder.stl")) {
        //        val c = cylinder() { angle, _ -> 1 / cos(PI / 4 - angle % (PI / 2)) }
//        val c2 = cylinder() { angle, _ -> 1 / (.5 + .5 * cos(PI / 4 - angle % (PI / 2))) }
//            add(c)
//            add(c2.translate(2, 0, 0))
        val points = (0 until 360 step 5).map { a ->
            //            val r = 9.0 / (.5 + .5 * cos((45 - a % 90).deg()))
//            Vector(r * sin((a + 0).deg()), r * cos((a + 0).deg()), 0.0)
            val c = cos((a + 0).deg)
            val s = sin((a + 0).deg)
            Vector(9.0 * sqrt(abs(c)) * sign(c), 9.0 * sqrt(abs(s)) * sign(s), 0.0)
        }
        val len = 25.0
        val r = prismRing(1.0, len, points)
        val xh = mutableListOf<List<Double>>()
        for (x in 0..20) {
            val yh = mutableListOf<Double>()
            for (y in 0..20) {
                yh.add(3 + 3 * sin(x.toDouble() / 20 * PI + y.toDouble() / 20 * PI))
            }
            xh.add(yh)
        }
        val h = heightModel(xh).translate(-10, -10, -len).rotateX(180.deg)
        add((r - h).rotateZ(45.deg).scale(1.5, 1.5, 1))
//        add((r - h).scale(1.2, 1.2, 1))
//        add(h)
    }
}
