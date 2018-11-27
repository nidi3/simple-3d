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