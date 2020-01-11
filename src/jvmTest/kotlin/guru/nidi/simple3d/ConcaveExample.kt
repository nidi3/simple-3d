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
import guru.nidi.simple3d.model.v
import java.io.File

fun main() {
    model(File("target/concave.stl")) {
        val vs = listOf(v(0, 0, 0), v(1, 1, 0), v(0, 2, 0), v(3, 2, 0), v(2, 1, 0), v(3, 0, 0))
        val xs = listOf(v(1, 1, 0), v(0, 2, 0), v(3, 2, 0), v(2, 1, 0), v(3, 0, 0), v(0, 0, 0))
        add(prism(10.0, true, xs))
        add(prism(10.0, false, vs).translate(5, 0, 0))
    }
}
