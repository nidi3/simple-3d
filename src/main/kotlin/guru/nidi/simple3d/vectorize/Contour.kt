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
package guru.nidi.simple3d.vectorize

import guru.nidi.simple3d.model.Vector
import java.awt.image.BufferedImage
import java.lang.Integer.signum

//TODO only one contour supported, endless loop is first point has no neighbors
fun contour(image: BufferedImage, classifier: (Int) -> Boolean): List<Point> {
    fun isBlack(x: Int, y: Int) = classifier(image.getRGB(x, y))
    fun isBlack(p: Point) = isBlack(p.x, p.y)

    val edges = mutableListOf<Point>()
    fun add(p: Point) {
        if (edges.size >= 2 && p.isOneLine(edges[edges.size - 1], edges[edges.size - 2])) edges[edges.size - 1] = p
        else edges += p
    }

    val start = (DirPoint.findStart(image.width, image.height, ::isBlack)
            ?: throw IllegalArgumentException("No black pixel found.")).findNext(::isBlack)
    var point = start
    do {
        add(point.pos())
        point = point.findNext(::isBlack)
    } while (point != start)
    return edges
}

data class Point(val x: Int, val y: Int) {
    fun isOneLine(p1: Point, p2: Point) = signum(p1.x - this.x) == signum(p2.x - p1.x)
            && signum(p1.y - this.y) == signum(p2.y - p1.y)

    fun toVector() = Vector(x.toDouble(), y.toDouble(), 0.0)
}

data class DirPoint(val dx: Int, val dy: Int, val x: Int, val y: Int) {
    companion object {
        fun findStart(width: Int, height: Int, isBlack: (Int, Int) -> Boolean): DirPoint? {
            for (x in 0 until width) {
                for (y in 0 until height) {
                    if (isBlack(x, y)) return DirPoint(-1, 0, x, y)
                }
            }
            return null
        }
    }

    fun pos() = Point(x, y)

    fun findNext(isBlack: (Point) -> Boolean): DirPoint {
        var dp = this
        while (!isBlack(dp.target())) dp = dp.rot()
        return dp.next()
    }

    private fun rot() = DirPoint(
            if (dx + dy > 0) 1 else if (dx + dy < 0) -1 else 0,
            if (dx - dy > 0) -1 else if (dx - dy < 0) 1 else 0,
            x, y)

    private fun target() = Point(x + dx, y + dy)

    fun next() = DirPoint(
            if (dx != -1 && dy == 1) -1 else if (dx != 1 && dy == -1) 1 else 0,
            if (dx == -1 && dy != -1) -1 else if (dx == 1 && dy != 1) 1 else 0,
            x + dx, y + dy)
}