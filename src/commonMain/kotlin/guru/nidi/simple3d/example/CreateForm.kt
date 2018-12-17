package guru.nidi.simple3d.example

import guru.nidi.simple3d.model.*
import guru.nidi.simple3d.vectorize.*
import kotlin.js.JsName

@JsName("createForm")
fun createForm(
        sourceImage: Image, outlineImage: Image?, simpleImage: Image?,
        classifier: (Int) -> Boolean,
        simplify: Double,
        scale: Double,
        width: Double,
        height: Double
): Csg {
    val c = outline(sourceImage, false, classifier)
            .also { p ->
                if (outlineImage != null) p.indices.forEach { i ->
                    outlineImage[p(i).x, p(i).y] = 0x70ff0000
                }
            }
            .simplify(simplify)
            .also { p ->
                if (simpleImage != null)
                    p.indices.forEach { i ->
                        simpleImage.line(p(i).x, p(i).y, p(i + 1).x, p(i + 1).y, 0x70ff0000)
                    }
            }
            .map { it.toVector().scale(v(scale, scale, 1)) }
    return prismRing(width, height, true, c).translate(v(0, 0, height))
}