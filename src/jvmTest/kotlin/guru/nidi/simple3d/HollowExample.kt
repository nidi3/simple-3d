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
import javax.imageio.ImageIO
import kotlin.math.min

fun main() {
    fun base(r: Double, b: Double, h: Double): Csg {
        val base = cube(length = v(2 * r, 2 * r, b))
        val corner = cube(length = v(2 * b, 2 * b, 2 * h)) +
                cube(center = v(b / 2, b * 2, 0), length = v(b, 2 * b, 2 * h)) +
                cube(center = v(b * 2, b / 2, 0), length = v(2 * b, b, 2 * h))

        val e = r - b
        return base + corner.rotateZ(180.deg).translate(e, e, h) +
                corner.rotateZ(270.deg).translate(e, -e, h) +
                corner.rotateZ(90.deg).translate(-e, e, h) +
                corner.translate(-e, -e, h)
    }

    fun top(r: Double, b: Double): Csg {
        val b2 = 1.0
        val d2 = r - 2.5 * b2
        val d3 = r - .5 * b2
        val base = cube(length = v(2 * r, 2 * r, b))
        val inner = cube(length = v(2 * r - 4 * b2, b2, 2 * b2))
        val innerC = cylinder(height = 10 + b / 2, slices = 24) { _, z -> 4.75 - .5 * z }
            .translate(0, 5 - b / 4, 0).rotateX(90.deg)
        val outerC = cylinder(height = 10 + b / 2, slices = 24) { _, z -> 5.75 - .5 * z }
            .translate(0, 5 - b / 4, 0).rotateX(90.deg)
        val cone = base + outerC - innerC
        val innerBorders = inner.translate(0, d2, b2) +
                inner.translate(0, d3, b2) +
                inner.translate(0, -d2, b2) +
                inner.translate(0, -d3, b2)
        val outerBorders = inner.rotateZ(90.deg).translate(d2, 0, b2) +
                inner.rotateZ(90.deg).translate(d3, 0, b2) +
                inner.rotateZ(90.deg).translate(-d2, 0, b2) +
                inner.rotateZ(90.deg).translate(-d3, 0, b2)
        return cone + innerBorders + outerBorders
    }

    data class Dino(val csg: Csg, val width: Double, val height: Double)

    fun dino(b: Double): Dino {
        val img =
            Image(ImageIO.read(Thread.currentThread().contextClassLoader.getResourceAsStream("brontosaurus-pattern.gif")))
        val c = outline(img) { rgb -> rgb < 0xffffff }
            .simplify(3.0)
            .map { it.toVector() }
        val dino = prism(b, c)
        val dinoBox = dino.boundingBox
        return Dino(dino.translate(-dinoBox.from.x, -dinoBox.from.y, 0), dinoBox.size().x, dinoBox.size().y)
    }

    fun side(r: Double, b: Double, h: Double): Csg {
        val f = r - 4.5 * b
        val dino = dino(b)
        val factor = min(1.7 * f / dino.width, 1.7 * h / dino.height)
        val scaleDino = dino.csg.scale(factor, factor, 1)

        val side = cube(length = v(2 * r - 4 * b, 2 * (h - b), b))
        val inner1 = cube(length = v(b, 2 * (h - b), 2 * b))
        val inner2 = cube(length = v(b, 2 * (r - 4.5 * b), 2 * b))
        val base = side - scaleDino.translate(-dino.width * factor / 2, -dino.height * factor / 2, b / 2)
        return base + inner1.translate(f, 0, b) +
                inner1.translate(-f, 0, b) +
                inner2.rotateZ(90.deg).translate(0, r - 1.5 * b, b) +
                inner2.rotateZ(90.deg).translate(0, -(r - 1.5 * b), b)
    }

    model(File("target/cube.stl")) {
        val r = 15.0
        val h = r
        val b = 1.0
//        val d = r - b / 2

//        val dino = dino(b)
//
//        val factor = Math.min(1.5 * r / dino.width, 1.5 * r / dino.height)
//        val scaleDino = dino.csg.scale(factor, factor, 1)
//
//        val base = cube(center = origin, radius = v(r, r, b / 2))
//        val dBase = base - scaleDino.translate(-dino.width * factor / 2, -dino.height * factor / 2, b / 2 - .45)
//        add(dBase)
//        add(dBase.rotateZ(180.deg()).rotateX(90.deg()).translate(0, d, d))
//        add(dBase.rotateZ(180.deg()).rotateX(90.deg()).translate(0, -d, d))
//        add(dBase.rotateZ(-90.deg()).rotateY(90.deg()).translate(d, 0, d))
//        add(dBase.rotateZ(-90.deg()).rotateY(90.deg()).translate(-d, 0, d))
        add(base(r, b, h))
        add(side(r, b, h).translate(0, 2.5 * r, 0))
        add(side(r, b, h).translate(2.5 * r, 2.5 * r, 0))
        add(side(r, b, h).translate(0, 5.0 * r, 0))
        add(side(r, b, h).translate(2.5 * r, 5.0 * r, 0))
        add(top(r, b).translate(2.5 * r, 0, 0))

//        val b2 = 1.0
//        val d2 = r - 3 * b2 / 2
//        transform(translate(2.5 * r, 0, 0)) {
//            val inner = cube(center = origin, radius = v(r - b2, b2 / 2, b2))
//            val innerC = cylinder(slices = 24, start = v(0, 0, -b / 2), end = v(0, 0, 10)) { _, z -> 4.75 - .5 * z }
//            val outerC = cylinder(slices = 24, start = v(0, 0, -b / 2), end = v(0, 0, 10)) { _, z -> 5.75 - .5 * z }
//            add(base + outerC - innerC)
//            add(inner.translate(0, d2, b2))
//            add(inner.translate(0, -d2, b2))
//            add(inner.rotateZ(90.deg()).translate(d2, 0, b2))
//            add(inner.rotateZ(90.deg()).translate(-d2, 0, b2))
//        }
    }
}
