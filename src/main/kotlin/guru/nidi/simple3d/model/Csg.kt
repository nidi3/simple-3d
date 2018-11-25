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

interface PolygonSink {
    fun add(p: Polygon)
}

private class PolygonList(val list: MutableList<Polygon>) : PolygonSink {
    override fun add(p: Polygon) {
        list.add(p)
    }
}

class Csg(val polygons: List<Polygon>, n: Node?) {
    private val node: Node by lazy { n ?: Node(polygons) }

    constructor(polygons: List<Polygon>) : this(polygons, null)
    constructor(node: Node) : this(node.allPolygons(), node)

    companion object {
        fun ofPolygons(steps: (PolygonSink) -> Unit): Csg {
            val polys = PolygonList(mutableListOf())
            steps(polys)
            return Csg(polys.list)
        }
    }

    fun translate(v: Vector) = AffineTransform().translate(v).applyTo(this)
    fun scale(v: Vector) = AffineTransform().scale(v).applyTo(this)
    fun rotateX(a: Double) = AffineTransform().rotateX(a).applyTo(this)
    fun rotateY(a: Double) = AffineTransform().rotateY(a).applyTo(this)
    fun rotateZ(a: Double) = AffineTransform().rotateZ(a).applyTo(this)

    infix fun union(csg: Csg): Csg {
        val a = node.clipTo(csg.node)
        val b2 = -(-csg.node.clipTo(a)).clipTo(a)
        return Csg(a.combine(b2))
    }

    operator fun plus(csg: Csg) = this union csg
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

    infix fun and(csg: Csg) = this intersect csg

    infix fun xor(csg: Csg) = (this or csg) - (this and csg)

    operator fun unaryMinus() = Csg(polygons.map { -it })

    fun boundingBox(): Pair<Vector, Vector> {
        var minX = Double.MAX_VALUE
        var maxX = Double.MIN_VALUE
        var minY = Double.MAX_VALUE
        var maxY = Double.MIN_VALUE
        var minZ = Double.MAX_VALUE
        var maxZ = Double.MIN_VALUE
        for (p in polygons) {
            val b = p.boundingBox()
            if (b.first.x < minX) minX = b.first.x
            if (b.second.x > maxX) maxX = b.second.x
            if (b.first.y < minY) minY = b.first.y
            if (b.second.y > maxY) maxY = b.second.y
            if (b.first.z < minZ) minZ = b.first.z
            if (b.second.z > maxZ) maxZ = b.second.z
        }
        return Pair(Vector(minX, minY, minZ), Vector(maxX, maxY, maxZ))
    }

    fun size() = boundingBox().let { (it.second - it.first).abs() }

    fun growLinear(value: Double) = growLinear(Vector(value, value, value))

    fun growLinear(value: Vector): Csg {
        val b = boundingBox()
        val size = (b.second - b.first).abs()
        val dist = b.first + size / 2.0
        return AffineTransform().translate(dist).scale(unit + (value scaleInv size)).translate(-dist).applyTo(this)
    }
}
