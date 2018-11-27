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

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class AffineTransformTest {
    @Test
    fun determinant() {
        val a = AffineTransform(
                1.0, 2.0, 3.0, 4.0,
                4.0, 3.0, 2.0, 1.0,
                1.0, 2.0, 1.0, 2.0)
        assertEquals(10.0, a.determinant())
    }

    @Test
    fun inverse() {
        val a = AffineTransform(
                1.0, 2.0, 3.0, 4.0,
                4.0, 3.0, 2.0, 1.0,
                1.0, 2.0, 1.0, 2.0)
        assertEquals(AffineTransform(
                -.1, .4, -.5, 1.0,
                -.2, -.2, 1.0, -1.0,
                .5, 0.0, -.5, -1.0
        ), a.inverse())
        val v = v(1, 2, 3)
        val va = a.inverse().applyTo(a.applyTo(v))
        assertEquals(v.x, va.x, .001)
        assertEquals(v.y, va.y, .001)
        assertEquals(v.z, va.z, .001)
    }
}