package guru.nidi.simple3d.vectorize

import org.khronos.webgl.Uint32Array
import org.khronos.webgl.get
import org.khronos.webgl.set
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.ImageData

actual class Image(private val canvas: HTMLCanvasElement) {
    private val ctx = canvas.getContext("2d") as CanvasRenderingContext2D
    private var data: ImageData? = null
    private val px = ctx.createImageData(1.0, 1.0)
    fun flush() {
        data = ctx.getImageData(0.0, 0.0, canvas.width.toDouble(), canvas.height.toDouble())
    }

    actual val width get() = canvas.width
    actual val height get() = canvas.height
    actual operator fun get(x: Int, y: Int): Int {
        if (data == null) flush()
        val i = y * (width * 4) + x * 4
        return (raw(i + 3) shl 24) or (raw(i) shl 16) or (raw(i + 1) shl 8) or (raw(i + 3))
    }

    actual operator fun set(x: Int, y: Int, rgb: Int) {
        val d = px.data.unsafeCast<Uint32Array>()
        d[0] = (rgb shr 16) and 0xff
        d[1] = (rgb shr 8) and 0xff
        d[2] = (rgb shr 0) and 0xff
        d[3] = (rgb shr 24) and 0xff
        ctx.putImageData(px, x.toDouble(), y.toDouble())
    }

    actual fun line(x0: Int, y0: Int, x1: Int, y1: Int, rgb: Int) {
        ctx.strokeStyle =
            "rgba(${(rgb shr 16) and 0xff},${(rgb shr 8) and 0xff},${(rgb shr 0) and 0xff},${((rgb shr 24) and 0xff) / 255.0})"
        ctx.moveTo(x0.toDouble(), y0.toDouble())
        ctx.lineTo(x1.toDouble(), y1.toDouble())
        ctx.stroke()
    }

    private fun raw(i: Int) = data!!.data[i].toInt()
}
