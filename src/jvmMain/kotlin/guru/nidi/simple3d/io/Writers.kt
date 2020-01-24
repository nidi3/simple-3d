package guru.nidi.simple3d.io

import guru.nidi.simple3d.model.Model
import java.io.File

fun model(file: File, actions: Model.() -> Unit) {
    val model = Model().also(actions)
    val dot = file.name.lastIndexOf('.')
    if (dot < 0) throw IllegalArgumentException("Filename must have an extension to determine the file type")
    when (val format = file.name.substring(dot)) {
        ".stl" -> model.writeBinaryStl(file)
        ".obj" -> model.writeObj(file)
        ".ply" -> model.writePly(file)
        else -> throw IllegalArgumentException("Unknown file format $format.")
    }
}
