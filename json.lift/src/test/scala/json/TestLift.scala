import org.scalatest._

import net.liftweb.json._

class TestLift extends FlatSpec with Matchers {

  "A Lift Ast" should "contain numbers 1, 2, and 3" in {

    val ast = parse( """ { "numbers" : [1, 2, 3] } """)

    ((ast \ "numbers")(0)) should be(JInt(1))
    ((ast \ "numbers")(1)) should be(JInt(2))
    ((ast \ "numbers")(2)) should be(JInt(3))

  }
}