package guru.nidi.simple3d.vectorize

import kotlin.math.abs
import kotlin.math.sqrt

fun simplify(points: List<Point>, epsilon: Double): List<Point> {
    fun dist(a: Point, b: Point, c: Point) =
            abs((c.y - a.y) * b.x - (c.x - a.x) * b.y + c.x * a.y - c.y * a.x) / sqrt((c.x - a.x).toDouble() * (c.x - a.x) + (c.y - a.y) * (c.y - a.y))

    var maxI = 0
    var maxD = 0.0
    for (i in 1..points.size - 2) {
        val d = dist(points.first(), points[i], points.last())
        if (d > maxD) {
            maxD = d
            maxI = i
        }
    }
    return if (maxD < epsilon) listOf(points.first(), points.last())
    else {
        val s1 = simplify(points.subList(0, maxI + 1), epsilon)
        val s2 = simplify(points.subList(maxI, points.size), epsilon)
        s1.subList(0, s1.size - 1) + s2
    }
}