package guru.nidi.simple3d.examples

import guru.nidi.simple3d.io.model
import guru.nidi.simple3d.model.cube
import guru.nidi.simple3d.model.deg
import guru.nidi.simple3d.model.sphere
import guru.nidi.simple3d.model.v
import java.io.File

fun main() {
    model(
        File("examples/simple.stl"),
        cube(length = v(1, 2, 3)).rotateX(45.deg),
        sphere(center = v(3, 0, 0))
    )
}
