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

import kotlin.js.JsName
import kotlin.math.*

data class Vector(val x: Double, val y: Double, val z: Double) {
    companion object {
        fun ofSpherical(r: Double, theta: Double, phi: Double) =
            Vector(r * sin(theta) * cos(phi), r * sin(theta) * sin(phi), r * cos(theta))
    }

    operator fun unaryMinus() = Vector(-x, -y, -z)
    operator fun plus(a: Vector) = Vector(x + a.x, y + a.y, z + a.z)
    operator fun minus(a: Vector) = Vector(x - a.x, y - a.y, z - a.z)
    operator fun times(a: Double) = Vector(x * a, y * a, z * a)
    operator fun div(a: Double) = Vector(x / a, y / a, z / a)
    operator fun times(a: Vector) = x * a.x + y * a.y + z * a.z
    infix fun x(a: Vector) = Vector(y * a.z - z * a.y, z * a.x - x * a.z, x * a.y - y * a.x)
    val length get() = sqrt(this * this)
    fun unit() = this / length
    fun interpolate(a: Vector, t: Double) = this + (a - this) * t
    fun abs() = Vector(abs(x), abs(y), abs(z))
    infix fun scale(a: Vector) = Vector(x * a.x, y * a.y, z * a.z)
    fun scale(x: Number, y: Number, z: Number) = scale(Vector(x.toDouble(), y.toDouble(), z.toDouble()))
    infix fun scaleInv(a: Vector) = Vector(x / a.x, y / a.y, z / a.z)

    fun inSegment(a: Vector, b: Vector): Boolean =
        min(abs(x - a.x), abs(x - b.x)) +
                min(abs(y - a.y), abs(y - b.y)) +
                min(abs(z - a.z), abs(z - b.z)) > EPSILON &&
                onSegment(a, b)

    fun onSegment(a: Vector, b: Vector): Boolean =
        x >= min(a.x, b.x) && x <= max(a.x, b.x) &&
                y >= min(a.y, b.y) && y <= max(a.y, b.y) &&
                z >= min(a.z, b.z) && z <= max(a.z, b.z) &&
                onStraight(a, b)

    fun onStraight(a: Vector, b: Vector): Boolean = when {
        abs(a.x - b.x) > EPSILON -> {
            abs(y - (a.y + (x - a.x) * ((b.y - a.y) / (b.x - a.x)))) < EPSILON &&
                    abs(z - (a.z + (x - a.x) * ((b.z - a.z) / (b.x - a.x)))) < EPSILON
        }
        abs(a.y - b.y) > EPSILON -> {
            abs(x - (a.x + (y - a.y) * ((b.x - a.x) / (b.y - a.y)))) < EPSILON &&
                    abs(z - (a.z + (y - a.y) * ((b.z - a.z) / (b.y - a.y)))) < EPSILON
        }
        abs(a.z - b.z) > EPSILON -> {
            abs(y - (a.y + (z - a.z) * ((b.y - a.y) / (b.z - a.z)))) < EPSILON &&
                    abs(x - (a.x + (z - a.z) * ((b.x - a.x) / (b.z - a.z)))) < EPSILON
        }
        else -> throw IllegalArgumentException("a and b are equal")
    }
}

operator fun Int.times(a: Vector) = a * this.toDouble()
operator fun Double.times(a: Vector) = a * this

val origin = v(0, 0, 0)
val unit = v(1, 1, 1)
val xUnit = v(1, 0, 0)
val yUnit = v(0, 1, 0)
val zUnit = v(0, 0, 1)

@JsName("v")
fun v(a: Number, b: Number, c: Number) = Vector(a.toDouble(), b.toDouble(), c.toDouble())
