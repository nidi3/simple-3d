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
import guru.nidi.simple3d.model.model
import guru.nidi.simple3d.model.prism
import guru.nidi.simple3d.vectorize.contour
import guru.nidi.simple3d.vectorize.simplify
import java.io.File
import javax.imageio.ImageIO

fun main() {
    model {
        val img = ImageIO.read(Thread.currentThread().contextClassLoader.getResourceAsStream("brontosaurus-pattern.gif"))
        val c = contour(img) { rgb -> rgb < 0xffffff }
                .simplify(5.0)
                .map { it.toVector() / 10.0 }
        add(prism(10.0, true, c))
        writeBinaryStl(File("target/dino.stl"))
    }
}