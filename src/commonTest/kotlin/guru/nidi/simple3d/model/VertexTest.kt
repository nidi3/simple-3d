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
        val pos = v(1, 2, 3)
        val normal = v(4, 5, 6)
        assertEquals(Vertex(pos, -normal), -Vertex(pos, normal))
    }

    @Test
    fun interpolate() {
        val a = Vertex(v(1, 2, 3), v(4, 5, 6))
        val b = Vertex(v(3, 5, 7), v(6, 4, 5))
        assertEquals(a, a.interpolate(b, 0.0))
        assertEquals(Vertex(v(2, 3.5, 5), v(5, 4.5, 5.5)), a.interpolate(b, 0.5))
        assertEquals(b, a.interpolate(b, 1.0))
    }
}
