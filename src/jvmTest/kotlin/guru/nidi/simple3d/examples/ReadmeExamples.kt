package guru.nidi.simple3d.examples

import guru.nidi.simple3d.io.model
import guru.nidi.simple3d.model.*
import guru.nidi.simple3d.vectorize.Image
import guru.nidi.simple3d.vectorize.outline
import guru.nidi.simple3d.vectorize.simplify
import java.io.File

fun main() {
    model(
        File("examples/simple.stl"),
        cube(length = v(1, 2, 3)).rotateX(45.deg),
        sphere(center = v(3, 0, 0))
    )

    model(File("examples/csg.stl")) {
        val a = cube(center = v(1, 0, 0))
        val b = sphere()

        add(
            (a union b).translate(3, 0, 0),
            (a subtract b).translate(6, 0, 0),
            (a intersect b).translate(0, 0, 0)
        )
    }

    model(File("examples/transform.stl")) {
        val cross = cube(length = v(1, 1, 2)) + cube(length = v(1, 2, 1))
        fun f(level: Int) {
            if (level > 0) {
                add(cross)
                scale(.25, .25, .25).apply {
                    translate(0, 3, 3).apply { f(level - 1) }
                    translate(0, -3, 3).apply { f(level - 1) }
                    translate(0, 3, -3).apply { f(level - 1) }
                    translate(0, -3, -3).apply { f(level - 1) }
                }
            }
        }
        f(3)
    }

    model(File("examples/material.obj")) {
        val red = material("red", Color(1.0, 0.0, 0.0))
        val green = material("green", Color(0.0, 1.0, 0.0))
        add(cube().material(red) - cube(center = v(.5, .5, 0)).material(green))
    }

    model(File("examples/dinosaur.stl")) {
        val img = Image.fromClasspath("brontosaurus-pattern.gif")
        val c = outline(img) { rgb -> rgb < 0xffffff }
            .simplify(5.0)
            .map { it.toVector() / 10.0 }
        add(prism(10, c))
    }
}
