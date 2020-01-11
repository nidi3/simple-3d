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
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

operator fun Int.times(a: Vector) = a * this.toDouble()
operator fun Double.times(a: Vector) = a * this

val origin = v(0, 0, 0)
val unit = v(1, 1, 1)
val xUnit = v(1, 0, 0)
val yUnit = v(0, 1, 0)
val zUnit = v(0, 0, 1)

@JsName("v")
fun v(a: Number, b: Number, c: Number) = Vector(a.toDouble(), b.toDouble(), c.toDouble())

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
}
