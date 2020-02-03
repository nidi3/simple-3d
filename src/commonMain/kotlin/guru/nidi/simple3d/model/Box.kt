package guru.nidi.simple3d.model

data class Box(val from: Vector, val to: Vector) {
    fun size() = to - from

    infix fun intersect(b: Box): Boolean =
        to.x >= b.from.x && from.x <= b.to.x &&
                to.y >= b.from.y && from.y <= b.to.y &&
                to.z >= b.from.z && from.z <= b.to.z

    operator fun contains(v: Vector): Boolean =
        v.x >= from.x && v.x <= to.x &&
                v.y >= from.y && v.y <= to.y &&
                v.z >= from.z && v.z <= to.z
}
