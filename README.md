# simple-3d
A simple CSG library to create models for 3D printing.
Originally, the code is based on [csg.js](https://github.com/evanw/csg.js/).

This is a kotlin multi platform project, so it can be used from both javascript and kotlin.
The javascript part is currently unpolished,
but for an example what can be done, [see here](https://nidi3.github.io/simple-3d/). 

To use it with kotlin, add it as a dependency:

```xml
<dependency>
    <groupId>guru.nidi.simple-3d</groupId>
    <artifactId>simple-3d-jvm</artifactId>
    <version>0.0.1</version>
</dependency>
```

Then, create a model an add objects to it:
```kotlin 
model(
    File("examples/simple.stl"),
    cube(length = v(1, 2, 3)).rotateX(45.deg),
    sphere(center = v(3, 0, 0))
)
```

<iframe src="https://www.viewstl.com/?embedded&url=https://github.com/nidi3/simple-3d/tree/master/examples/simple.stl&color=green" style="border:0;margin:0;width:500px;height:300px;"></iframe>

More examples can be found [here](https://github.com/nidi3/simple-3d/tree/master/src/jvmTest/kotlin/guru/nidi/simple3d/examples).
