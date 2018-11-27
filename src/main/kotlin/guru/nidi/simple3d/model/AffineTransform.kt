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

data class AffineTransform constructor(val m00: Double, val m01: Double, val m02: Double, val m03: Double,
                                       val m10: Double, val m11: Double, val m12: Double, val m13: Double,
                                       val m20: Double, val m21: Double, val m22: Double, val m23: Double) {
    constructor() : this(
            1.0, 0.0, 0.0, 0.0,
            0.0, 1.0, 0.0, 0.0,
            0.0, 0.0, 1.0, 0.0)

    fun transpose() = AffineTransform(m00, m10, m20, 0.0, m01, m11, m21, 0.0, m02, m12, m22, 0.0)

    fun determinant() = m00 * (m11 * m22 - m12 * m21) - m10 * (m01 * m22 - m02 * m21) + m20 * (m01 * m12 - m02 * m11)

    fun adjugate(): AffineTransform {
        val x = m00 * m11 * m22 + m01 * m12 * m20 + m02 * m10 * m21 - m02 * m11 * m20 - m01 * m10 * m22 - m00 * m12 * m21
        return AffineTransform(
                m11 * m22 - m12 * m21, -m01 * m22 + m02 * m21, m01 * m12 - m02 * m11, (-m01 * m12 * m23 - m02 * m13 * m21 - m03 * m11 * m22 + m03 * m12 * m21 + m02 * m11 * m23 + m01 * m13 * m22),
                -m10 * m22 + m12 * m20, m00 * m22 - m02 * m20, -m00 * m12 + m02 * m10, (m00 * m12 * m23 + m02 * m13 * m20 + m03 * m10 * m22 - m03 * m12 * m20 - m02 * m10 * m23 - m00 * m13 * m22),
                m10 * m21 - m11 * m20, -m00 * m21 + m01 * m20, m00 * m11 - m01 * m10, (-m00 * m11 * m23 - m01 * m13 * m20 - m03 * m10 * m21 + m03 * m11 * m20 + m01 * m10 * m23 + m00 * m13 * m21)
        )
    }

    fun inverse() = (1.0 / determinant()) * adjugate()

    operator fun Double.times(a: AffineTransform) = AffineTransform(this * a.m00, this * a.m01, this * a.m02, this * a.m03,
            this * a.m10, this * a.m11, this * a.m12, this * a.m13,
            this * a.m20, this * a.m21, this * a.m22, this * a.m23)

    fun translate(v: Vector) = AffineTransform(
            m00, m01, m02, m03 + m00 * v.x + m01 * v.y + m02 * v.z,
            m10, m11, m12, m13 + m10 * v.x + m11 * v.y + m12 * v.z,
            m20, m21, m22, m23 + m20 * v.x + m21 * v.y + m22 * v.z)

    fun scale(v: Vector) = AffineTransform(
            m00 * v.x, m01 * v.y, m02 * v.z, m03,
            m10 * v.x, m11 * v.y, m12 * v.z, m13,
            m20 * v.x, m21 * v.y, m22 * v.z, m23)

    fun rotateX(a: Double) = AffineTransform(
            m00, m01 * Math.cos(a) + m02 * Math.sin(a), -m01 * Math.sin(a) + m02 * Math.cos(a), m03,
            m10, m11 * Math.cos(a) + m12 * Math.sin(a), -m11 * Math.sin(a) + m12 * Math.cos(a), m13,
            m20, m21 * Math.cos(a) + m22 * Math.sin(a), -m21 * Math.sin(a) + m22 * Math.cos(a), m23)

    fun rotateY(a: Double) = AffineTransform(
            m00 * Math.cos(a) + m02 * Math.sin(a), m01, -m00 * Math.sin(a) + m02 * Math.cos(a), m03,
            m10 * Math.cos(a) + m12 * Math.sin(a), m11, -m10 * Math.sin(a) + m12 * Math.cos(a), m13,
            m20 * Math.cos(a) + m22 * Math.sin(a), m21, -m20 * Math.sin(a) + m22 * Math.cos(a), m23)

    fun rotateZ(a: Double) = AffineTransform(
            m00 * Math.cos(a) - m01 * Math.sin(a), m00 * Math.sin(a) + m01 * Math.cos(a), m02, m03,
            m10 * Math.cos(a) - m11 * Math.sin(a), m10 * Math.sin(a) + m11 * Math.cos(a), m12, m13,
            m20 * Math.cos(a) - m21 * Math.sin(a), m20 * Math.sin(a) + m21 * Math.cos(a), m22, m23)

    fun applyTo(a: AffineTransform) = AffineTransform(
            m00 * a.m00 + m01 * a.m10 + m02 * a.m20, m00 * a.m01 + m01 * a.m11 + m02 * a.m21, m00 * a.m02 + m01 * a.m12 + m02 * a.m22, m00 * a.m03 + m01 * a.m13 + m02 * a.m23 + m03,
            m10 * a.m00 + m11 * a.m10 + m12 * a.m20, m10 * a.m01 + m11 * a.m11 + m12 * a.m21, m10 * a.m02 + m11 * a.m12 + m12 * a.m22, m10 * a.m03 + m11 * a.m13 + m12 * a.m23 + m13,
            m20 * a.m00 + m21 * a.m10 + m22 * a.m20, m20 * a.m01 + m21 * a.m11 + m22 * a.m21, m20 * a.m02 + m21 * a.m12 + m22 * a.m22, m20 * a.m03 + m21 * a.m13 + m22 * a.m23 + m23)

    fun applyTo(p: Vector) = Vector(
            m00 * p.x + m01 * p.y + m02 * p.z + m03,
            m10 * p.x + m11 * p.y + m12 * p.z + m13,
            m20 * p.x + m21 * p.y + m22 * p.z + m23)

    fun applyTo(t: Vertex) = Vertex(applyTo(t.pos), inverse().transpose().applyTo(t.normal))

    fun applyTo(t: Polygon) = Polygon(t.vertices.map { applyTo(it) })

    fun applyTo(t: Csg) = Csg(t.polygons.map { applyTo(it) })

    override fun toString(): String {
        val f = "%6.2f %6.2f %6.2f %6.2f%n"
        return String.format(f, m00, m01, m02, m03) + String.format(f, m10, m11, m12, m13) + String.format(f, m20, m21, m22, m23)
    }
}

fun translate(v: Vector) = AffineTransform().translate(v)
fun scale(v: Vector) = AffineTransform().scale(v)
fun rotateX(a: Double) = AffineTransform().rotateX(a)
fun rotateY(a: Double) = AffineTransform().rotateY(a)
fun rotateZ(a: Double) = AffineTransform().rotateZ(a)
