package guru.nidi.simple3d.model

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

open class Line(val a: Vector, val b: Vector) {
    protected fun insideSegment(p: Vector): Boolean =
        min(abs(p.x - a.x), abs(p.x - b.x)) +
                min(abs(p.y - a.y), abs(p.y - b.y)) +
                min(abs(p.z - a.z), abs(p.z - b.z)) > EPSILON &&
                onSegment(p)

    protected fun onSegment(p: Vector): Boolean =
        p.x >= min(a.x, b.x) && p.x <= max(a.x, b.x) &&
                p.y >= min(a.y, b.y) && p.y <= max(a.y, b.y) &&
                p.z >= min(a.z, b.z) && p.z <= max(a.z, b.z) &&
                onStraight(p)

    protected fun onStraight(p: Vector): Boolean = when {
        abs(a.x - b.x) > EPSILON -> {
            abs(p.y - (a.y + (p.x - a.x) * ((b.y - a.y) / (b.x - a.x)))) < EPSILON &&
                    abs(p.z - (a.z + (p.x - a.x) * ((b.z - a.z) / (b.x - a.x)))) < EPSILON
        }
        abs(a.y - b.y) > EPSILON -> {
            abs(p.x - (a.x + (p.y - a.y) * ((b.x - a.x) / (b.y - a.y)))) < EPSILON &&
                    abs(p.z - (a.z + (p.y - a.y) * ((b.z - a.z) / (b.y - a.y)))) < EPSILON
        }
        abs(a.z - b.z) > EPSILON -> {
            abs(p.y - (a.y + (p.z - a.z) * ((b.y - a.y) / (b.z - a.z)))) < EPSILON &&
                    abs(p.x - (a.x + (p.z - a.z) * ((b.x - a.x) / (b.z - a.z)))) < EPSILON
        }
        else -> throw IllegalArgumentException("a and b are equal")
    }
}

class OpenSegment(a: Vector, b: Vector) : Line(a, b) {
    operator fun contains(p: Vector): Boolean = insideSegment(p)
}

class Segment(a: Vector, b: Vector) : Line(a, b) {
    operator fun contains(p: Vector): Boolean = onSegment(p)
}

class Straight(a: Vector, b: Vector) : Line(a, b) {
    operator fun contains(p: Vector): Boolean = onStraight(p)
}
