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
    model(File("target/fix.stl")) {
        val base = prism(30, listOf(v(-2, 0, 0), v(-4, 0, 4), v(4, 0, 4), v(2, 0, 0)))
//                val bigConn = conn.scale(1.2 * unit)
        val conn = base.rotateZ(45.deg)
        val bigConn = base.growLinear(v(1, 1, 0)).translate(0, 0, 0).rotateZ(45.deg)

//                add(conn)
//                add(bigConn)

        add(cube(v(0, 0, 3), v(10, 10, 3)))
        add(conn.translate(-15, -5, 6) and cube(length = 20 * unit))
        add(conn.translate(-5, -15, 6) and cube(length = 20 * unit))

        val neg = cube(v(30, 10, 0), v(10, 10, 5)) - bigConn.translate(15, 5, -5) - bigConn.translate(25, -5, -5)
        add(neg.rotateX(180.deg).translate(0, 0, 5))
    }
}
