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

import org.junit.jupiter.api.Test
import java.awt.image.BufferedImage
import kotlin.test.assertEquals

class ContourTest {
    @Test
    fun simple() {
        val img = image("""
------
-+++--
-+-++-
-+--+-
-++++-
------
""")
        assertEquals(listOf(
                Point(1, 2), Point(1, 4), Point(4, 4),
                Point(4, 2), Point(3, 1), Point(1, 1)),
                contour(img) { rgb -> rgb != 0 })
    }
}

fun image(pattern: String) =
        pattern.trim().split("\n").let { lines ->
            BufferedImage(lines[0].length, lines.size, BufferedImage.TYPE_INT_ARGB).apply {
                lines.forEachIndexed { y, line ->
                    line.forEachIndexed { x, c ->
                        setRGB(x, y, if (c == '+') 1 else 0)
                    }
                }

            }
        }