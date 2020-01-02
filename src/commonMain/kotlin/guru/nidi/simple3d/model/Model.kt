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
package guru.nidi.simple3d.model

import kotlin.js.JsName
import kotlin.math.PI

class Model {
    internal val csgs = mutableListOf<Csg>()
    private var transform = AffineTransform()

    fun <T> transform(a: AffineTransform, block: Model.() -> T): T {
        val orig = transform
        try {
            transform = transform.applyTo(a)
            return block()
        } finally {
            transform = orig
        }
    }

    @JsName("add")
    fun add(csg: Csg) = csgs.add(transform.applyTo(csg))
}

@JsName("model")
fun model(actions: Model.() -> Unit) = Model().apply(actions)

val Int.deg get() = this * PI / 180
val Double.deg get() = this * PI / 180
val Double.rad get() = this

operator fun <T> List<T>.invoke(i: Int) = get(((i % size) + size) % size)
