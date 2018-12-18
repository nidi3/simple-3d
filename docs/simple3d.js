if (typeof kotlin === 'undefined') {
  throw new Error("Error loading module 'simple3d'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'simple3d'.");
}
var simple3d = function (_, Kotlin) {
  'use strict';
  var get_indices = Kotlin.kotlin.collections.get_indices_gzk92b$;
  var Unit = Kotlin.kotlin.Unit;
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var Kind_INTERFACE = Kotlin.Kind.INTERFACE;
  var Kind_OBJECT = Kotlin.Kind.OBJECT;
  var kotlin_js_internal_DoubleCompanionObject = Kotlin.kotlin.js.internal.DoubleCompanionObject;
  var lazy = Kotlin.kotlin.lazy_klfg04$;
  var math = Kotlin.kotlin.math;
  var toMutableList = Kotlin.kotlin.collections.toMutableList_4c7yge$;
  var ensureNotNull = Kotlin.ensureNotNull;
  var plus = Kotlin.kotlin.collections.plus_mydzjv$;
  var listOf = Kotlin.kotlin.collections.listOf_i5x0yv$;
  var toList = Kotlin.kotlin.collections.toList_us0mfu$;
  var IllegalArgumentException_init = Kotlin.kotlin.IllegalArgumentException_init_pdl1vj$;
  var reversed = Kotlin.kotlin.collections.reversed_7wnvza$;
  var Pair = Kotlin.kotlin.Pair;
  var Enum = Kotlin.kotlin.Enum;
  var throwISE = Kotlin.throwISE;
  var asList = Kotlin.kotlin.collections.asList_us0mfu$;
  var get_lastIndex = Kotlin.kotlin.collections.get_lastIndex_55thoc$;
  var numberToInt = Kotlin.numberToInt;
  var IllegalArgumentException = Kotlin.kotlin.IllegalArgumentException;
  var getCallableRef = Kotlin.getCallableRef;
  var get_sign = Kotlin.kotlin.math.get_sign_s8ev3n$;
  var abs = Kotlin.kotlin.math.abs_za3lpa$;
  var first = Kotlin.kotlin.collections.first_2p1efm$;
  var last = Kotlin.kotlin.collections.last_2p1efm$;
  var throwCCE = Kotlin.throwCCE;
  Plane$Type.prototype = Object.create(Enum.prototype);
  Plane$Type.prototype.constructor = Plane$Type;
  var collectionSizeOrDefault = Kotlin.kotlin.collections.collectionSizeOrDefault_ba2ldo$;
  var ArrayList_init = Kotlin.kotlin.collections.ArrayList_init_ww73n8$;
  function createForm(sourceImage, outlineImage, simpleImage, classifier, simplify_0, scale, width, height) {
    var $receiver = outline(sourceImage, false, classifier);
    if (outlineImage != null) {
      var tmp$;
      tmp$ = get_indices($receiver).iterator();
      while (tmp$.hasNext()) {
        var element = tmp$.next();
        outlineImage.set_qt1dr2$(invoke($receiver, element).x, invoke($receiver, element).y, 1895759872);
      }
    }
    var $receiver_0 = simplify($receiver, simplify_0);
    if (simpleImage != null) {
      var tmp$_0;
      tmp$_0 = get_indices($receiver_0).iterator();
      while (tmp$_0.hasNext()) {
        var element_0 = tmp$_0.next();
        simpleImage.line_4qozqa$(invoke($receiver_0, element_0).x, invoke($receiver_0, element_0).y, invoke($receiver_0, element_0 + 1 | 0).x, invoke($receiver_0, element_0 + 1 | 0).y, 1895759872);
      }
    }
    var destination = ArrayList_init(collectionSizeOrDefault($receiver_0, 10));
    var tmp$_1;
    tmp$_1 = $receiver_0.iterator();
    while (tmp$_1.hasNext()) {
      var item = tmp$_1.next();
      destination.add_11rb$(item.toVector().scale_wk194y$(v_2(scale, scale, 1)));
    }
    var c = destination;
    return prismRing_0(width, height, true, c).translate(v_3(0, 0, height));
  }
  function AffineTransform(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23) {
    this.m00 = m00;
    this.m01 = m01;
    this.m02 = m02;
    this.m03 = m03;
    this.m10 = m10;
    this.m11 = m11;
    this.m12 = m12;
    this.m13 = m13;
    this.m20 = m20;
    this.m21 = m21;
    this.m22 = m22;
    this.m23 = m23;
  }
  AffineTransform.prototype.transpose = function () {
    return new AffineTransform(this.m00, this.m10, this.m20, 0.0, this.m01, this.m11, this.m21, 0.0, this.m02, this.m12, this.m22, 0.0);
  };
  AffineTransform.prototype.determinant = function () {
    return this.m00 * (this.m11 * this.m22 - this.m12 * this.m21) - this.m10 * (this.m01 * this.m22 - this.m02 * this.m21) + this.m20 * (this.m01 * this.m12 - this.m02 * this.m11);
  };
  AffineTransform.prototype.adjugate = function () {
    return new AffineTransform(this.m11 * this.m22 - this.m12 * this.m21, -this.m01 * this.m22 + this.m02 * this.m21, this.m01 * this.m12 - this.m02 * this.m11, -this.m01 * this.m12 * this.m23 - this.m02 * this.m13 * this.m21 - this.m03 * this.m11 * this.m22 + this.m03 * this.m12 * this.m21 + this.m02 * this.m11 * this.m23 + this.m01 * this.m13 * this.m22, -this.m10 * this.m22 + this.m12 * this.m20, this.m00 * this.m22 - this.m02 * this.m20, -this.m00 * this.m12 + this.m02 * this.m10, this.m00 * this.m12 * this.m23 + this.m02 * this.m13 * this.m20 + this.m03 * this.m10 * this.m22 - this.m03 * this.m12 * this.m20 - this.m02 * this.m10 * this.m23 - this.m00 * this.m13 * this.m22, this.m10 * this.m21 - this.m11 * this.m20, -this.m00 * this.m21 + this.m01 * this.m20, this.m00 * this.m11 - this.m01 * this.m10, -this.m00 * this.m11 * this.m23 - this.m01 * this.m13 * this.m20 - this.m03 * this.m10 * this.m21 + this.m03 * this.m11 * this.m20 + this.m01 * this.m10 * this.m23 + this.m00 * this.m13 * this.m21);
  };
  AffineTransform.prototype.inverse = function () {
    return this.times_kd4jdt$(1.0 / this.determinant(), this.adjugate());
  };
  AffineTransform.prototype.times_kd4jdt$ = function ($receiver, a) {
    return new AffineTransform($receiver * a.m00, $receiver * a.m01, $receiver * a.m02, $receiver * a.m03, $receiver * a.m10, $receiver * a.m11, $receiver * a.m12, $receiver * a.m13, $receiver * a.m20, $receiver * a.m21, $receiver * a.m22, $receiver * a.m23);
  };
  AffineTransform.prototype.translate_wk194y$ = function (v) {
    return new AffineTransform(this.m00, this.m01, this.m02, this.m03 + this.m00 * v.x + this.m01 * v.y + this.m02 * v.z, this.m10, this.m11, this.m12, this.m13 + this.m10 * v.x + this.m11 * v.y + this.m12 * v.z, this.m20, this.m21, this.m22, this.m23 + this.m20 * v.x + this.m21 * v.y + this.m22 * v.z);
  };
  AffineTransform.prototype.scale_wk194y$ = function (v) {
    return new AffineTransform(this.m00 * v.x, this.m01 * v.y, this.m02 * v.z, this.m03, this.m10 * v.x, this.m11 * v.y, this.m12 * v.z, this.m13, this.m20 * v.x, this.m21 * v.y, this.m22 * v.z, this.m23);
  };
  var Math_0 = Math;
  AffineTransform.prototype.rotateX_14dthe$ = function (a) {
    return new AffineTransform(this.m00, this.m01 * Math_0.cos(a) + this.m02 * Math_0.sin(a), -this.m01 * Math_0.sin(a) + this.m02 * Math_0.cos(a), this.m03, this.m10, this.m11 * Math_0.cos(a) + this.m12 * Math_0.sin(a), -this.m11 * Math_0.sin(a) + this.m12 * Math_0.cos(a), this.m13, this.m20, this.m21 * Math_0.cos(a) + this.m22 * Math_0.sin(a), -this.m21 * Math_0.sin(a) + this.m22 * Math_0.cos(a), this.m23);
  };
  AffineTransform.prototype.rotateY_14dthe$ = function (a) {
    return new AffineTransform(this.m00 * Math_0.cos(a) + this.m02 * Math_0.sin(a), this.m01, -this.m00 * Math_0.sin(a) + this.m02 * Math_0.cos(a), this.m03, this.m10 * Math_0.cos(a) + this.m12 * Math_0.sin(a), this.m11, -this.m10 * Math_0.sin(a) + this.m12 * Math_0.cos(a), this.m13, this.m20 * Math_0.cos(a) + this.m22 * Math_0.sin(a), this.m21, -this.m20 * Math_0.sin(a) + this.m22 * Math_0.cos(a), this.m23);
  };
  AffineTransform.prototype.rotateZ_14dthe$ = function (a) {
    return new AffineTransform(this.m00 * Math_0.cos(a) - this.m01 * Math_0.sin(a), this.m00 * Math_0.sin(a) + this.m01 * Math_0.cos(a), this.m02, this.m03, this.m10 * Math_0.cos(a) - this.m11 * Math_0.sin(a), this.m10 * Math_0.sin(a) + this.m11 * Math_0.cos(a), this.m12, this.m13, this.m20 * Math_0.cos(a) - this.m21 * Math_0.sin(a), this.m20 * Math_0.sin(a) + this.m21 * Math_0.cos(a), this.m22, this.m23);
  };
  AffineTransform.prototype.applyTo_qhtjma$ = function (a) {
    return new AffineTransform(this.m00 * a.m00 + this.m01 * a.m10 + this.m02 * a.m20, this.m00 * a.m01 + this.m01 * a.m11 + this.m02 * a.m21, this.m00 * a.m02 + this.m01 * a.m12 + this.m02 * a.m22, this.m00 * a.m03 + this.m01 * a.m13 + this.m02 * a.m23 + this.m03, this.m10 * a.m00 + this.m11 * a.m10 + this.m12 * a.m20, this.m10 * a.m01 + this.m11 * a.m11 + this.m12 * a.m21, this.m10 * a.m02 + this.m11 * a.m12 + this.m12 * a.m22, this.m10 * a.m03 + this.m11 * a.m13 + this.m12 * a.m23 + this.m13, this.m20 * a.m00 + this.m21 * a.m10 + this.m22 * a.m20, this.m20 * a.m01 + this.m21 * a.m11 + this.m22 * a.m21, this.m20 * a.m02 + this.m21 * a.m12 + this.m22 * a.m22, this.m20 * a.m03 + this.m21 * a.m13 + this.m22 * a.m23 + this.m23);
  };
  AffineTransform.prototype.applyTo_wk194y$ = function (p) {
    return new Vector(this.m00 * p.x + this.m01 * p.y + this.m02 * p.z + this.m03, this.m10 * p.x + this.m11 * p.y + this.m12 * p.z + this.m13, this.m20 * p.x + this.m21 * p.y + this.m22 * p.z + this.m23);
  };
  AffineTransform.prototype.applyTo_wkatpf$ = function (t) {
    return new Vertex(this.applyTo_wk194y$(t.pos), this.inverse().transpose().applyTo_wk194y$(t.normal));
  };
  AffineTransform.prototype.applyTo_2npujf$ = function (t) {
    var $receiver = t.vertices;
    var destination = ArrayList_init(collectionSizeOrDefault($receiver, 10));
    var tmp$;
    tmp$ = $receiver.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      destination.add_11rb$(this.applyTo_wkatpf$(item));
    }
    return new Polygon(destination);
  };
  AffineTransform.prototype.applyTo_ehrgfc$ = function (t) {
    var $receiver = t.polygons;
    var destination = ArrayList_init(collectionSizeOrDefault($receiver, 10));
    var tmp$;
    tmp$ = $receiver.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      destination.add_11rb$(this.applyTo_2npujf$(item));
    }
    return Csg_init(destination);
  };
  AffineTransform.prototype.toString = function () {
    return this.m00.toString() + ' ' + this.m01 + ' ' + this.m02 + ' ' + this.m03 + '\n' + this.m10 + ' ' + this.m11 + ' ' + this.m12 + ' ' + this.m13 + '\n' + this.m20 + ' ' + this.m21 + ' ' + this.m22 + ' ' + this.m23;
  };
  AffineTransform.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'AffineTransform',
    interfaces: []
  };
  function AffineTransform_init($this) {
    $this = $this || Object.create(AffineTransform.prototype);
    AffineTransform.call($this, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
    return $this;
  }
  AffineTransform.prototype.component1 = function () {
    return this.m00;
  };
  AffineTransform.prototype.component2 = function () {
    return this.m01;
  };
  AffineTransform.prototype.component3 = function () {
    return this.m02;
  };
  AffineTransform.prototype.component4 = function () {
    return this.m03;
  };
  AffineTransform.prototype.component5 = function () {
    return this.m10;
  };
  AffineTransform.prototype.component6 = function () {
    return this.m11;
  };
  AffineTransform.prototype.component7 = function () {
    return this.m12;
  };
  AffineTransform.prototype.component8 = function () {
    return this.m13;
  };
  AffineTransform.prototype.component9 = function () {
    return this.m20;
  };
  AffineTransform.prototype.component10 = function () {
    return this.m21;
  };
  AffineTransform.prototype.component11 = function () {
    return this.m22;
  };
  AffineTransform.prototype.component12 = function () {
    return this.m23;
  };
  AffineTransform.prototype.copy_wtsvi4$ = function (m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23) {
    return new AffineTransform(m00 === void 0 ? this.m00 : m00, m01 === void 0 ? this.m01 : m01, m02 === void 0 ? this.m02 : m02, m03 === void 0 ? this.m03 : m03, m10 === void 0 ? this.m10 : m10, m11 === void 0 ? this.m11 : m11, m12 === void 0 ? this.m12 : m12, m13 === void 0 ? this.m13 : m13, m20 === void 0 ? this.m20 : m20, m21 === void 0 ? this.m21 : m21, m22 === void 0 ? this.m22 : m22, m23 === void 0 ? this.m23 : m23);
  };
  AffineTransform.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.m00) | 0;
    result = result * 31 + Kotlin.hashCode(this.m01) | 0;
    result = result * 31 + Kotlin.hashCode(this.m02) | 0;
    result = result * 31 + Kotlin.hashCode(this.m03) | 0;
    result = result * 31 + Kotlin.hashCode(this.m10) | 0;
    result = result * 31 + Kotlin.hashCode(this.m11) | 0;
    result = result * 31 + Kotlin.hashCode(this.m12) | 0;
    result = result * 31 + Kotlin.hashCode(this.m13) | 0;
    result = result * 31 + Kotlin.hashCode(this.m20) | 0;
    result = result * 31 + Kotlin.hashCode(this.m21) | 0;
    result = result * 31 + Kotlin.hashCode(this.m22) | 0;
    result = result * 31 + Kotlin.hashCode(this.m23) | 0;
    return result;
  };
  AffineTransform.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.m00, other.m00) && Kotlin.equals(this.m01, other.m01) && Kotlin.equals(this.m02, other.m02) && Kotlin.equals(this.m03, other.m03) && Kotlin.equals(this.m10, other.m10) && Kotlin.equals(this.m11, other.m11) && Kotlin.equals(this.m12, other.m12) && Kotlin.equals(this.m13, other.m13) && Kotlin.equals(this.m20, other.m20) && Kotlin.equals(this.m21, other.m21) && Kotlin.equals(this.m22, other.m22) && Kotlin.equals(this.m23, other.m23)))));
  };
  function translate(v) {
    return AffineTransform_init().translate_wk194y$(v);
  }
  function scale(v) {
    return AffineTransform_init().scale_wk194y$(v);
  }
  function rotateX(a) {
    return AffineTransform_init().rotateX_14dthe$(a);
  }
  function rotateY(a) {
    return AffineTransform_init().rotateY_14dthe$(a);
  }
  function rotateZ(a) {
    return AffineTransform_init().rotateZ_14dthe$(a);
  }
  function PolygonSink() {
  }
  PolygonSink.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'PolygonSink',
    interfaces: []
  };
  function PolygonList(list) {
    this.list = list;
  }
  PolygonList.prototype.add_2npujf$ = function (p) {
    this.list.add_11rb$(p);
  };
  PolygonList.prototype.addAll_1k44c0$ = function (ps) {
    this.list.addAll_brywnq$(ps);
  };
  PolygonList.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'PolygonList',
    interfaces: [PolygonSink]
  };
  function Csg(polygons, n) {
    Csg$Companion_getInstance();
    this.polygons = polygons;
    this.node_x2ajmm$_0 = lazy(Csg$node$lambda(n, this));
  }
  Object.defineProperty(Csg.prototype, 'node_0', {
    get: function () {
      return this.node_x2ajmm$_0.value;
    }
  });
  function Csg$Companion() {
    Csg$Companion_instance = this;
  }
  var ArrayList_init_0 = Kotlin.kotlin.collections.ArrayList_init_287e2$;
  Csg$Companion.prototype.ofPolygons_w9xryh$ = function (steps) {
    var polys = new PolygonList(ArrayList_init_0());
    steps(polys);
    return Csg_init(polys.list);
  };
  Csg$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var Csg$Companion_instance = null;
  function Csg$Companion_getInstance() {
    if (Csg$Companion_instance === null) {
      new Csg$Companion();
    }
    return Csg$Companion_instance;
  }
  Csg.prototype.translate = function (v) {
    return AffineTransform_init().translate_wk194y$(v).applyTo_ehrgfc$(this);
  };
  Csg.prototype.scale = function (v) {
    return AffineTransform_init().scale_wk194y$(v).applyTo_ehrgfc$(this);
  };
  Csg.prototype.rotateX_14dthe$ = function (a) {
    return AffineTransform_init().rotateX_14dthe$(a).applyTo_ehrgfc$(this);
  };
  Csg.prototype.rotateY_14dthe$ = function (a) {
    return AffineTransform_init().rotateY_14dthe$(a).applyTo_ehrgfc$(this);
  };
  Csg.prototype.rotateZ_14dthe$ = function (a) {
    return AffineTransform_init().rotateZ_14dthe$(a).applyTo_ehrgfc$(this);
  };
  Csg.prototype.union_ehrgfc$ = function (csg) {
    var a = this.node_0.clipTo_n3rvgf$(csg.node_0);
    var b2 = csg.node_0.clipTo_n3rvgf$(a).unaryMinus().clipTo_n3rvgf$(a).unaryMinus();
    return Csg_init_0(a.combine_n3rvgf$(b2));
  };
  Csg.prototype.plus_ehrgfc$ = function (csg) {
    return this.union_ehrgfc$(csg);
  };
  Csg.prototype.or = function (csg) {
    return this.union_ehrgfc$(csg);
  };
  Csg.prototype.subtract_ehrgfc$ = function (csg) {
    var a = this.node_0.unaryMinus().clipTo_n3rvgf$(csg.node_0);
    var b1 = csg.node_0.clipTo_n3rvgf$(a).unaryMinus().clipTo_n3rvgf$(a).unaryMinus();
    return Csg_init_0(a.combine_n3rvgf$(b1).unaryMinus());
  };
  Csg.prototype.minus_ehrgfc$ = function (csg) {
    return this.subtract_ehrgfc$(csg);
  };
  Csg.prototype.intersect_ehrgfc$ = function (csg) {
    var a = this.node_0.unaryMinus();
    var b = csg.node_0.clipTo_n3rvgf$(a).unaryMinus();
    var a1 = a.clipTo_n3rvgf$(b);
    var b2 = b.clipTo_n3rvgf$(a1);
    return Csg_init_0(a1.combine_n3rvgf$(b2).unaryMinus());
  };
  Csg.prototype.times_ehrgfc$ = function (csg) {
    return this.intersect_ehrgfc$(csg);
  };
  Csg.prototype.and = function (csg) {
    return this.intersect_ehrgfc$(csg);
  };
  Csg.prototype.xor = function (csg) {
    return this.or(csg).minus_ehrgfc$(this.and(csg));
  };
  Csg.prototype.unaryMinus = function () {
    var $receiver = this.polygons;
    var destination = ArrayList_init(collectionSizeOrDefault($receiver, 10));
    var tmp$;
    tmp$ = $receiver.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      destination.add_11rb$(item.unaryMinus());
    }
    return Csg_init(destination);
  };
  Csg.prototype.boundingBox = function () {
    var tmp$;
    var minX = kotlin_js_internal_DoubleCompanionObject.MAX_VALUE;
    var maxX = kotlin_js_internal_DoubleCompanionObject.MIN_VALUE;
    var minY = kotlin_js_internal_DoubleCompanionObject.MAX_VALUE;
    var maxY = kotlin_js_internal_DoubleCompanionObject.MIN_VALUE;
    var minZ = kotlin_js_internal_DoubleCompanionObject.MAX_VALUE;
    var maxZ = kotlin_js_internal_DoubleCompanionObject.MIN_VALUE;
    tmp$ = this.polygons.iterator();
    while (tmp$.hasNext()) {
      var p = tmp$.next();
      var b = p.boundingBox();
      if (b.first.x < minX)
        minX = b.first.x;
      if (b.second.x > maxX)
        maxX = b.second.x;
      if (b.first.y < minY)
        minY = b.first.y;
      if (b.second.y > maxY)
        maxY = b.second.y;
      if (b.first.z < minZ)
        minZ = b.first.z;
      if (b.second.z > maxZ)
        maxZ = b.second.z;
    }
    return new Box(new Vector(minX, minY, minZ), new Vector(maxX, maxY, maxZ));
  };
  Csg.prototype.size = function () {
    return this.boundingBox().size();
  };
  Csg.prototype.growLinear_14dthe$ = function (value) {
    return this.growLinear_wk194y$(new Vector(value, value, value));
  };
  Csg.prototype.growLinear_wk194y$ = function (value) {
    var b = this.boundingBox();
    var dist = b.from.plus_wk194y$(b.size().div_14dthe$(2.0));
    return AffineTransform_init().translate_wk194y$(dist).scale_wk194y$(unit.plus_wk194y$(value.scaleInv_wk194y$(b.size()))).translate_wk194y$(dist.unaryMinus()).applyTo_ehrgfc$(this);
  };
  function Csg$node$lambda(closure$n, this$Csg) {
    return function () {
      return closure$n != null ? closure$n : Node_init_0(this$Csg.polygons);
    };
  }
  Csg.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Csg',
    interfaces: []
  };
  function Csg_init(polygons, $this) {
    $this = $this || Object.create(Csg.prototype);
    Csg.call($this, polygons, null);
    return $this;
  }
  function Csg_init_0(node, $this) {
    $this = $this || Object.create(Csg.prototype);
    Csg.call($this, node.allPolygons(), node);
    return $this;
  }
  function Box(from, to) {
    this.from = from;
    this.to = to;
  }
  Box.prototype.size = function () {
    return this.to.minus_wk194y$(this.from);
  };
  Box.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Box',
    interfaces: []
  };
  Box.prototype.component1 = function () {
    return this.from;
  };
  Box.prototype.component2 = function () {
    return this.to;
  };
  Box.prototype.copy_pcjqg8$ = function (from, to) {
    return new Box(from === void 0 ? this.from : from, to === void 0 ? this.to : to);
  };
  Box.prototype.toString = function () {
    return 'Box(from=' + Kotlin.toString(this.from) + (', to=' + Kotlin.toString(this.to)) + ')';
  };
  Box.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.from) | 0;
    result = result * 31 + Kotlin.hashCode(this.to) | 0;
    return result;
  };
  Box.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.from, other.from) && Kotlin.equals(this.to, other.to)))));
  };
  function Model() {
    this.csgs_8be2vx$ = ArrayList_init_0();
    this.transform_0 = AffineTransform_init();
  }
  Model.prototype.transform_egy62$ = function (a, block) {
    var orig = this.transform_0;
    try {
      this.transform_0 = this.transform_0.applyTo_qhtjma$(a);
      return block(this);
    }
    finally {
      this.transform_0 = orig;
    }
  };
  Model.prototype.add = function (csg) {
    return this.csgs_8be2vx$.add_11rb$(this.transform_0.applyTo_ehrgfc$(csg));
  };
  Model.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Model',
    interfaces: []
  };
  function model(actions) {
    var $receiver = new Model();
    actions($receiver);
    return $receiver;
  }
  function deg($receiver) {
    return $receiver * math.PI / 180;
  }
  function deg_0($receiver) {
    return $receiver * math.PI / 180;
  }
  function rad($receiver) {
    return $receiver;
  }
  function invoke($receiver, i) {
    return $receiver.get_za3lpa$((i % $receiver.size + $receiver.size | 0) % $receiver.size);
  }
  function Node(polygons, plane, front, back) {
    this.polygons_0 = polygons;
    this.plane_0 = plane;
    this.front_0 = front;
    this.back_0 = back;
  }
  Node.prototype.copy = function () {
    var tmp$, tmp$_0, tmp$_1;
    return new Node(toMutableList(this.polygons_0), (tmp$ = this.plane_0) != null ? tmp$.copy_1dsxm4$() : null, (tmp$_0 = this.front_0) != null ? tmp$_0.copy() : null, (tmp$_1 = this.back_0) != null ? tmp$_1.copy() : null);
  };
  Node.prototype.unaryMinus = function () {
    var tmp$, tmp$_0, tmp$_1;
    var tmp$_2 = this.polygons_0;
    var destination = ArrayList_init_0();
    var tmp$_3;
    tmp$_3 = tmp$_2.iterator();
    while (tmp$_3.hasNext()) {
      var item = tmp$_3.next();
      destination.add_11rb$(item.unaryMinus());
    }
    return new Node(destination, (tmp$ = this.plane_0) != null ? tmp$.unaryMinus() : null, (tmp$_0 = this.back_0) != null ? tmp$_0.unaryMinus() : null, (tmp$_1 = this.front_0) != null ? tmp$_1.unaryMinus() : null);
  };
  Node.prototype.clipPolygons_0 = function (polygons) {
    var tmp$, tmp$_0;
    if (this.plane_0 == null)
      return toMutableList(polygons);
    var f = {v: ArrayList_init_0()};
    var b = {v: ArrayList_init_0()};
    var tmp$_1;
    tmp$_1 = polygons.iterator();
    while (tmp$_1.hasNext()) {
      var element = tmp$_1.next();
      ensureNotNull(this.plane_0).splitPolygon_w0iq3f$(element, f.v, b.v, f.v, b.v);
    }
    if (this.front_0 != null)
      f.v = ensureNotNull(this.front_0).clipPolygons_0(f.v);
    b.v = (tmp$_0 = (tmp$ = this.back_0) != null ? tmp$.clipPolygons_0(b.v) : null) != null ? tmp$_0 : ArrayList_init_0();
    f.v.addAll_brywnq$(b.v);
    return f.v;
  };
  Node.prototype.clipTo_n3rvgf$ = function (bsp) {
    var tmp$, tmp$_0;
    return new Node(bsp.clipPolygons_0(this.polygons_0), this.plane_0, (tmp$ = this.front_0) != null ? tmp$.clipTo_n3rvgf$(bsp) : null, (tmp$_0 = this.back_0) != null ? tmp$_0.clipTo_n3rvgf$(bsp) : null);
  };
  var emptyList = Kotlin.kotlin.collections.emptyList_287e2$;
  Node.prototype.allPolygons = function () {
    var tmp$, tmp$_0, tmp$_1, tmp$_2;
    return plus(plus(this.polygons_0, (tmp$_0 = (tmp$ = this.front_0) != null ? tmp$.allPolygons() : null) != null ? tmp$_0 : emptyList()), (tmp$_2 = (tmp$_1 = this.back_0) != null ? tmp$_1.allPolygons() : null) != null ? tmp$_2 : emptyList());
  };
  Node.prototype.combine_n3rvgf$ = function (node) {
    return this.combine_1k44c0$(node.allPolygons());
  };
  Node.prototype.combine_1k44c0$ = function (polygons) {
    return this.copy().build_0(polygons);
  };
  Node.prototype.build_0 = function (polygons) {
    var tmp$;
    this.plane_0 = (tmp$ = this.plane_0) != null ? tmp$ : polygons.get_za3lpa$(0).plane;
    var f = ArrayList_init_0();
    var b = ArrayList_init_0();
    var tmp$_0;
    tmp$_0 = polygons.iterator();
    while (tmp$_0.hasNext()) {
      var element = tmp$_0.next();
      ensureNotNull(this.plane_0).splitPolygon_w0iq3f$(element, this.polygons_0, this.polygons_0, f, b);
    }
    if (!f.isEmpty()) {
      if (this.front_0 == null)
        this.front_0 = Node_init();
      ensureNotNull(this.front_0).build_0(f);
    }
    if (!b.isEmpty()) {
      if (this.back_0 == null)
        this.back_0 = Node_init();
      ensureNotNull(this.back_0).build_0(b);
    }
    return this;
  };
  Node.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Node',
    interfaces: []
  };
  function Node_init($this) {
    $this = $this || Object.create(Node.prototype);
    Node.call($this, ArrayList_init_0(), null, null, null);
    return $this;
  }
  function Node_init_0(polygons, $this) {
    $this = $this || Object.create(Node.prototype);
    Node_init($this);
    $this.build_0(toMutableList(polygons));
    return $this;
  }
  function cube(center, radius) {
    if (center === void 0)
      center = unit;
    if (radius === void 0)
      radius = unit;
    var $receiver = listOf([listOf([listOf([0, 4, 6, 2]), listOf([-1, 0, 0])]), listOf([listOf([1, 3, 7, 5]), listOf([1, 0, 0])]), listOf([listOf([0, 1, 5, 4]), listOf([0, -1, 0])]), listOf([listOf([2, 6, 7, 3]), listOf([0, 1, 0])]), listOf([listOf([0, 2, 3, 1]), listOf([0, 0, -1])]), listOf([listOf([4, 5, 7, 6]), listOf([0, 0, 1])])]);
    var destination = ArrayList_init(collectionSizeOrDefault($receiver, 10));
    var tmp$;
    tmp$ = $receiver.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      var tmp$_0 = destination.add_11rb$;
      var $receiver_0 = item.get_za3lpa$(0);
      var destination_0 = ArrayList_init(collectionSizeOrDefault($receiver_0, 10));
      var tmp$_1;
      tmp$_1 = $receiver_0.iterator();
      while (tmp$_1.hasNext()) {
        var item_0 = tmp$_1.next();
        var tmp$_2 = destination_0.add_11rb$;
        var pos = new Vector(center.x + radius.x * ((2 * (item_0 & 1) | 0) - 1 | 0), center.y + radius.y * ((2 * (item_0 >> 1 & 1) | 0) - 1 | 0), center.z + radius.z * ((2 * (item_0 >> 2 & 1) | 0) - 1 | 0));
        tmp$_2.call(destination_0, new Vertex(pos, new Vector(item.get_za3lpa$(1).get_za3lpa$(0), item.get_za3lpa$(1).get_za3lpa$(1), item.get_za3lpa$(1).get_za3lpa$(2))));
      }
      tmp$_0.call(destination, new Polygon(destination_0));
    }
    return Csg_init(destination);
  }
  function prism(length, rightHand, points) {
    return prism_0(length, rightHand, toList(points));
  }
  function prism$side(closure$n, closure$dn) {
    return function (p1, p2) {
      var r = closure$n.x_wk194y$(p1.minus_wk194y$(p2).unit());
      return Polygon_init([new Vertex(p1, r), new Vertex(p2, r), new Vertex(p2.plus_wk194y$(closure$dn), r), new Vertex(p1.plus_wk194y$(closure$dn), r)]);
    };
  }
  function prism$lambda(closure$points, closure$n, closure$dn, closure$side) {
    return function ($receiver) {
      var tmp$;
      var $receiver_0 = reversed(closure$points);
      var destination = ArrayList_init(collectionSizeOrDefault($receiver_0, 10));
      var tmp$_0;
      tmp$_0 = $receiver_0.iterator();
      while (tmp$_0.hasNext()) {
        var item = tmp$_0.next();
        destination.add_11rb$(new Vertex(item, closure$n.unaryMinus()));
      }
      $receiver.add_2npujf$(new Polygon(destination));
      var $receiver_1 = closure$points;
      var destination_0 = ArrayList_init(collectionSizeOrDefault($receiver_1, 10));
      var tmp$_1;
      tmp$_1 = $receiver_1.iterator();
      while (tmp$_1.hasNext()) {
        var item_0 = tmp$_1.next();
        var tmp$_2 = destination_0.add_11rb$;
        var closure$dn_0 = closure$dn;
        var closure$n_0 = closure$n;
        tmp$_2.call(destination_0, new Vertex(item_0.plus_wk194y$(closure$dn_0), closure$n_0));
      }
      $receiver.add_2npujf$(new Polygon(destination_0));
      tmp$ = closure$points.size;
      for (var i = 0; i < tmp$; i++) {
        $receiver.add_2npujf$(closure$side(invoke(closure$points, i), invoke(closure$points, i + 1 | 0)));
      }
      return Unit;
    };
  }
  var Collection = Kotlin.kotlin.collections.Collection;
  function prism_0(length, rightHand, points) {
    var p = Plane$Companion_getInstance().fromPoints_2xkzaf$(points.get_za3lpa$(0), points.get_za3lpa$(1), points.get_za3lpa$(2), rightHand);
    var any$result;
    any$break: do {
      var tmp$;
      if (Kotlin.isType(points, Collection) && points.isEmpty()) {
        any$result = false;
        break any$break;
      }
      tmp$ = points.iterator();
      while (tmp$.hasNext()) {
        var element = tmp$.next();
        if (!p.contains_wk194y$(element)) {
          any$result = true;
          break any$break;
        }
      }
      any$result = false;
    }
     while (false);
    if (any$result)
      throw IllegalArgumentException_init('not all points in a plane');
    var n = p.normal;
    var dn = n.times_14dthe$(length);
    var side = prism$side(n, dn);
    return Csg$Companion_getInstance().ofPolygons_w9xryh$(prism$lambda(points, n, dn, side));
  }
  function prismRing(width, length, rightHand, points) {
    return prismRing_0(width, length, rightHand, toList(points));
  }
  function prismRing_0(width, length, rightHand, points) {
    return prismRing_1(width, width, length, rightHand, points);
  }
  function prismRing$side(closure$n, closure$w1, closure$w2, closure$dn) {
    return function (p0, p1, p2, p3) {
      var tmp$, tmp$_0;
      var r0 = closure$n.x_wk194y$(p1.minus_wk194y$(p0).unit());
      var r1 = closure$n.x_wk194y$(p2.minus_wk194y$(p1).unit());
      var r2 = closure$n.x_wk194y$(p3.minus_wk194y$(p2).unit());
      var e1 = Plane$Companion_getInstance().fromVertex_wkatpf$(new Vertex(p1.plus_wk194y$(r1.times_14dthe$(closure$w1)), r1));
      var f1 = Plane$Companion_getInstance().fromVertex_wkatpf$(new Vertex(p1.minus_wk194y$(r1.times_14dthe$(closure$w2)), r1.unaryMinus()));
      if (r0 != null ? r0.equals(r1) : null)
        tmp$ = new Pair(p1.plus_wk194y$(r1.times_14dthe$(closure$w1)), p1.minus_wk194y$(r1.times_14dthe$(closure$w2)));
      else {
        var e0 = Plane$Companion_getInstance().fromVertex_wkatpf$(new Vertex(p0.plus_wk194y$(r0.times_14dthe$(closure$w1)), r0));
        var f0 = Plane$Companion_getInstance().fromVertex_wkatpf$(new Vertex(p0.minus_wk194y$(r0.times_14dthe$(closure$w2)), r0.unaryMinus()));
        tmp$ = new Pair(Plane$Companion_getInstance().fromVertex_wkatpf$(new Vertex(p1, closure$n)).and_wkatpf$(e0.and_5wtx37$(e1)).pos, Plane$Companion_getInstance().fromVertex_wkatpf$(new Vertex(p1, closure$n)).and_wkatpf$(f0.and_5wtx37$(f1)).pos);
      }
      var tmp$_1 = tmp$;
      var p11 = tmp$_1.component1()
      , p12 = tmp$_1.component2();
      if (r1 != null ? r1.equals(r2) : null)
        tmp$_0 = new Pair(p2.plus_wk194y$(r1.times_14dthe$(closure$w1)), p2.minus_wk194y$(r1.times_14dthe$(closure$w2)));
      else {
        var e2 = Plane$Companion_getInstance().fromVertex_wkatpf$(new Vertex(p2.plus_wk194y$(r2.times_14dthe$(closure$w1)), r2));
        var f2 = Plane$Companion_getInstance().fromVertex_wkatpf$(new Vertex(p2.minus_wk194y$(r2.times_14dthe$(closure$w2)), r2.unaryMinus()));
        tmp$_0 = new Pair(Plane$Companion_getInstance().fromVertex_wkatpf$(new Vertex(p2, closure$n)).and_wkatpf$(e1.and_5wtx37$(e2)).pos, Plane$Companion_getInstance().fromVertex_wkatpf$(new Vertex(p2, closure$n)).and_wkatpf$(f1.and_5wtx37$(f2)).pos);
      }
      var tmp$_2 = tmp$_0;
      var p21 = tmp$_2.component1()
      , p22 = tmp$_2.component2();
      return listOf([Polygon_init([new Vertex(p11, r1), new Vertex(p11.plus_wk194y$(closure$dn), r1), new Vertex(p21.plus_wk194y$(closure$dn), r1), new Vertex(p21, r1)]), Polygon_init([new Vertex(p12, r1.unaryMinus()), new Vertex(p22, r1.unaryMinus()), new Vertex(p22.plus_wk194y$(closure$dn), r1.unaryMinus()), new Vertex(p12.plus_wk194y$(closure$dn), r1.unaryMinus())]), Polygon_init([new Vertex(p11, closure$n.unaryMinus()), new Vertex(p21, closure$n.unaryMinus()), new Vertex(p22, closure$n.unaryMinus()), new Vertex(p12, closure$n.unaryMinus())]), Polygon_init([new Vertex(p11.plus_wk194y$(closure$dn), closure$n), new Vertex(p12.plus_wk194y$(closure$dn), closure$n), new Vertex(p22.plus_wk194y$(closure$dn), closure$n), new Vertex(p21.plus_wk194y$(closure$dn), closure$n)])]);
    };
  }
  function prismRing$lambda(closure$points, closure$side) {
    return function ($receiver) {
      var tmp$;
      tmp$ = closure$points.size;
      for (var i = 0; i < tmp$; i++) {
        $receiver.addAll_1k44c0$(closure$side(invoke(closure$points, i), invoke(closure$points, i + 1 | 0), invoke(closure$points, i + 2 | 0), invoke(closure$points, i + 3 | 0)));
      }
      return Unit;
    };
  }
  function prismRing_1(w1, w2, length, rightHand, points) {
    var p = Plane$Companion_getInstance().fromPoints_2xkzaf$(points.get_za3lpa$(0), points.get_za3lpa$(1), points.get_za3lpa$(2), rightHand);
    var any$result;
    any$break: do {
      var tmp$;
      if (Kotlin.isType(points, Collection) && points.isEmpty()) {
        any$result = false;
        break any$break;
      }
      tmp$ = points.iterator();
      while (tmp$.hasNext()) {
        var element = tmp$.next();
        if (!p.contains_wk194y$(element)) {
          any$result = true;
          break any$break;
        }
      }
      any$result = false;
    }
     while (false);
    if (any$result)
      throw IllegalArgumentException_init('not all points in a plane');
    var n = p.normal;
    var dn = n.times_14dthe$(length);
    var side = prismRing$side(n, w1, w2, dn);
    return Csg$Companion_getInstance().ofPolygons_w9xryh$(prismRing$lambda(points, side));
  }
  function sphere$vertex(closure$center, closure$radiusFunc, closure$radius) {
    return function (phi, theta) {
      var tmp$, tmp$_0;
      var dir = Vector$Companion_getInstance().ofSpherical_yvo9jy$(-1.0, theta * math.PI, phi * math.PI * 2);
      tmp$_0 = dir.times_14dthe$((tmp$ = closure$radiusFunc != null ? closure$radiusFunc(phi, theta) : null) != null ? tmp$ : closure$radius);
      return new Vertex(closure$center.plus_wk194y$(tmp$_0), dir);
    };
  }
  function sphere$lambda(closure$slices, closure$stacks, closure$vertex) {
    return function ($receiver) {
      var tmp$, tmp$_0;
      tmp$ = closure$slices;
      for (var i = 0; i < tmp$; i++) {
        var id = i;
        tmp$_0 = closure$stacks;
        for (var j = 0; j < tmp$_0; j++) {
          var vertices = ArrayList_init_0();
          var jd = j;
          vertices.add_11rb$(closure$vertex(id / closure$slices, jd / closure$stacks));
          if (j > 0)
            vertices.add_11rb$(closure$vertex((id + 1) / closure$slices, jd / closure$stacks));
          if (j < (closure$stacks - 1 | 0))
            vertices.add_11rb$(closure$vertex((id + 1) / closure$slices, (jd + 1) / closure$stacks));
          vertices.add_11rb$(closure$vertex(id / closure$slices, (jd + 1) / closure$stacks));
          $receiver.add_2npujf$(new Polygon(vertices));
        }
      }
      return Unit;
    };
  }
  function sphere(center, radius, slices, stacks, radiusFunc) {
    if (center === void 0)
      center = unit;
    if (radius === void 0)
      radius = 1.0;
    if (slices === void 0)
      slices = 32;
    if (stacks === void 0)
      stacks = 16;
    if (radiusFunc === void 0)
      radiusFunc = null;
    var vertex = sphere$vertex(center, radiusFunc, radius);
    return Csg$Companion_getInstance().ofPolygons_w9xryh$(sphere$lambda(slices, stacks, vertex));
  }
  function cylinder$point(closure$axisX, closure$axisY, closure$start, closure$ray, closure$radiusFunc, closure$radius) {
    return function (stack, slice) {
      var tmp$;
      var angle = slice * math.PI * 2;
      var out = closure$axisX.times_14dthe$(Math_0.cos(angle)).plus_wk194y$(closure$axisY.times_14dthe$(Math_0.sin(angle)));
      return closure$start.plus_wk194y$(closure$ray.times_14dthe$(stack)).plus_wk194y$(out.times_14dthe$((tmp$ = closure$radiusFunc != null ? closure$radiusFunc(angle, stack) : null) != null ? tmp$ : closure$radius));
    };
  }
  function cylinder$lambda(closure$slices, closure$start, closure$point, closure$stacks, closure$end) {
    return function ($receiver) {
      var tmp$, tmp$_0;
      tmp$ = closure$slices;
      for (var i = 0; i < tmp$; i++) {
        var id = i;
        var t0 = id / closure$slices;
        var t1 = (id + 1) / closure$slices;
        $receiver.add_2npujf$(Polygon_init_1(true, [closure$start, closure$point(0.0, t0), closure$point(0.0, t1)]));
        tmp$_0 = closure$stacks;
        for (var j = 0; j < tmp$_0; j++) {
          var jd = j;
          var j0 = jd / closure$stacks;
          var j1 = (jd + 1) / closure$stacks;
          $receiver.add_2npujf$(Polygon_init_1(true, [closure$point(j0, t1), closure$point(j0, t0), closure$point(j1, t0), closure$point(j1, t1)]));
        }
        $receiver.add_2npujf$(Polygon_init_1(true, [closure$end, closure$point(1.0, t1), closure$point(1.0, t0)]));
      }
      return Unit;
    };
  }
  function cylinder(start, end, radius, slices, stacks, radiusFunc) {
    if (start === void 0)
      start = yUnit.unaryMinus();
    if (end === void 0)
      end = yUnit;
    if (radius === void 0)
      radius = 1.0;
    if (slices === void 0)
      slices = 32;
    if (stacks === void 0)
      stacks = 16;
    if (radiusFunc === void 0)
      radiusFunc = null;
    var ray = end.minus_wk194y$(start);
    var axisZ = ray.unit();
    var x = axisZ.y;
    var isY = Math_0.abs(x) > 0.5 ? 1.0 : 0.0;
    var axisX = (new Vector(isY, 1.0 - isY, 0.0)).x_wk194y$(axisZ).unit();
    var axisY = axisX.x_wk194y$(axisZ).unit();
    var point = cylinder$point(axisX, axisY, start, ray, radiusFunc, radius);
    return Csg$Companion_getInstance().ofPolygons_w9xryh$(cylinder$lambda(slices, start, point, stacks, end));
  }
  function ring$vertex(closure$center) {
    return function (r, a, b, norm) {
      return new Vertex(closure$center.plus_wk194y$(Vector$Companion_getInstance().ofSpherical_yvo9jy$(r, b, a)), norm);
    };
  }
  function ring$vertex_0(closure$center) {
    return function (r, a, b, dir) {
      var v = Vector$Companion_getInstance().ofSpherical_yvo9jy$(r, b, a);
      return new Vertex(v.plus_wk194y$(closure$center), v.times_14dthe$(dir));
    };
  }
  function ring$lambda(closure$a, closure$da, closure$h, closure$radius, closure$vertex, closure$r, closure$vertex_0) {
    return function ($receiver) {
      while (closure$a.v < 2 * math.PI + closure$da / 2) {
        var b = (math.PI - closure$h / closure$radius) / 2;
        $receiver.add_2npujf$(Polygon_init([closure$vertex(closure$radius, closure$a.v, b, zUnit), closure$vertex(closure$radius + closure$r, closure$a.v, b, zUnit), closure$vertex(closure$radius + closure$r, closure$a.v + closure$da, b, zUnit), closure$vertex(closure$radius, closure$a.v + closure$da, b, zUnit)]));
        var db = closure$h / closure$radius;
        while (b < (math.PI + closure$h / closure$radius) / 2) {
          $receiver.add_2npujf$(Polygon_init([closure$vertex_0(closure$radius, closure$a.v, b, -1), closure$vertex_0(closure$radius, closure$a.v + closure$da, b, -1), closure$vertex_0(closure$radius, closure$a.v + closure$da, b + db, -1), closure$vertex_0(closure$radius, closure$a.v, b + db, -1)]));
          $receiver.add_2npujf$(Polygon_init([closure$vertex_0(closure$radius + closure$r, closure$a.v, b, 1), closure$vertex_0(closure$radius + closure$r, closure$a.v, b + db, 1), closure$vertex_0(closure$radius + closure$r, closure$a.v + closure$da, b + db, 1), closure$vertex_0(closure$radius + closure$r, closure$a.v + closure$da, b, 1)]));
          b += db;
        }
        $receiver.add_2npujf$(Polygon_init([closure$vertex(closure$radius, closure$a.v, b, zUnit.unaryMinus()), closure$vertex(closure$radius, closure$a.v + closure$da, b, zUnit.unaryMinus()), closure$vertex(closure$radius + closure$r, closure$a.v + closure$da, b, zUnit.unaryMinus()), closure$vertex(closure$radius + closure$r, closure$a.v, b, zUnit.unaryMinus())]));
        closure$a.v += closure$da;
      }
      return Unit;
    };
  }
  function ring(center, radius, r, h, slices) {
    if (center === void 0)
      center = unit;
    if (radius === void 0)
      radius = 1.0;
    if (r === void 0)
      r = 1.0;
    if (h === void 0)
      h = 1.0;
    if (slices === void 0)
      slices = 32;
    var vertex = ring$vertex(center);
    var vertex_0 = ring$vertex_0(center);
    var da = 2 * math.PI / slices;
    var a = {v: 0.0};
    return Csg$Companion_getInstance().ofPolygons_w9xryh$(ring$lambda(a, da, h, radius, vertex, r, vertex_0));
  }
  var EPSILON;
  function Plane(normal, w) {
    Plane$Companion_getInstance();
    this.normal = normal;
    this.w_0 = w;
  }
  function Plane$Type(name, ordinal, i) {
    Enum.call(this);
    this.i = i;
    this.name$ = name;
    this.ordinal$ = ordinal;
  }
  function Plane$Type_initFields() {
    Plane$Type_initFields = function () {
    };
    Plane$Type$COPLANAR_instance = new Plane$Type('COPLANAR', 0, 0);
    Plane$Type$FRONT_instance = new Plane$Type('FRONT', 1, 1);
    Plane$Type$BACK_instance = new Plane$Type('BACK', 2, 2);
    Plane$Type$SPANNING_instance = new Plane$Type('SPANNING', 3, 3);
  }
  var Plane$Type$COPLANAR_instance;
  function Plane$Type$COPLANAR_getInstance() {
    Plane$Type_initFields();
    return Plane$Type$COPLANAR_instance;
  }
  var Plane$Type$FRONT_instance;
  function Plane$Type$FRONT_getInstance() {
    Plane$Type_initFields();
    return Plane$Type$FRONT_instance;
  }
  var Plane$Type$BACK_instance;
  function Plane$Type$BACK_getInstance() {
    Plane$Type_initFields();
    return Plane$Type$BACK_instance;
  }
  var Plane$Type$SPANNING_instance;
  function Plane$Type$SPANNING_getInstance() {
    Plane$Type_initFields();
    return Plane$Type$SPANNING_instance;
  }
  Plane$Type.prototype.or_prxx1x$ = function (t) {
    var $receiver = Plane$Type$values();
    var firstOrNull$result;
    firstOrNull$break: do {
      var tmp$;
      for (tmp$ = 0; tmp$ !== $receiver.length; ++tmp$) {
        var element = $receiver[tmp$];
        if (element.i === (this.i | t.i)) {
          firstOrNull$result = element;
          break firstOrNull$break;
        }
      }
      firstOrNull$result = null;
    }
     while (false);
    return ensureNotNull(firstOrNull$result);
  };
  Plane$Type.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Type',
    interfaces: [Enum]
  };
  function Plane$Type$values() {
    return [Plane$Type$COPLANAR_getInstance(), Plane$Type$FRONT_getInstance(), Plane$Type$BACK_getInstance(), Plane$Type$SPANNING_getInstance()];
  }
  Plane$Type.values = Plane$Type$values;
  function Plane$Type$valueOf(name) {
    switch (name) {
      case 'COPLANAR':
        return Plane$Type$COPLANAR_getInstance();
      case 'FRONT':
        return Plane$Type$FRONT_getInstance();
      case 'BACK':
        return Plane$Type$BACK_getInstance();
      case 'SPANNING':
        return Plane$Type$SPANNING_getInstance();
      default:throwISE('No enum constant guru.nidi.simple3d.model.Plane.Type.' + name);
    }
  }
  Plane$Type.valueOf_61zpoe$ = Plane$Type$valueOf;
  function Plane$Companion() {
    Plane$Companion_instance = this;
  }
  Plane$Companion.prototype.fromPoints_2xkzaf$ = function (a, b, c, rightHand) {
    if (rightHand === void 0)
      rightHand = true;
    var n = times(rightHand ? 1 : -1, b.minus_wk194y$(a).x_wk194y$(c.minus_wk194y$(a)).unit());
    return new Plane(n, n.times_wk194y$(a));
  };
  Plane$Companion.prototype.fromVertex_wkatpf$ = function (a) {
    return new Plane(a.normal, a.normal.times_wk194y$(a.pos));
  };
  Plane$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var Plane$Companion_instance = null;
  function Plane$Companion_getInstance() {
    if (Plane$Companion_instance === null) {
      new Plane$Companion();
    }
    return Plane$Companion_instance;
  }
  Plane.prototype.contains_wk194y$ = function (point) {
    var x = this.normal.times_wk194y$(point) - this.w_0;
    return Math_0.abs(x) < EPSILON;
  };
  Plane.prototype.unaryMinus = function () {
    return new Plane(this.normal.unaryMinus(), -this.w_0);
  };
  Plane.prototype.isParallel_5wtx37$ = function (p) {
    return this.normal.x_wk194y$(p.normal).length() < EPSILON;
  };
  Plane.prototype.intersect_5wtx37$ = function (p) {
    var tmp$;
    var n = this.normal.x_wk194y$(p.normal);
    if (n.x !== 0.0) {
      var y = (this.w_0 * p.normal.z - p.w_0 * this.normal.z) / (this.normal.y * p.normal.z - this.normal.z * p.normal.y);
      var z = (this.w_0 * p.normal.y - p.w_0 * this.normal.y) / (this.normal.z * p.normal.y - this.normal.y * p.normal.z);
      tmp$ = new Vertex(v_0(0, y, z), n);
    }
     else if (n.y !== 0.0) {
      var x = (this.w_0 * p.normal.z - p.w_0 * this.normal.z) / (this.normal.x * p.normal.z - this.normal.z * p.normal.x);
      var z_0 = (this.w_0 * p.normal.x - p.w_0 * this.normal.x) / (this.normal.z * p.normal.x - this.normal.x * p.normal.z);
      tmp$ = new Vertex(v_1(x, 0, z_0), n);
    }
     else if (n.z !== 0.0) {
      var x_0 = (this.w_0 * p.normal.y - p.w_0 * this.normal.y) / (this.normal.x * p.normal.y - this.normal.y * p.normal.x);
      var y_0 = (this.w_0 * p.normal.x - p.w_0 * this.normal.x) / (this.normal.y * p.normal.x - this.normal.x * p.normal.y);
      tmp$ = new Vertex(v_2(x_0, y_0, 0), n);
    }
     else
      throw IllegalArgumentException_init('Parallel planes');
    return tmp$;
  };
  Plane.prototype.intersect_wkatpf$ = function (v_0) {
    var t = (this.w_0 - v_0.pos.times_wk194y$(this.normal)) / v_0.normal.times_wk194y$(this.normal);
    return new Vertex(v(v_0.pos.x + t * v_0.normal.x, v_0.pos.y + t * v_0.normal.y, v_0.pos.z + t * v_0.normal.z), v_0.normal);
  };
  Plane.prototype.and_5wtx37$ = function (p) {
    return this.intersect_5wtx37$(p);
  };
  Plane.prototype.and_wkatpf$ = function (v) {
    return this.intersect_wkatpf$(v);
  };
  var checkIndexOverflow = Kotlin.kotlin.collections.checkIndexOverflow_za3lpa$;
  Plane.prototype.splitPolygon_w0iq3f$ = function (polygon, coplanarFront, coplanarBack, front, back) {
    var polygonType = {v: Plane$Type$COPLANAR_getInstance()};
    var $receiver = polygon.vertices;
    var destination = ArrayList_init(collectionSizeOrDefault($receiver, 10));
    var tmp$;
    tmp$ = $receiver.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      var tmp$_0 = destination.add_11rb$;
      var tmp$_1;
      var t = this.normal.times_wk194y$(item.pos) - this.w_0;
      if (t < -EPSILON)
        tmp$_1 = Plane$Type$BACK_getInstance();
      else if (t > EPSILON)
        tmp$_1 = Plane$Type$FRONT_getInstance();
      else
        tmp$_1 = Plane$Type$COPLANAR_getInstance();
      var type = tmp$_1;
      polygonType.v = polygonType.v.or_prxx1x$(type);
      tmp$_0.call(destination, type);
    }
    var types = destination;
    switch (polygonType.v.name) {
      case 'COPLANAR':
        var co = this.normal.times_wk194y$(polygon.plane.normal) > 0 ? coplanarFront : coplanarBack;
        co.add_11rb$(polygon);
        break;
      case 'FRONT':
        front.add_11rb$(polygon);
        break;
      case 'BACK':
        back.add_11rb$(polygon);
        break;
      case 'SPANNING':
        var f = ArrayList_init_0();
        var b = ArrayList_init_0();
        var tmp$_2, tmp$_0_0;
        var index = 0;
        tmp$_2 = polygon.vertices.iterator();
        while (tmp$_2.hasNext()) {
          var item_0 = tmp$_2.next();
          var i = checkIndexOverflow((tmp$_0_0 = index, index = tmp$_0_0 + 1 | 0, tmp$_0_0));
          var j = (i + 1 | 0) % polygon.vertices.size;
          var ti = types.get_za3lpa$(i);
          var tj = types.get_za3lpa$(j);
          var vi = polygon.vertices.get_za3lpa$(i);
          var vj = polygon.vertices.get_za3lpa$(j);
          if (ti !== Plane$Type$BACK_getInstance())
            f.add_11rb$(vi);
          if (ti !== Plane$Type$FRONT_getInstance())
            b.add_11rb$(vi);
          if (ti.or_prxx1x$(tj) === Plane$Type$SPANNING_getInstance()) {
            var t_0 = (this.w_0 - this.normal.times_wk194y$(vi.pos)) / this.normal.times_wk194y$(vj.pos.minus_wk194y$(vi.pos));
            var v = vi.interpolate_epc5fv$(vj, t_0);
            f.add_11rb$(v);
            b.add_11rb$(v);
          }
        }

        if (f.size >= 3)
          front.add_11rb$(new Polygon(f));
        if (b.size >= 3)
          back.add_11rb$(new Polygon(b));
        break;
      default:Kotlin.noWhenBranchMatched();
        break;
    }
  };
  Plane.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Plane',
    interfaces: []
  };
  Plane.prototype.component1 = function () {
    return this.normal;
  };
  Plane.prototype.component2_0 = function () {
    return this.w_0;
  };
  Plane.prototype.copy_1dsxm4$ = function (normal, w) {
    return new Plane(normal === void 0 ? this.normal : normal, w === void 0 ? this.w_0 : w);
  };
  Plane.prototype.toString = function () {
    return 'Plane(normal=' + Kotlin.toString(this.normal) + (', w=' + Kotlin.toString(this.w_0)) + ')';
  };
  Plane.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.normal) | 0;
    result = result * 31 + Kotlin.hashCode(this.w_0) | 0;
    return result;
  };
  Plane.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.normal, other.normal) && Kotlin.equals(this.w_0, other.w_0)))));
  };
  function Polygon(vertices) {
    Polygon$Companion_getInstance();
    this.vertices = vertices;
    this.plane = Plane$Companion_getInstance().fromVertex_wkatpf$(this.vertices.get_za3lpa$(0));
    var $receiver = this.vertices;
    var destination = ArrayList_init_0();
    var tmp$;
    tmp$ = $receiver.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      if (!this.plane.contains_wk194y$(element.pos))
        destination.add_11rb$(element);
    }
    var nonPlanar = destination;
    if (!nonPlanar.isEmpty())
      throw IllegalArgumentException_init('not all points in a plane: ' + nonPlanar);
  }
  function Polygon$Companion() {
    Polygon$Companion_instance = this;
  }
  Polygon$Companion.prototype.toVertices_1z2e3i$ = function (rightHand, points) {
    var n = times(rightHand ? 1 : -1, Plane$Companion_getInstance().fromPoints_2xkzaf$(points.get_za3lpa$(0), points.get_za3lpa$(1), points.get_za3lpa$(2)).normal);
    var destination = ArrayList_init(collectionSizeOrDefault(points, 10));
    var tmp$;
    tmp$ = points.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      destination.add_11rb$(new Vertex(item, n));
    }
    return destination;
  };
  Polygon$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var Polygon$Companion_instance = null;
  function Polygon$Companion_getInstance() {
    if (Polygon$Companion_instance === null) {
      new Polygon$Companion();
    }
    return Polygon$Companion_instance;
  }
  Polygon.prototype.unaryMinus = function () {
    var $receiver = reversed(this.vertices);
    var destination = ArrayList_init(collectionSizeOrDefault($receiver, 10));
    var tmp$;
    tmp$ = $receiver.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      destination.add_11rb$(item.unaryMinus());
    }
    return new Polygon(destination);
  };
  Polygon.prototype.boundingBox = function () {
    var tmp$;
    var minX = kotlin_js_internal_DoubleCompanionObject.MAX_VALUE;
    var maxX = kotlin_js_internal_DoubleCompanionObject.MIN_VALUE;
    var minY = kotlin_js_internal_DoubleCompanionObject.MAX_VALUE;
    var maxY = kotlin_js_internal_DoubleCompanionObject.MIN_VALUE;
    var minZ = kotlin_js_internal_DoubleCompanionObject.MAX_VALUE;
    var maxZ = kotlin_js_internal_DoubleCompanionObject.MIN_VALUE;
    tmp$ = this.vertices.iterator();
    while (tmp$.hasNext()) {
      var v = tmp$.next();
      if (v.pos.x < minX)
        minX = v.pos.x;
      if (v.pos.x > maxX)
        maxX = v.pos.x;
      if (v.pos.y < minY)
        minY = v.pos.y;
      if (v.pos.y > maxY)
        maxY = v.pos.y;
      if (v.pos.z < minZ)
        minZ = v.pos.z;
      if (v.pos.z > maxZ)
        maxZ = v.pos.z;
    }
    return new Pair(new Vector(minX, minY, minZ), new Vector(maxX, maxY, maxZ));
  };
  Polygon.prototype.size = function () {
    var it = this.boundingBox();
    return it.second.minus_wk194y$(it.first).abs();
  };
  Polygon.prototype.toTriangles = function () {
    return triangulate(this.vertices);
  };
  Polygon.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Polygon',
    interfaces: []
  };
  function Polygon_init(vs, $this) {
    $this = $this || Object.create(Polygon.prototype);
    Polygon.call($this, asList(vs));
    return $this;
  }
  function Polygon_init_0(rightHand, points, $this) {
    $this = $this || Object.create(Polygon.prototype);
    Polygon.call($this, Polygon$Companion_getInstance().toVertices_1z2e3i$(rightHand, points));
    return $this;
  }
  function Polygon_init_1(rightHand, points, $this) {
    $this = $this || Object.create(Polygon.prototype);
    Polygon_init_0(rightHand, asList(points), $this);
    return $this;
  }
  Polygon.prototype.component1 = function () {
    return this.vertices;
  };
  Polygon.prototype.copy_ssxrte$ = function (vertices) {
    return new Polygon(vertices === void 0 ? this.vertices : vertices);
  };
  Polygon.prototype.toString = function () {
    return 'Polygon(vertices=' + Kotlin.toString(this.vertices) + ')';
  };
  Polygon.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.vertices) | 0;
    return result;
  };
  Polygon.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && Kotlin.equals(this.vertices, other.vertices))));
  };
  var CONCAVE;
  var CONVEX;
  function triangulate$pred(closure$cs) {
    return function (i) {
      return i === 0 ? get_lastIndex(closure$cs) : i - 1 | 0;
    };
  }
  function triangulate$succ(closure$cs) {
    return function (i) {
      return i === get_lastIndex(closure$cs) ? 0 : i + 1 | 0;
    };
  }
  function triangulate$spannedAreaSign(p1, p2, p3) {
    var t = p2.pos.minus_wk194y$(p1.pos).x_wk194y$(p3.pos.minus_wk194y$(p2.pos));
    var x = t.times_wk194y$(p1.normal);
    return numberToInt(Math_0.sign(x));
  }
  function triangulate$classify(closure$cs, closure$pred, closure$succ, closure$spannedAreaSign) {
    return function (n) {
      return closure$spannedAreaSign(closure$cs.get_za3lpa$(closure$pred(n)), closure$cs.get_za3lpa$(n), closure$cs.get_za3lpa$(closure$succ(n)));
    };
  }
  function triangulate$addTriangle(closure$tris, closure$cs, closure$pred, closure$succ) {
    return function (n) {
      try {
        var $receiver = closure$tris;
        var element = Polygon_init([closure$cs.get_za3lpa$(closure$pred(n)), closure$cs.get_za3lpa$(n), closure$cs.get_za3lpa$(closure$succ(n))]);
        $receiver.add_11rb$(element);
      }
       catch (e) {
        if (!Kotlin.isType(e, IllegalArgumentException))
          throw e;
      }
    };
  }
  function triangulate$isEarTip(closure$vertexType, closure$pred, closure$succ, closure$cs, closure$spannedAreaSign) {
    return function (n) {
      if (closure$vertexType.get_za3lpa$(n) === -1)
        return false;
      var prev = closure$pred(n);
      var next = closure$succ(n);
      var i = closure$succ(next);
      while (i !== prev) {
        if (closure$vertexType.get_za3lpa$(i) !== 1 && closure$spannedAreaSign(closure$cs.get_za3lpa$(next), closure$cs.get_za3lpa$(prev), closure$cs.get_za3lpa$(i)) >= 0 && closure$spannedAreaSign(closure$cs.get_za3lpa$(prev), closure$cs.get_za3lpa$(n), closure$cs.get_za3lpa$(i)) >= 0 && closure$spannedAreaSign(closure$cs.get_za3lpa$(n), closure$cs.get_za3lpa$(next), closure$cs.get_za3lpa$(i)) >= 0)
          return false;
        i = closure$succ(i);
      }
      return true;
    };
  }
  function triangulate$findEarTip(closure$cs, closure$isEarTip, closure$vertexType) {
    return function () {
      var tmp$, tmp$_0;
      tmp$ = closure$cs.size;
      for (var i = 0; i < tmp$; i++)
        if (closure$isEarTip(i))
          return i;
      tmp$_0 = closure$cs.size;
      for (var i_0 = 0; i_0 < tmp$_0; i_0++)
        if (closure$vertexType.get_za3lpa$(i_0) !== -1)
          return i_0;
      return 0;
    };
  }
  function triangulate$cutEarTip(closure$addTriangle, closure$cs, closure$vertexType) {
    return function (n) {
      closure$addTriangle(n);
      closure$cs.removeAt_za3lpa$(n);
      closure$vertexType.removeAt_za3lpa$(n);
    };
  }
  function triangulate(points) {
    var cs = toMutableList(points);
    var tris = ArrayList_init_0();
    var pred = triangulate$pred(cs);
    var succ = triangulate$succ(cs);
    var spannedAreaSign = triangulate$spannedAreaSign;
    var classify = triangulate$classify(cs, pred, succ, spannedAreaSign);
    var addTriangle = triangulate$addTriangle(tris, cs, pred, succ);
    var destination = ArrayList_init_0();
    var tmp$, tmp$_0;
    var index = 0;
    tmp$ = cs.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      destination.add_11rb$(classify(checkIndexOverflow((tmp$_0 = index, index = tmp$_0 + 1 | 0, tmp$_0))));
    }
    var vertexType = destination;
    var isEarTip = triangulate$isEarTip(vertexType, pred, succ, cs, spannedAreaSign);
    var findEarTip = triangulate$findEarTip(cs, isEarTip, vertexType);
    var cutEarTip = triangulate$cutEarTip(addTriangle, cs, vertexType);
    while (cs.size > 3) {
      var n = findEarTip();
      cutEarTip(n);
      var prev = pred(n);
      var next = n === cs.size ? 0 : n;
      vertexType.set_wxm5ur$(prev, classify(prev));
      vertexType.set_wxm5ur$(next, classify(next));
    }
    if (cs.size === 3) {
      addTriangle(1);
    }
    return tris;
  }
  function Vector(x, y, z) {
    Vector$Companion_getInstance();
    this.x = x;
    this.y = y;
    this.z = z;
  }
  function Vector$Companion() {
    Vector$Companion_instance = this;
  }
  Vector$Companion.prototype.ofSpherical_yvo9jy$ = function (r, theta, phi) {
    return new Vector(r * Math_0.sin(theta) * Math_0.cos(phi), r * Math_0.sin(theta) * Math_0.sin(phi), r * Math_0.cos(theta));
  };
  Vector$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var Vector$Companion_instance = null;
  function Vector$Companion_getInstance() {
    if (Vector$Companion_instance === null) {
      new Vector$Companion();
    }
    return Vector$Companion_instance;
  }
  Vector.prototype.unaryMinus = function () {
    return new Vector(-this.x, -this.y, -this.z);
  };
  Vector.prototype.plus_wk194y$ = function (a) {
    return new Vector(this.x + a.x, this.y + a.y, this.z + a.z);
  };
  Vector.prototype.minus_wk194y$ = function (a) {
    return new Vector(this.x - a.x, this.y - a.y, this.z - a.z);
  };
  Vector.prototype.times_14dthe$ = function (a) {
    return new Vector(this.x * a, this.y * a, this.z * a);
  };
  Vector.prototype.div_14dthe$ = function (a) {
    return new Vector(this.x / a, this.y / a, this.z / a);
  };
  Vector.prototype.times_wk194y$ = function (a) {
    return this.x * a.x + this.y * a.y + this.z * a.z;
  };
  Vector.prototype.x_wk194y$ = function (a) {
    return new Vector(this.y * a.z - this.z * a.y, this.z * a.x - this.x * a.z, this.x * a.y - this.y * a.x);
  };
  Vector.prototype.length = function () {
    var x = this.times_wk194y$(this);
    return Math_0.sqrt(x);
  };
  Vector.prototype.unit = function () {
    return this.div_14dthe$(this.length());
  };
  Vector.prototype.interpolate_1dsxm4$ = function (a, t) {
    return this.plus_wk194y$(a.minus_wk194y$(this).times_14dthe$(t));
  };
  Vector.prototype.abs = function () {
    var x = this.x;
    var tmp$ = Math_0.abs(x);
    var x_0 = this.y;
    var tmp$_0 = Math_0.abs(x_0);
    var x_1 = this.z;
    return new Vector(tmp$, tmp$_0, Math_0.abs(x_1));
  };
  Vector.prototype.scale_wk194y$ = function (a) {
    return new Vector(this.x * a.x, this.y * a.y, this.z * a.z);
  };
  Vector.prototype.scaleInv_wk194y$ = function (a) {
    return new Vector(this.x / a.x, this.y / a.y, this.z / a.z);
  };
  Vector.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Vector',
    interfaces: []
  };
  Vector.prototype.component1 = function () {
    return this.x;
  };
  Vector.prototype.component2 = function () {
    return this.y;
  };
  Vector.prototype.component3 = function () {
    return this.z;
  };
  Vector.prototype.copy_yvo9jy$ = function (x, y, z) {
    return new Vector(x === void 0 ? this.x : x, y === void 0 ? this.y : y, z === void 0 ? this.z : z);
  };
  Vector.prototype.toString = function () {
    return 'Vector(x=' + Kotlin.toString(this.x) + (', y=' + Kotlin.toString(this.y)) + (', z=' + Kotlin.toString(this.z)) + ')';
  };
  Vector.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.x) | 0;
    result = result * 31 + Kotlin.hashCode(this.y) | 0;
    result = result * 31 + Kotlin.hashCode(this.z) | 0;
    return result;
  };
  Vector.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.x, other.x) && Kotlin.equals(this.y, other.y) && Kotlin.equals(this.z, other.z)))));
  };
  function times($receiver, a) {
    return a.times_14dthe$($receiver);
  }
  function times_0($receiver, a) {
    return a.times_14dthe$($receiver);
  }
  var origin;
  var unit;
  var xUnit;
  var yUnit;
  var zUnit;
  function v(a, b, c) {
    return new Vector(a, b, c);
  }
  function v_0(a, b, c) {
    return new Vector(a, b, c);
  }
  function v_1(a, b, c) {
    return new Vector(a, b, c);
  }
  function v_2(a, b, c) {
    return new Vector(a, b, c);
  }
  function v_3(a, b, c) {
    return new Vector(a, b, c);
  }
  function v_4(a, b, c) {
    return new Vector(a, b, c);
  }
  function v_5(a, b, c) {
    return new Vector(a, b, c);
  }
  function v_6(a, b, c) {
    return new Vector(a, b, c);
  }
  function Vertex(pos, normal) {
    this.pos = pos;
    this.normal = normal;
  }
  Vertex.prototype.unaryMinus = function () {
    return new Vertex(this.pos, this.normal.unaryMinus());
  };
  Vertex.prototype.interpolate_epc5fv$ = function (other, t) {
    return new Vertex(this.pos.interpolate_1dsxm4$(other.pos, t), this.normal.interpolate_1dsxm4$(other.normal, t));
  };
  Vertex.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Vertex',
    interfaces: []
  };
  Vertex.prototype.component1 = function () {
    return this.pos;
  };
  Vertex.prototype.component2 = function () {
    return this.normal;
  };
  Vertex.prototype.copy_pcjqg8$ = function (pos, normal) {
    return new Vertex(pos === void 0 ? this.pos : pos, normal === void 0 ? this.normal : normal);
  };
  Vertex.prototype.toString = function () {
    return 'Vertex(pos=' + Kotlin.toString(this.pos) + (', normal=' + Kotlin.toString(this.normal)) + ')';
  };
  Vertex.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.pos) | 0;
    result = result * 31 + Kotlin.hashCode(this.normal) | 0;
    return result;
  };
  Vertex.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.pos, other.pos) && Kotlin.equals(this.normal, other.normal)))));
  };
  function outline$isBlack(closure$image, closure$classifier) {
    return function (x, y) {
      return x < 0 || x >= closure$image.width || y < 0 || y >= closure$image.height ? false : closure$classifier(closure$image.get_vux9f0$(x, y));
    };
  }
  function outline$isBlack_0(closure$isBlack) {
    return function (p) {
      return closure$isBlack(p.x, p.y);
    };
  }
  function outline$add(closure$optimize, closure$edges) {
    return function (p) {
      if (closure$optimize && closure$edges.size >= 2 && p.isOneLine_q9k8ss$(closure$edges.get_za3lpa$(closure$edges.size - 1 | 0), closure$edges.get_za3lpa$(closure$edges.size - 2 | 0)))
        closure$edges.set_wxm5ur$(closure$edges.size - 1 | 0, p);
      else {
        closure$edges.add_11rb$(p);
      }
    };
  }
  function outline(image, optimize, classifier) {
    if (optimize === void 0)
      optimize = true;
    var tmp$;
    var isBlack = outline$isBlack(image, classifier);
    var isBlack_0 = outline$isBlack_0(isBlack);
    var edges = ArrayList_init_0();
    var add = outline$add(optimize, edges);
    tmp$ = DirPoint$Companion_getInstance().findStart_fdu5j5$(image.width, image.height, getCallableRef('isBlack', function (x, y) {
      return isBlack(x, y);
    }));
    if (tmp$ == null) {
      throw IllegalArgumentException_init('No black pixel found.');
    }
    var start = tmp$.findNext_tjd9da$(getCallableRef('isBlack', function (p) {
      return isBlack_0(p);
    }));
    var point = start;
    do {
      add(point.pos());
      point = point.findNext_tjd9da$(getCallableRef('isBlack', function (p) {
        return isBlack_0(p);
      }));
    }
     while (!(point != null ? point.equals(start) : null));
    return edges;
  }
  function Point(x, y) {
    this.x = x;
    this.y = y;
  }
  Point.prototype.isOneLine_q9k8ss$ = function (p1, p2) {
    return get_sign(p1.x - this.x | 0) === get_sign(p2.x - p1.x | 0) && get_sign(p1.y - this.y | 0) === get_sign(p2.y - p1.y | 0);
  };
  Point.prototype.toVector = function () {
    return new Vector(this.x, this.y, 0.0);
  };
  Point.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Point',
    interfaces: []
  };
  Point.prototype.component1 = function () {
    return this.x;
  };
  Point.prototype.component2 = function () {
    return this.y;
  };
  Point.prototype.copy_vux9f0$ = function (x, y) {
    return new Point(x === void 0 ? this.x : x, y === void 0 ? this.y : y);
  };
  Point.prototype.toString = function () {
    return 'Point(x=' + Kotlin.toString(this.x) + (', y=' + Kotlin.toString(this.y)) + ')';
  };
  Point.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.x) | 0;
    result = result * 31 + Kotlin.hashCode(this.y) | 0;
    return result;
  };
  Point.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.x, other.x) && Kotlin.equals(this.y, other.y)))));
  };
  function DirPoint(dx, dy, x, y) {
    DirPoint$Companion_getInstance();
    this.dx = dx;
    this.dy = dy;
    this.x = x;
    this.y = y;
  }
  function DirPoint$Companion() {
    DirPoint$Companion_instance = this;
  }
  function DirPoint$Companion$findStart$hasBlackNeighbour(closure$isBlack) {
    return function (x, y) {
      var tmp$, tmp$_0;
      tmp$ = x + 1 | 0;
      for (var i = x - 1 | 0; i <= tmp$; i++) {
        tmp$_0 = y + 1 | 0;
        for (var j = y - 1 | 0; j <= tmp$_0; j++) {
          if ((i !== x || j !== y) && closure$isBlack(i, j))
            return true;
        }
      }
      return false;
    };
  }
  DirPoint$Companion.prototype.findStart_fdu5j5$ = function (width, height, isBlack) {
    var hasBlackNeighbour = DirPoint$Companion$findStart$hasBlackNeighbour(isBlack);
    for (var x = 0; x < width; x++) {
      for (var y = 0; y < height; y++) {
        if (isBlack(x, y) && hasBlackNeighbour(x, y)) {
          return new DirPoint(-1, 0, x, y);
        }
      }
    }
    return null;
  };
  DirPoint$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var DirPoint$Companion_instance = null;
  function DirPoint$Companion_getInstance() {
    if (DirPoint$Companion_instance === null) {
      new DirPoint$Companion();
    }
    return DirPoint$Companion_instance;
  }
  DirPoint.prototype.pos = function () {
    return new Point(this.x, this.y);
  };
  DirPoint.prototype.findNext_tjd9da$ = function (isBlack) {
    var dp = this;
    while (!isBlack(dp.target_0()))
      dp = dp.rot_0();
    return dp.next();
  };
  DirPoint.prototype.rot_0 = function () {
    return new DirPoint((this.dx + this.dy | 0) > 0 ? 1 : (this.dx + this.dy | 0) < 0 ? -1 : 0, (this.dx - this.dy | 0) > 0 ? -1 : (this.dx - this.dy | 0) < 0 ? 1 : 0, this.x, this.y);
  };
  DirPoint.prototype.target_0 = function () {
    return new Point(this.x + this.dx | 0, this.y + this.dy | 0);
  };
  DirPoint.prototype.next = function () {
    return new DirPoint(this.dx !== -1 && this.dy === 1 ? -1 : this.dx !== 1 && this.dy === -1 ? 1 : 0, this.dx === -1 && this.dy !== -1 ? -1 : this.dx === 1 && this.dy !== 1 ? 1 : 0, this.x + this.dx | 0, this.y + this.dy | 0);
  };
  DirPoint.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'DirPoint',
    interfaces: []
  };
  DirPoint.prototype.component1 = function () {
    return this.dx;
  };
  DirPoint.prototype.component2 = function () {
    return this.dy;
  };
  DirPoint.prototype.component3 = function () {
    return this.x;
  };
  DirPoint.prototype.component4 = function () {
    return this.y;
  };
  DirPoint.prototype.copy_tjonv8$ = function (dx, dy, x, y) {
    return new DirPoint(dx === void 0 ? this.dx : dx, dy === void 0 ? this.dy : dy, x === void 0 ? this.x : x, y === void 0 ? this.y : y);
  };
  DirPoint.prototype.toString = function () {
    return 'DirPoint(dx=' + Kotlin.toString(this.dx) + (', dy=' + Kotlin.toString(this.dy)) + (', x=' + Kotlin.toString(this.x)) + (', y=' + Kotlin.toString(this.y)) + ')';
  };
  DirPoint.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.dx) | 0;
    result = result * 31 + Kotlin.hashCode(this.dy) | 0;
    result = result * 31 + Kotlin.hashCode(this.x) | 0;
    result = result * 31 + Kotlin.hashCode(this.y) | 0;
    return result;
  };
  DirPoint.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.dx, other.dx) && Kotlin.equals(this.dy, other.dy) && Kotlin.equals(this.x, other.x) && Kotlin.equals(this.y, other.y)))));
  };
  function simplify($receiver, epsilon) {
    return simplifyPolygon($receiver, epsilon);
  }
  function simplifyPolygon$dist(a, b, c) {
    var tmp$ = abs(Kotlin.imul(c.y - a.y | 0, b.x) - Kotlin.imul(c.x - a.x | 0, b.y) + Kotlin.imul(c.x, a.y) - Kotlin.imul(c.y, a.x) | 0);
    var x = (c.x - a.x | 0) * (c.x - a.x | 0) + Kotlin.imul(c.y - a.y | 0, c.y - a.y | 0);
    return tmp$ / Math_0.sqrt(x);
  }
  function simplifyPolygon(points, epsilon) {
    var tmp$, tmp$_0;
    var dist = simplifyPolygon$dist;
    var maxI = 0;
    var maxD = 0.0;
    tmp$ = points.size - 2 | 0;
    for (var i = 1; i <= tmp$; i++) {
      var d = dist(first(points), points.get_za3lpa$(i), last(points));
      if (d > maxD) {
        maxD = d;
        maxI = i;
      }
    }
    if (maxD < epsilon)
      tmp$_0 = listOf([first(points), last(points)]);
    else {
      var s1 = simplifyPolygon(points.subList_vux9f0$(0, maxI + 1 | 0), epsilon);
      var s2 = simplifyPolygon(points.subList_vux9f0$(maxI, points.size), epsilon);
      tmp$_0 = plus(s1.subList_vux9f0$(0, s1.size - 1 | 0), s2);
    }
    var res = tmp$_0;
    return res;
  }
  function StlBinaryWriter(buf) {
    StlBinaryWriter$Companion_getInstance();
    this.buf = buf;
    this.bytes_0 = new DataView(this.buf);
    this.pos_0 = 84;
    this.bytes_0.setInt32(80, (this.buf.byteLength - 84 | 0) / 50 | 0, true);
  }
  function StlBinaryWriter$Companion() {
    StlBinaryWriter$Companion_instance = this;
  }
  StlBinaryWriter$Companion.prototype.write = function (m) {
    var $receiver = StlBinaryWriter_init(m);
    $receiver.writeModel_5yfbw6$(m);
    return $receiver.buf;
  };
  StlBinaryWriter$Companion.prototype.triangles_1 = function (m) {
    var tmp$;
    var sum = 0;
    tmp$ = m.csgs_8be2vx$.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      sum = sum + this.triangles_0(element) | 0;
    }
    return sum;
  };
  StlBinaryWriter$Companion.prototype.triangles_0 = function (csg) {
    var tmp$;
    var sum = 0;
    tmp$ = csg.polygons.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      sum = sum + this.triangles_2(element) | 0;
    }
    return sum;
  };
  StlBinaryWriter$Companion.prototype.triangles_2 = function (p) {
    return p.vertices.size - 2 | 0;
  };
  StlBinaryWriter$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var StlBinaryWriter$Companion_instance = null;
  function StlBinaryWriter$Companion_getInstance() {
    if (StlBinaryWriter$Companion_instance === null) {
      new StlBinaryWriter$Companion();
    }
    return StlBinaryWriter$Companion_instance;
  }
  StlBinaryWriter.prototype.writeModel_5yfbw6$ = function (m) {
    var tmp$;
    tmp$ = m.csgs_8be2vx$.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      this.writeCsg_ehrgfc$(element);
    }
  };
  StlBinaryWriter.prototype.writeCsg_ehrgfc$ = function (csg) {
    this.writePolygons_1k44c0$(csg.polygons);
  };
  StlBinaryWriter.prototype.writePolygons_1k44c0$ = function (ps) {
    var tmp$;
    tmp$ = ps.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      this.writePolygon_2npujf$(element);
    }
  };
  StlBinaryWriter.prototype.writePolygon_2npujf$ = function (p) {
    if (p.vertices.size > 3)
      this.writePolygons_1k44c0$(p.toTriangles());
    else {
      this.writeTriangle_q1m4lu$(p.vertices.get_za3lpa$(0).pos, p.vertices.get_za3lpa$(1).pos, p.vertices.get_za3lpa$(2).pos);
    }
  };
  StlBinaryWriter.prototype.writeTriangle_q1m4lu$ = function (a, b, c) {
    this.writeVector_0(origin);
    this.writeVector_0(a);
    this.writeVector_0(b);
    this.writeVector_0(c);
    this.pos_0 = this.pos_0 + 2 | 0;
  };
  StlBinaryWriter.prototype.writeVector_0 = function (a) {
    this.writeFloat_0(a.x);
    this.writeFloat_0(a.y);
    this.writeFloat_0(a.z);
  };
  StlBinaryWriter.prototype.writeFloat_0 = function (v) {
    this.bytes_0.setFloat32(this.pos_0, v, true);
    this.pos_0 = this.pos_0 + 4 | 0;
  };
  StlBinaryWriter.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'StlBinaryWriter',
    interfaces: []
  };
  function StlBinaryWriter_init(m, $this) {
    $this = $this || Object.create(StlBinaryWriter.prototype);
    StlBinaryWriter.call($this, new ArrayBuffer(84 + (50 * StlBinaryWriter$Companion_getInstance().triangles_1(m) | 0) | 0));
    return $this;
  }
  function writeBinaryStl($receiver) {
    return StlBinaryWriter$Companion_getInstance().write($receiver);
  }
  function Image(canvas) {
    this.canvas_0 = canvas;
    var tmp$;
    this.ctx_0 = Kotlin.isType(tmp$ = this.canvas_0.getContext('2d'), CanvasRenderingContext2D) ? tmp$ : throwCCE();
    this.data_0 = null;
    this.px_0 = this.ctx_0.createImageData(1.0, 1.0);
  }
  Image.prototype.flush = function () {
    this.data_0 = this.ctx_0.getImageData(0.0, 0.0, this.canvas_0.width, this.canvas_0.height);
  };
  Object.defineProperty(Image.prototype, 'width', {
    get: function () {
      return this.canvas_0.width;
    }
  });
  Object.defineProperty(Image.prototype, 'height', {
    get: function () {
      return this.canvas_0.height;
    }
  });
  Image.prototype.get_vux9f0$ = function (x, y) {
    if (this.data_0 == null)
      this.flush();
    var i = Kotlin.imul(y, this.width * 4 | 0) + (x * 4 | 0) | 0;
    return this.raw_0(i + 3 | 0) << 24 | this.raw_0(i) << 16 | this.raw_0(i + 1 | 0) << 8 | this.raw_0(i + 3 | 0);
  };
  Image.prototype.set_qt1dr2$ = function (x, y, rgb) {
    var d = this.px_0.data;
    d[0] = rgb >> 16 & 255;
    d[1] = rgb >> 8 & 255;
    d[2] = rgb >> 0 & 255;
    d[3] = rgb >> 24 & 255;
    this.ctx_0.putImageData(this.px_0, x, y);
  };
  Image.prototype.line_4qozqa$ = function (x0, y0, x1, y1, rgb) {
    this.ctx_0.strokeStyle = 'rgba(' + (rgb >> 16 & 255) + ',' + (rgb >> 8 & 255) + ',' + (rgb >> 0 & 255) + ',' + (rgb >> 24 & 255) / 255.0 + ')';
    this.ctx_0.moveTo(x0, y0);
    this.ctx_0.lineTo(x1, y1);
    this.ctx_0.stroke();
  };
  Image.prototype.raw_0 = function (i) {
    return ensureNotNull(this.data_0).data[i];
  };
  Image.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Image',
    interfaces: []
  };
  var package$guru = _.guru || (_.guru = {});
  var package$nidi = package$guru.nidi || (package$guru.nidi = {});
  var package$simple3d = package$nidi.simple3d || (package$nidi.simple3d = {});
  var package$example = package$simple3d.example || (package$simple3d.example = {});
  package$example.createForm = createForm;
  var package$model = package$simple3d.model || (package$simple3d.model = {});
  package$model.AffineTransform_init = AffineTransform_init;
  package$model.AffineTransform = AffineTransform;
  package$model.translate_wk194y$ = translate;
  package$model.scale_wk194y$ = scale;
  package$model.rotateX_14dthe$ = rotateX;
  package$model.rotateY_14dthe$ = rotateY;
  package$model.rotateZ_14dthe$ = rotateZ;
  package$model.PolygonSink = PolygonSink;
  Object.defineProperty(Csg, 'Companion', {
    get: Csg$Companion_getInstance
  });
  package$model.Csg_init_1k44c0$ = Csg_init;
  package$model.Csg_init_n3rvgf$ = Csg_init_0;
  package$model.Csg = Csg;
  package$model.Box = Box;
  package$model.Model = Model;
  package$model.model = model;
  package$model.deg_s8ev3n$ = deg;
  package$model.deg_yrwdxr$ = deg_0;
  package$model.rad_yrwdxr$ = rad;
  package$model.invoke_yzln2o$ = invoke;
  package$model.Node_init = Node_init;
  package$model.Node_init_1k44c0$ = Node_init_0;
  package$model.Node = Node;
  package$model.cube_pcjqg8$ = cube;
  package$model.prism_z8nq7y$ = prism;
  package$model.prism_z4imyc$ = prism_0;
  package$model.prismRing_35ibys$ = prismRing;
  package$model.prismRing = prismRing_0;
  package$model.prismRing_2de4dk$ = prismRing_1;
  package$model.sphere_q7h779$ = sphere;
  package$model.cylinder_uob481$ = cylinder;
  package$model.ring_i68h6$ = ring;
  Object.defineProperty(Plane, 'Companion', {
    get: Plane$Companion_getInstance
  });
  package$model.Plane = Plane;
  Object.defineProperty(Polygon, 'Companion', {
    get: Polygon$Companion_getInstance
  });
  package$model.Polygon_init_w94jq8$ = Polygon_init;
  package$model.Polygon_init_1z2e3i$ = Polygon_init_0;
  package$model.Polygon_init_l1jrzk$ = Polygon_init_1;
  package$model.Polygon = Polygon;
  Object.defineProperty(Vector, 'Companion', {
    get: Vector$Companion_getInstance
  });
  package$model.Vector = Vector;
  package$model.times_nq6crv$ = times;
  package$model.times_ak8s1d$ = times_0;
  Object.defineProperty(package$model, 'origin', {
    get: function () {
      return origin;
    }
  });
  Object.defineProperty(package$model, 'unit', {
    get: function () {
      return unit;
    }
  });
  Object.defineProperty(package$model, 'xUnit', {
    get: function () {
      return xUnit;
    }
  });
  Object.defineProperty(package$model, 'yUnit', {
    get: function () {
      return yUnit;
    }
  });
  Object.defineProperty(package$model, 'zUnit', {
    get: function () {
      return zUnit;
    }
  });
  package$model.v = v;
  package$model.v_w4xg1m$ = v_0;
  package$model.v_o4m142$ = v_1;
  package$model.v_syxxoe$ = v_2;
  package$model.v_224j3y$ = v_3;
  package$model.v_6wgfoa$ = v_4;
  package$model.v_mqu1mq$ = v_5;
  package$model.v_qt1dr2$ = v_6;
  package$model.Vertex = Vertex;
  var package$vectorize = package$simple3d.vectorize || (package$simple3d.vectorize = {});
  package$vectorize.outline = outline;
  package$vectorize.Point = Point;
  Object.defineProperty(DirPoint, 'Companion', {
    get: DirPoint$Companion_getInstance
  });
  package$vectorize.DirPoint = DirPoint;
  package$vectorize.simplify_bq76rl$ = simplify;
  package$vectorize.simplifyPolygon = simplifyPolygon;
  Object.defineProperty(StlBinaryWriter, 'Companion', {
    get: StlBinaryWriter$Companion_getInstance
  });
  var package$io = package$simple3d.io || (package$simple3d.io = {});
  package$io.StlBinaryWriter_init_5yfbw6$ = StlBinaryWriter_init;
  package$io.StlBinaryWriter = StlBinaryWriter;
  package$io.writeBinaryStl_sg4x9h$ = writeBinaryStl;
  package$vectorize.Image = Image;
  EPSILON = 1.0E-5;
  CONCAVE = -1;
  CONVEX = 1;
  origin = v_6(0, 0, 0);
  unit = v_6(1, 1, 1);
  xUnit = v_6(1, 0, 0);
  yUnit = v_6(0, 1, 0);
  zUnit = v_6(0, 0, 1);
  Kotlin.defineModule('simple3d', _);
  return _;
}(typeof simple3d === 'undefined' ? {} : simple3d, kotlin);
