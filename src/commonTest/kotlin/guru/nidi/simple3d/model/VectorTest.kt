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

import kotlin.math.sqrt
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class VectorTest {
    @Test
    fun neg() {
        assertEquals(v(-1, -2, -3), -v(1, 2, 3))
    }

    @Test
    fun plus() {
        assertEquals(v(6, 8, 10), v(1, 2, 3) + v(5, 6, 7))
    }

    @Test
    fun minus() {
        assertEquals(v(-4, -2, 0), v(1, 2, 3) - v(5, 4, 3))
    }

    @Test
    fun mult() {
        assertEquals(v(2, 4, 6), v(1, 2, 3) * 2.0)
    }

    @Test
    fun div() {
        assertEquals(v(.5, 1, 1.5), v(1, 2, 3) / 2.0)
    }

    @Test
    fun dot() {
        assertEquals(20.0, v(1, 2, 3) * v(2, 3, 4))
    }

    @Test
    fun cross() {
        assertEquals(v(-1, 2, -1), v(1, 2, 3) x v(2, 3, 4))
    }

    @Test
    fun length() {
        assertEquals(sqrt(4.0 + 9 + 16), v(2, 3, 4).length)
    }

    @Test
    fun unit() {
        val len = sqrt(4.0 + 16 + 64)
        assertEquals(v(2 / len, 4 / len, 8 / len), v(2, 4, 8).unit())
    }

    @Test
    fun interpolate() {
        val v = v(3, 5, 7)
        assertEquals(v(2, 3, 4), v(2, 3, 4).interpolate(v, 0.0))
        assertEquals(v(2.5, 4, 5.5), v(2, 3, 4).interpolate(v, 0.5))
        assertEquals(v(3, 5, 7), v(2, 3, 4).interpolate(v, 1.0))
    }

    @Test
    fun inSegment() {
        val pos = v(12.5, 1.9015085966428351, -3.338178405040813)
        val a = v(12.5, 1.9615705608064604, -3.1401806440322546)
        val b = v(12.5, 1.9015085966428351, -3.7500000000000004)
        assertFalse(pos in Segment(a, b))
    }
}
