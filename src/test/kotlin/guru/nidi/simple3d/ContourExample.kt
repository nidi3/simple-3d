package guru.nidi.simple3d

import guru.nidi.simple3d.model.model
import guru.nidi.simple3d.model.prism
import guru.nidi.simple3d.vectorize.contour
import guru.nidi.simple3d.vectorize.simplify
import java.io.File
import javax.imageio.ImageIO

fun main() {
    model {
        val img = ImageIO.read(Thread.currentThread().contextClassLoader.getResourceAsStream("brontosaurus-pattern.gif"))
        val c = contour(img) { rgb ->
            rgb < 0xffffff
        }
        val d = simplify(c, 5.0)
        add(prism(10.0, true, d.map { it.toVector() / 10.0 }))
        write(File("target/dino.stl"), "dino")
    }
}