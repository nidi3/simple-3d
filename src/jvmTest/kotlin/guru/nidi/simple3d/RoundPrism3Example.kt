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
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
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

    fun asBlackAndWhite(img: BufferedImage) {
        val b = BufferedImage(img.width, img.height, BufferedImage.TYPE_INT_ARGB)
        for (x in 0 until img.width) {
            for (y in 0 until img.height) {
                if (isBlack(img.getRGB(x, y))) b.setRGB(x, y, 0xff000000.toInt())
                else b.setRGB(x, y, 0xffffffff.toInt())
            }
        }
        ImageIO.write(b, "png", File("target/blackAndWhite.png"))
    }

    fun hand(w: Double, h: Double) = csg {
        val img = Image.fromClasspath("middle.jpg")
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
        val base = hand(4.0, 2.5)
        val rest = hand(.4, 20.0)
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

    fun round() = csg {
        val r = 20.0
        val points = (0 until 360 step 9).map { v(r * cos(it.deg), r * sin(it.deg), 0) }
        -prismRing(4.0, 2.5, points).translate(0, 0, 2.5) +
                -prismRing(.4, 20, points).translate(0, 0, 20.0)
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

    fun puller() = (fingerFull(2.5) - finger(2.0, 2.5)).scale(-1, 1, 1) +
            cylinder(radius = 5).scale(1, 24, 1).rotateX(90.deg).translate(-21, 52, 12)

    model(File("target/round2.stl")) {
        add(
//            puller(),
//            handForm(),
            fingerForm().translate(50, 0, 0)
//            round().translate(35, -25, 0),
//            small().translate(60, 20, 0)
        )
    }

}
