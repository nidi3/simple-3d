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
package guru.nidi.simple3d.vectorize

import kotlin.test.Test
import kotlin.test.assertEquals

class SimplifyTest {
    @Test
    fun simple() {
        val ps = listOf(Point(0, 0), Point(11, 10), Point(20, 20))
        assertEquals(ps, ps.simplify(.1))
        assertEquals(listOf(Point(0, 0), Point(20, 20)), ps.simplify(1.0))
    }
}