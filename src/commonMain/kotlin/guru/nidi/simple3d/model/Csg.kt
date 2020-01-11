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

interface PolygonSink {
    fun add(p: Polygon)
    fun addAll(ps: List<Polygon>)
}

private class PolygonList(val list: MutableList<Polygon>) : PolygonSink {
    override fun add(p: Polygon) {
        list.add(p)
    }

    override fun addAll(ps: List<Polygon>) {
        list.addAll(ps)
    }
}

class Csg internal constructor(val polygons: List<Polygon>, n: Node?) {
    private constructor(node: Node) : this(node.allPolygons(), node)
    constructor(polygons: List<Polygon>) : this(polygons, null)
    constructor(steps: PolygonSink.() -> Unit) : this(PolygonList(mutableListOf()).also { steps(it) }.list, null)

    private val node: Node by lazy { n ?: Node(polygons) }

    @JsName("translate")
    fun translate(v: Vector) = AffineTransform().translate(v).applyTo(this)

    fun translate(x: Number, y: Number, z: Number) = translate(Vector(x.toDouble(), y.toDouble(), z.toDouble()))

    @JsName("scale")
    fun scale(v: Vector) = AffineTransform().scale(v).applyTo(this)

    fun scale(x: Number, y: Number, z: Number) = scale(Vector(x.toDouble(), y.toDouble(), z.toDouble()))

    fun rotateX(a: Double) = AffineTransform().rotateX(a).applyTo(this)
    fun rotateY(a: Double) = AffineTransform().rotateY(a).applyTo(this)
    fun rotateZ(a: Double) = AffineTransform().rotateZ(a).applyTo(this)

    infix fun union(csg: Csg): Csg {
        val a = node.clipTo(csg.node)
        val b2 = -(-csg.node.clipTo(a)).clipTo(a)
        return Csg(a.combine(b2))
    }

    operator fun plus(csg: Csg) = this union csg
    @JsName("or")
    infix fun or(csg: Csg) = this union csg

    infix fun subtract(csg: Csg): Csg {
        val a = (-node).clipTo(csg.node)
        val b1 = -(-csg.node.clipTo(a)).clipTo(a)
        return Csg(-a.combine(b1))
    }

    operator fun minus(csg: Csg) = this subtract csg

    infix fun intersect(csg: Csg): Csg {
        val a = -node
        val b = -csg.node.clipTo(a)
        val a1 = a.clipTo(b)
        val b2 = b.clipTo(a1)
        return Csg(-a1.combine(b2))
    }

    operator fun times(csg: Csg) = this intersect csg

    @JsName("and")
    infix fun and(csg: Csg) = this intersect csg

    @JsName("xor")
    infix fun xor(csg: Csg) = (this or csg) - (this and csg)

    operator fun unaryMinus() = Csg(polygons.map { -it }, null)

    val boundingBox: Box by lazy {
        var minX = Double.MAX_VALUE
        var maxX = Double.MIN_VALUE
        var minY = Double.MAX_VALUE
        var maxY = Double.MIN_VALUE
        var minZ = Double.MAX_VALUE
        var maxZ = Double.MIN_VALUE
        for (p in polygons) {
            val b = p.boundingBox
            if (b.from.x < minX) minX = b.from.x
            if (b.to.x > maxX) maxX = b.to.x
            if (b.from.y < minY) minY = b.from.y
            if (b.to.y > maxY) maxY = b.to.y
            if (b.from.z < minZ) minZ = b.from.z
            if (b.to.z > maxZ) maxZ = b.to.z
        }
        Box(Vector(minX, minY, minZ), Vector(maxX, maxY, maxZ))
    }

    fun size() = boundingBox.size()

    fun growLinear(value: Double) = growLinear(Vector(value, value, value))

    fun growLinear(value: Vector): Csg {
        val b = boundingBox
        val dist = b.from + b.size() / 2.0
        return AffineTransform().translate(dist).scale(unit + (value scaleInv b.size())).translate(-dist).applyTo(this)
    }
}

data class Box(val from: Vector, val to: Vector) {
    fun size() = to - from

    infix fun intersect(b: Box): Boolean =
        to.x >= b.from.x &&
                from.x <= b.to.x &&
                to.y >= b.from.y &&
                from.y <= b.to.y
}
