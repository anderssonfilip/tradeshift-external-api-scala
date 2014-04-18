import json._
import http._

object Program extends App {

  val foo = new Foo()
  val bar = new Bar()

  println(foo.foo + bar.bar)
}