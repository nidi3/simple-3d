package guru.nidi.simple3d.model

data class Color(val red: Double, val green: Double, val blue: Double)

data class Material internal constructor(
    val name: String,
    val diffuseColor: Color,
    val ambientColor: Color? = null,
    val transparency: Double? = null
)
