package guru.nidi.simple3d.vectorize

import java.awt.Color
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

actual class Image(val img: BufferedImage) : AutoCloseable {
    private val lazyGraph = lazy { img.createGraphics() }
    private val graph by lazyGraph
    override fun close() {
        if (lazyGraph.isInitialized()) graph.dispose()
    }

    actual val width get() = img.width
    actual val height get() = img.height
    actual operator fun get(x: Int, y: Int) = img.getRGB(x, y)
    actual operator fun set(x: Int, y: Int, rgb: Int) = img.setRGB(x, y, rgb)
    actual fun line(x0: Int, y0: Int, x1: Int, y1: Int, rgb: Int) {
        graph.color = Color(rgb, true)
        graph.drawLine(x0, y0, x1, y1)
    }

    companion object {
        fun fromClasspath(name: String) =
            Image(ImageIO.read(Thread.currentThread().contextClassLoader.getResourceAsStream(name)))
    }
}