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
import guru.nidi.simple3d.vectorize.Image
import guru.nidi.simple3d.vectorize.outline
import guru.nidi.simple3d.vectorize.simplify
import java.io.File
import kotlin.math.cos
import kotlin.math.sin

fun main() {
    fun isBlack(rgb: Int): Boolean {
        val red = (rgb ushr 16) and 0xFF
        val green = (rgb ushr 8) and 0xFF
        val blue = (rgb ushr 0) and 0xFF
        val luminance = (red * 0.2126f + green * 0.7152f + blue * 0.0722f) / 255
        return luminance < 0.9
    }

    fun dino(w: Double, h: Double): Csg {
        val img = Image.fromClasspath("guitar.jpg")
//        val img = Image.fromClasspath("Monolophosaurus.jpg")
        val c = outline(img) { isBlack(it) }
            .simplify(2.0)
            .map { it.toVector().scale(.05, .05, 1) }
        val dino = prismRing(w, h, c)
        return dino.translate(0, 0, h)
    }

    fun dinoFull(h: Double): Csg {
        val img = Image.fromClasspath("round-dino.png")
        val c = outline(img) { isBlack(it) }
            .simplify(2.0)
            .map { it.toVector().scale(.15, .15, 1) }
        val dino = prism(h, c)
        return dino.translate(0, 0, h)
    }

    fun dinoForm(): Csg {
        val base = dino(2.0, 2.5)
        val rest = dino(.2, 20.0)
//        val small = form.growLinear(v(-5, -5, 10))
//        val small = form.scale(.9, .9, 2)
//        val bb = small.boundingBox()
        return base + rest
//        add(small.translate((dino.width - (bb.second.x - bb.first.x)) / 2, (dino.height - (bb.second.y - bb.first.y)) / 2, 0))
    }

    fun round(): Csg {
        val r = 20.0
        val points = (0 until 360 step 9).map { v(r * cos(it.deg), r * sin(it.deg), 0) }
        return -prismRing(2.0, 2.5, points).translate(0, 0, 2.5) +
                -prismRing(.2, 20, points).translate(0, 0, 20)
    }

    fun small(): Csg {
        val r2 = 20.0
        val p2 = (0 until 360 step 9).map { v(r2 * cos(it.deg), r2 * sin(it.deg), 0) }
        val m = -prismRing(2.1, 2.5, p2).translate(0, 0, 2.5)

        val r = 10.0
        val points = (0 until 360 step 9).map { v(r * cos(it.deg), r * sin(it.deg), 0) }
        val long = -prismRing(.2, 22.5, points).translate(0, 0, 20)
        val longFull = prism(25, points).translate(0, 0, -5)
        val base = cube(radius = v(12, 25, 2.5))
        return (base - longFull + long - m).translate(0, 0, 2.5)
    }

    fun puller(): Csg {
        return (dinoFull(2.5) - dino(1.0, 2.5)).scale(-1, 1, 1) +
                cylinder(radius = 5).scale(1, 24, 1).rotateX(90.deg).translate(-21, 52, 12)
    }

    model(File("target/guitar.stl")) {
        //        add(puller())
        add(dinoForm())
//        add(round().translate(35, -25, 0))
//        add(small().translate(60, 20, 0))
    }

}
