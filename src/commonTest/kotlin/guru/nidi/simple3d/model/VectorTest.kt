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
        assertEquals(Vector(-1.0, -2.0, -3.0), -Vector(1.0, 2.0, 3.0))
    }

    @Test
    fun plus() {
        assertEquals(Vector(6.0, 8.0, 10.0), Vector(1.0, 2.0, 3.0) + Vector(5.0, 6.0, 7.0))
    }

    @Test
    fun minus() {
        assertEquals(Vector(-4.0, -2.0, 0.0), Vector(1.0, 2.0, 3.0) - Vector(5.0, 4.0, 3.0))
    }

    @Test
    fun mult() {
        assertEquals(Vector(2.0, 4.0, 6.0), Vector(1.0, 2.0, 3.0) * 2.0)
    }

    @Test
    fun div() {
        assertEquals(Vector(.5, 1.0, 1.5), Vector(1.0, 2.0, 3.0) / 2.0)
    }

    @Test
    fun dot() {
        assertEquals(20.0, Vector(1.0, 2.0, 3.0) * Vector(2.0, 3.0, 4.0))
    }

    @Test
    fun cross() {
        assertEquals(Vector(-1.0, 2.0, -1.0), Vector(1.0, 2.0, 3.0) x Vector(2.0, 3.0, 4.0))
    }

    @Test
    fun length() {
        assertEquals(sqrt(4.0 + 9.0 + 16.0), Vector(2.0, 3.0, 4.0).length)
    }

    @Test
    fun unit() {
        val len = sqrt(4.0 + 16.0 + 64.0)
        assertEquals(Vector(2.0 / len, 4.0 / len, 8.0 / len), Vector(2.0, 4.0, 8.0).unit())
    }

    @Test
    fun interpolate() {
        val v = Vector(3.0, 5.0, 7.0)
        assertEquals(Vector(2.0, 3.0, 4.0), Vector(2.0, 3.0, 4.0).interpolate(v, 0.0))
        assertEquals(Vector(2.5, 4.0, 5.5), Vector(2.0, 3.0, 4.0).interpolate(v, 0.5))
        assertEquals(Vector(3.0, 5.0, 7.0), Vector(2.0, 3.0, 4.0).interpolate(v, 1.0))
    }

    @Test
    fun inSegment() {
        val pos = Vector(12.5, 1.9015085966428351, -3.338178405040813)
        val a = Vector(12.5, 1.9615705608064604, -3.1401806440322546)
        val b = Vector(12.5, 1.9015085966428351, -3.7500000000000004)
        assertFalse(pos.inSegment(a, b))
    }
}
