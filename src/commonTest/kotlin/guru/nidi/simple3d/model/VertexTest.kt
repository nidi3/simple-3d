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

import kotlin.test.Test
import kotlin.test.assertEquals

class VertexTest {
    @Test
    fun init() {
        val pos = Vector(1.0, 2.0, 3.0)
        val normal = Vector(4.0, 5.0, 6.0)
        assertEquals(Vertex(pos, -normal), -Vertex(pos, normal))
    }

    @Test
    fun interpolate() {
        val a = Vertex(Vector(1.0, 2.0, 3.0), Vector(4.0, 5.0, 6.0))
        val b = Vertex(Vector(3.0, 5.0, 7.0), Vector(6.0, 4.0, 5.0))
        assertEquals(a, a.interpolate(b, 0.0))
        assertEquals(Vertex(Vector(2.0, 3.5, 5.0), Vector(5.0, 4.5, 5.5)), a.interpolate(b, 0.5))
        assertEquals(b, a.interpolate(b, 1.0))
    }
}