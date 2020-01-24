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
import java.awt.BasicStroke
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import kotlin.math.sqrt

fun main() {
    fun isBlack(rgb: Int): Boolean {
        val red = (rgb ushr 16) and 0xFF
        val green = (rgb ushr 8) and 0xFF
        val blue = (rgb ushr 0) and 0xFF
        val luminance = (red * 0.2126f + green * 0.7152f + blue * 0.0722f) / 255
        return luminance < 0.9
    }

    fun asBlackAndWhite(img: BufferedImage) {
        val b = BufferedImage(img.width, img.height, BufferedImage.TYPE_INT_ARGB)
        for (x in 0 until img.width) {
            for (y in 0 until img.height) {
                if (isBlack(img.getRGB(x, y))) b.setRGB(x, y, 0xff000000.toInt()) else b.setRGB(
                    x,
                    y,
                    0xffffffff.toInt()
                )
            }
        }
        ImageIO.write(b, "png", File("target/blackAndWhite.png"))
    }

    fun fist(): List<Vector> {
        val img = Image.fromClasspath("fist.jpg")
        return outline(img) { isBlack(it) }
            .simplify(2.0)
            .map { it.toVector().scale(.15, .15, 1) }
    }

    fun hand(w: Double, h: Double) = csg {
        val img = Image.fromClasspath("middle.jpg")
        val c = outline(img) { isBlack(it) }
            .simplify(2.0)
            .map { it.toVector().scale(.15, .15, 1) }
        prismRing(w, h, c).translate(0, 0, h)
    }

    fun peaceForm(w: Double, h: Double, img: Image) = csg {
        val c = outline(img) { isBlack(it) }
            .simplify(2.0)
            .map { it.toVector().scale(.15, .15, 1) }
        prismRing(w, h, c).translate(0, 0, h)
    }

    fun finger(w: Double, h: Double) = csg {
        val img = Image.fromClasspath("middleOnly.png")
        val c = outline(img) { isBlack(it) }
            .simplify(2.0)
            .map { it.toVector().scale(.15, .15, 1) }
        prismRing(w, h, c).translate(0, 0, h)
    }

    fun fingerOpen(w: Double, h: Double) = csg {
        val img = Image.fromClasspath("middleOpen2.png")
        val c = outline(img) { isBlack(it) }
            .simplify(1.5)
            .map { it.toVector().scale(.15, .15, 1) }
        prismRing(w, h, c).translate(0, 0, h)
    }

    fun nail(w: Double, h: Double) = csg {
        val img = Image.fromClasspath("nail.png")
        asBlackAndWhite(img.img)
        val c = outline(img) { isBlack(it) }
            .simplify(2.0)
            .map { it.toVector().scale(.15, .15, 1) }
        prismRing(w, h, c).translate(0, 0, h)
    }

    fun nailFull(h: Double) = csg {
        val img = Image.fromClasspath("nail.png")
        asBlackAndWhite(img.img)
        val c = outline(img) { isBlack(it) }
            .simplify(2.0)
            .map { it.toVector().scale(.15, .15, 1) }
        prism(h, c).translate(0, 0, h)
    }

    fun fingerFull(h: Double) = csg {
        val img = Image.fromClasspath("middleOnly.png")
        val c = outline(img) { isBlack(it) }
            .simplify(2.0)
            .map { it.toVector().scale(.15, .15, 1) }
        prism(h, c).translate(0, 0, h)
    }

    fun handForm() = csg {
        val base = hand(2.0, 2.5)
        val rest = hand(.2, 20.0)
        base + rest
    }

    fun fingerForm() = csg {
        val base = fingerOpen(3.6, 2.5) //- cube(center = v(25, 4, 1.25), radius = v(10, 8, 1.25))
        val rest = fingerOpen(.4, 17.5).translate(0, 0, 2.5)
        val top = cube(center = v(25, 4, 1.25), length = v(8, 16, 2.5)) +
                cube(center = v(35, 4, 1.25), length = v(8, 16, 2.5))
//        val nail = nail(.4, 20.0)
//        val holder = cube(center = origin, radius = v(6, 6, 1.25)).translate(26, 8, 1.25)
        base + rest// +top// +holder - nailFull(2.5) + nail + base
    }

    fun peace(): Image {
        val img = BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB)
        val g = img.createGraphics()
        g.stroke = BasicStroke(35f)
        g.color = Color.WHITE
        g.fillRect(0, 0, 200, 200)
        g.color = Color.BLACK
        g.drawLine(100, 0, 100, 200)
        g.drawLine(100, 90, 0, 200)
        g.drawLine(100, 90, 200, 200)
        g.color = Color.WHITE
        for (x in 0 until 200) {
            for (y in 0 until 200) {
                if ((x - 100) * (x - 100) + (y - 100) * (y - 100) > 6000) g.fillRect(x, y, 1, 1)
            }
        }

        ImageIO.write(img, "png", File("target/peace.png"))
        return Image(img)
    }

    fun round() = csg {
        val peace = peace()
        val c = outline(peace) { isBlack(it) }
            .simplify(2.0)
            .map { it.toVector().scale(.2, .2, 1) }
        val p = -prismRing(.4, 20, c).translate(-20, -20, 0)
        val q = -prismRing(7, .2, 2.5, c).translate(-20, -20, 0)

        val r = 20.0
        val points = (0 until 360 step 9).map { v(r * cos(it.deg), r * sin(it.deg), 0) }
        val base = -prismRing(3, 2.5, points).translate(0, 0, 2.5)
        val rest = -prismRing(.4, 20, points).translate(0, 0, 20.0)
        p + q + base + rest
    }

    fun small() = csg {
        val r2 = 20.0
        val p2 = (0 until 360 step 9).map { v(r2 * cos(it.deg), r2 * sin(it.deg), 0) }
        val m = -prismRing(4.2, 2.5, p2).translate(0, 0, 2.5)

        val r = 10.0
        val points = (0 until 360 step 9).map { v(r * cos(it.deg), r * sin(it.deg), 0) }
        val long = -prismRing(.4, 22.5, points).translate(0, 0, 20)
        val longFull = prism(25, points).translate(0, 0, -5)
        val base = cube(length = v(24, 50, 5))
        (base - longFull + long - m).translate(0, 0, 2.5)
    }

    fun smallFist() = csg {
        val r2 = 20.0
        val p2 = (0 until 360 step 9).map { v(r2 * cos(it.deg), r2 * sin(it.deg), 0) }
        val m = -prismRing(4.2, 2.5, p2).translate(0, 0, 2.5)

        val ps = fist()
        val min = ps.fold(v(1000, 1000, 1000)) { a, n -> v(min(a.x, n.x), min(a.y, n.y), min(a.z, n.z)) }
        val points = ps.map { .25 * v(it.x - min.x, it.y - min.y, it.z - min.z) }
        val long = prismRing(.4, 22.5, points).translate(-10.5, -13.5, 20)
        val longFull = prism(25, points).translate(-10.5, -13.5, 20)
        val base = cube(length = v(24, 50, 5))
        (base - longFull + long - m).translate(0, 0, 2.5)
    }

    fun smallFlower() = csg {
        val r2 = 20.0
        val p2 = (0 until 360 step 9).map { v(r2 * cos(it.deg), r2 * sin(it.deg), 0) }
        val m = -prismRing(4.2, 2.5, p2).translate(0, 0, 2.5)

        val r = 5.0
        val r3 = 4.8
        val f = 4
        val d = f * sqrt(3.0) / 2
        val points = (0 until 360 step 9).map { v(r * cos(it.deg), r * sin(it.deg), 0) }
        val points2 = (0 until 360 step 9).map { v(r3 * cos(it.deg), r3 * sin(it.deg), 0) }
        val long = -prismRing(.4, 22.5, points).translate(-d, 0, 20) +
                -prismRing(.4, 22.5, points).translate(d, f, 20) +
                -prismRing(.4, 22.5, points).translate(d, -f, 20)
        val longFull = prism(35, points2).translate(d, -f, -5) +
                prism(35, points2).translate(d, f, -5) +
                prism(35, points2).translate(-d, 0, -5)
        val base = cube(length = v(24, 50, 5))
        (base + long - longFull - m).translate(0, 0, 2.5)
    }

    fun smallCross() = csg {
        val r2 = 20.0
        val p2 = (0 until 360 step 9).map { v(r2 * cos(it.deg), r2 * sin(it.deg), 0) }
        val m = -prismRing(4.2, 2.5, p2).translate(0, 0, 2.5)

        val r = 20.0
        val points = listOf(
            v(r / 3, 0, 0),
            v(2 * r / 3, 0, 0),
            v(2 * r / 3, r / 3, 0),
            v(r, r / 3, 0),
            v(r, 2 * r / 3, 0),
            v(2 * r / 3, 2 * r / 3, 0),
            v(2 * r / 3, r, 0),
            v(r / 3, r, 0),
            v(r / 3, 2 * r / 3, 0),
            v(0, 2 * r / 3, 0),
            v(0, r / 3, 0),
            v(r / 3, r / 3, 0)
        )
        val long = -prismRing(.4, 22.5, points).translate(-r / 2, -r / 2, 20)
        val longFull = prism(25, points).translate(-r / 2, -r / 2, -5)
        val base = cube(length = v(24, 50, 5))
        (base - longFull + long - m).translate(0, 0, 2.5)
    }

    fun smallFlash() = csg {
        val r2 = 20.0
        val p2 = (0 until 360 step 9).map { v(r2 * cos(it.deg), r2 * sin(it.deg), 0) }
        val m = -prismRing(4.2, 2.5, p2).translate(0, 0, 2.5)

        val r = 13.5
        val xs = 1.5
        val points = listOf(
            v(2 * r / 3, 0, 0),
            v(r, 0, 0),
            v(2 * r / 3, 2 * r / 3, 0),
            v(r, 2 * r / 3, 0),
            v(0, 2 * r, 0),
            v(r / 3, r, 0),
            v(0, r, 0)
        ).map { v(it.x * xs, it.y, it.z) }
        val long = -prismRing(.4, 22.5, points).translate(-r * xs / 2, -r, 20)
        val longFull = prism(25, points).translate(-r * xs / 2, -r, -5)
        val base = cube(length = v(24, 50, 5))
        (base - longFull + long - m).translate(0, 0, 2.5)
    }

    fun smallLambda() = csg {
        val r2 = 20.0
        val p2 = (0 until 360 step 9).map { v(r2 * cos(it.deg), r2 * sin(it.deg), 0) }
        val m = -prismRing(4.2, 2.5, p2).translate(0, 0, 2.5)

        val r = 13.5
        val xs = 1.5
        val points = listOf(
            v(0, 0, 0),
            v(r / 3, 0, 0),
            v(r, 2 * r, 0),
            v(2 * r / 3, 2 * r, 0),
            v(r / 2, 4 * r / 3, 0),
            v(r / 3, 2 * r, 0),
            v(0, 2 * r, 0),
            v(r / 3, r, 0)
        ).map { v(it.x * xs, it.y, it.z) }
        val long = -prismRing(.4, 22.5, points).translate(-r * xs / 2, -r, 20)
        val longFull = prism(25, points).translate(-r * xs / 2, -r, -5)
        val base = cube(length = v(24, 50, 5))
        (base - longFull + long - m).translate(0, 0, 2.5)
    }


    fun smallPeace() = csg {
        val r2 = 20.0
        val p2 = (0 until 360 step 9).map { v(r2 * cos(it.deg), r2 * sin(it.deg), 0) }
        val m = -prismRing(4.2, 2.5, p2).translate(0, 0, 2.5)

        val c = outline(peace()) { isBlack(it) }
            .simplify(2.0)
            .map { it.toVector().scale(.2, .2, 1) }
        val long = prismRing(.4, 22.5, c).translate(-20, -20, 20)
        val longFull = prism(25, c).translate(-20, -20, 20)
//        val q = prismRing(3.5, .2, 2.5, false, c).translate(-20, -20, 0)

//        val long = prismRing(.2, 22.5, false, points).translate(0, 0, 20)
//        val longFull = prism(25.0, true, points).translate(0, 0, -5)

        val base = cube(length = v(30, 50, 5))
        (base - longFull + long - m).translate(0, 0, 2.5)
    }

    fun puller() = (fingerFull(2.5) - finger(2.0, 2.5)).scale(-1, 1, 1) +
            cylinder(radius = 5).scale(1, 24, 1).rotateX(90.deg).translate(-21, 52, 12)

    model(File("target/small-fist.stl")) {
//        asBlackAndWhite(peace().img)
        add(
//            puller(),
//            handForm(),
//            fingerForm().translate(50, 0, 0),
//            smallFlower().translate(60, 20, 0),
            smallFist()
        )
    }

}
