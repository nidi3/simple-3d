package guru.nidi.simple3d.vectorize

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SimplifyTest {
    @Test
    fun simple() {
        val ps = listOf(Point(0, 0), Point(11, 10), Point(20, 20))
        assertEquals(ps, simplify(ps, .1))
        assertEquals(listOf(Point(0, 0), Point(20, 20)), simplify(ps, 1.0))
    }
}