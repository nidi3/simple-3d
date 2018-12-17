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
import guru.nidi.simple3d.vectorize.contour
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
                if (isBlack(img.getRGB(x, y))) b.setRGB(x, y, 0xff000000.toInt()) else b.setRGB(x, y, 0xffffffff.toInt())
            }
        }
        ImageIO.write(b, "png", File("target/blackAndWhite.png"))
    }

    fun hand(w: Double, h: Double): Csg {
        val img = ImageIO.read(Thread.currentThread().contextClassLoader.getResourceAsStream("middle.jpg"))
        val c = contour(img) { isBlack(it) }
                .simplify(2.0)
                .map { it.toVector().scale(v(.15, .15, 1)) }
        val dino = prismRing(w, h, true, c)
        return dino.translate(v(0, 0, h))
    }

    fun finger(w: Double, h: Double): Csg {
        val img = ImageIO.read(Thread.currentThread().contextClassLoader.getResourceAsStream("middleOnly.png"))
        val c = contour(img) { isBlack(it) }
                .simplify(2.0)
                .map { it.toVector().scale(v(.15, .15, 1)) }
        val dino = prismRing(w, h, true, c)
        return dino.translate(v(0, 0, h))
    }

    fun fingerOpen(w: Double, h: Double): Csg {
        val img = ImageIO.read(Thread.currentThread().contextClassLoader.getResourceAsStream("middleOpen2.png"))
        val c = contour(img) { isBlack(it) }
                .simplify(1.5)
                .map { it.toVector().scale(v(.15, .15, 1)) }
        val dino = prismRing(w, h, true, c)
        return dino.translate(v(0, 0, h))
    }

    fun nail(w: Double, h: Double): Csg {
        val img = ImageIO.read(Thread.currentThread().contextClassLoader.getResourceAsStream("nail.png"))
        asBlackAndWhite(img)
        val c = contour(img) { isBlack(it) }
                .simplify(2.0)
                .map { it.toVector().scale(v(.15, .15, 1)) }
        val dino = prismRing(w, h, true, c)
        return dino.translate(v(0, 0, h))
    }

    fun nailFull(h: Double): Csg {
        val img = ImageIO.read(Thread.currentThread().contextClassLoader.getResourceAsStream("nail.png"))
        asBlackAndWhite(img)
        val c = contour(img) { isBlack(it) }
                .simplify(2.0)
                .map { it.toVector().scale(v(.15, .15, 1)) }
        val dino = prism(h, true, c)
        return dino.translate(v(0, 0, h))
    }

    fun fingerFull(h: Double): Csg {
        val img = ImageIO.read(Thread.currentThread().contextClassLoader.getResourceAsStream("middleOnly.png"))
        val c = contour(img) { isBlack(it) }
                .simplify(2.0)
                .map { it.toVector().scale(v(.15, .15, 1)) }
        val dino = prism(h, true, c)
        return dino.translate(v(0, 0, h))
    }

    fun handForm(): Csg {
        val base = hand(2.0, 2.5)
        val rest = hand(.2, 20.0)
        return base + rest
    }

    fun fingerForm(): Csg {
        val base = fingerOpen(1.8, 2.5) //- cube(center = v(25, 4, 1.25), radius = v(10, 8, 1.25))
        val rest = fingerOpen(.2, 17.5).translate(v(0, 0, 2.5))
        val top = cube(center = v(25, 4, 1.25), radius = v(4, 8, 1.25)) + cube(center = v(35, 4, 1.25), radius = v(4, 8, 1.25))
//        val nail = nail(.2, 20.0)
//        val holder = cube(center = origin, radius = v(6, 6, 1.25)).translate(v(26, 8, 1.25))
        return base + rest// +top// +holder - nailFull(2.5) + nail + base
    }

    fun round(): Csg {
        val r = 20.0
        val points = (0 until 360 step 9).map { v(r * cos(it.deg()), r * sin(it.deg()), 0) }
        return prismRing(2.0, 2.5, false, points).translate(v(0, 0, 2.5)) + prismRing(.2, 20.0, false, points).translate(v(0, 0, 20.0))
    }

    fun small(): Csg {
        val r2 = 20.0
        val p2 = (0 until 360 step 9).map { v(r2 * cos(it.deg()), r2 * sin(it.deg()), 0) }
        val m = prismRing(2.1, 2.5, false, p2).translate(v(0, 0, 2.5))

        val r = 10.0
        val points = (0 until 360 step 9).map { v(r * cos(it.deg()), r * sin(it.deg()), 0) }
        val long = prismRing(.2, 22.5, false, points).translate(v(0, 0, 20))
        val longFull = prism(25.0, true, points).translate(v(0, 0, -5))
        val base = cube(center = origin, radius = v(12, 25, 2.5))
        return (base - longFull + long - m).translate(v(0, 0, 2.5))
    }

    fun puller(): Csg {
        return (fingerFull(2.5) - finger(1.0, 2.5)).scale(v(-1, 1, 1)) + cylinder(radius = 5.0).scale(v(1, 12, 1)).rotateX(90.deg()).translate(v(-21, 52, 12))
    }

    model {
        //        add(puller())
//        add(handForm())
        add(fingerForm().translate(v(50, 0, 0)))
//        add(round().translate(v(35, -25, 0)))
//        add(small().translate(v(60, 20, 0)))
        writeBinaryStl(File("target/round2.stl"))
    }

}