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

import guru.nidi.simple3d.model.*
import guru.nidi.simple3d.vectorize.contour
import guru.nidi.simple3d.vectorize.simplify
import java.io.File
import javax.imageio.ImageIO

fun main() {
    fun dino(h: Double, w: Double): Csg {
        val img = ImageIO.read(Thread.currentThread().contextClassLoader.getResourceAsStream("round-dino.png"))
        val c = contour(img) { rgb -> rgb != 0 }
                .simplify(2.0)
                .map { it.toVector().scale(v(.15, .15, 1)) }
        val dino = prismRing(w, h, true, c)
        val dinoBox = dino.boundingBox()
        return dino.translate(v(0, 0, h))
    }

    model {
        val base = dino(2.5, 2.0)
        val rest = dino(10.0, .2)
//        val small = form.growLinear(v(-5, -5, 10))
//        val small = form.scale(v(.9, .9, 2))
//        val bb = small.boundingBox()
        add(base)
        add(rest)
//        add(small.translate(v((dino.width - (bb.second.x - bb.first.x)) / 2, (dino.height - (bb.second.y - bb.first.y)) / 2, 0)))
        write(File("target/round.stl"), "csg")
    }

}