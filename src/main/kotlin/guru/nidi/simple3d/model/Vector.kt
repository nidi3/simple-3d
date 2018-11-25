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

import java.lang.Math.sqrt
import kotlin.math.abs

data class Vector(val x: Double, val y: Double, val z: Double) {
    companion object {
        fun ofSpherical(r: Double, theta: Double, phi: Double) =
                Vector(r * Math.sin(theta) * Math.cos(phi), r * Math.sin(theta) * Math.sin(phi), r * Math.cos(theta))
    }

    operator fun unaryMinus() = Vector(-x, -y, -z)
    operator fun plus(a: Vector) = Vector(x + a.x, y + a.y, z + a.z)
    operator fun minus(a: Vector) = Vector(x - a.x, y - a.y, z - a.z)
    operator fun times(a: Double) = Vector(x * a, y * a, z * a)
    operator fun div(a: Double) = Vector(x / a, y / a, z / a)
    operator fun times(a: Vector) = x * a.x + y * a.y + z * a.z
    infix fun x(a: Vector) = Vector(y * a.z - z * a.y, z * a.x - x * a.z, x * a.y - y * a.x)
    fun length() = sqrt(this * this)
    fun unit() = this / length()
    fun interpolate(a: Vector, t: Double) = this + (a - this) * t
    fun abs() = Vector(abs(x), abs(y), abs(z))
    infix fun scale(a: Vector) = Vector(x * a.x, y * a.y, z * a.z)
    infix fun scaleInv(a: Vector) = Vector(x / a.x, y / a.y, z / a.z)
}

operator fun Int.times(a: Vector) = a * this.toDouble()
operator fun Double.times(a: Vector) = a * this

val origin = v(0, 0, 0)
val unit = v(1, 1, 1)

fun v(a: Double, b: Double, c: Double) = Vector(a, b, c)
fun v(a: Int, b: Double, c: Double) = Vector(a.toDouble(), b, c)
fun v(a: Double, b: Int, c: Double) = Vector(a, b.toDouble(), c)
fun v(a: Double, b: Double, c: Int) = Vector(a, b, c.toDouble())
fun v(a: Int, b: Int, c: Double) = Vector(a.toDouble(), b.toDouble(), c)
fun v(a: Int, b: Double, c: Int) = Vector(a.toDouble(), b, c.toDouble())
fun v(a: Double, b: Int, c: Int) = Vector(a, b.toDouble(), c.toDouble())
fun v(a: Int, b: Int, c: Int) = Vector(a.toDouble(), b.toDouble(), c.toDouble())
