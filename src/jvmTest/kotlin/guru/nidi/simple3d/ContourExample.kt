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
import guru.nidi.simple3d.model.prism
import guru.nidi.simple3d.vectorize.Image
import guru.nidi.simple3d.vectorize.outline
import guru.nidi.simple3d.vectorize.simplify
import java.io.File

fun main() {
    model(File("target/dino.stl")) {
        val img = Image.fromClasspath("brontosaurus-pattern.gif")
        val c = outline(img) { rgb -> rgb < 0xffffff }
            .simplify(5.0)
            .map { it.toVector() / 10.0 }
        add(prism(10.0, c))
    }
}
