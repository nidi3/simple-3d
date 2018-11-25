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
package guru.nidi.simple3d.model

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PolygonTest {
    @Test
    fun flip() {
        val a = Vector(1.0, 2.0, 3.0)
        val b = Vector(1.0, 2.0, 3.0)
        val c = Vector(1.0, 2.0, 3.0)
        assertEquals(Polygon(Vertex(c, -c), Vertex(b, -b), Vertex(a, -a)),
                -Polygon(Vertex(a, a), Vertex(b, b), Vertex(c, c)))
    }
}