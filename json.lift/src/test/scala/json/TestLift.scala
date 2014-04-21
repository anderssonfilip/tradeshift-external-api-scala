import container.TsAddress
import java.text.SimpleDateFormat
import org.scalatest._

import net.liftweb.json._

class TestLift extends FlatSpec with Matchers {

  "A Lift Ast" should "contain numbers 1, 2, and 3" in {

    val ast = parse( """ { "numbers" : [1, 2, 3] } """)

    ((ast \ "numbers")(0)) should be(JInt(1))
    ((ast \ "numbers")(1)) should be(JInt(2))
    ((ast \ "numbers")(2)) should be(JInt(3))

  }

  "A valid json string" should "parse to TsAccountInfo" in {

    val accountInfo = parse( """ {
      "CompanyName" : "tradespoke",
      "Country" : "US",
      "CompanyAccountId" : "e7c3ac16-d38d-4a45-8130-0d34227e2c30",
      "Identifiers" : [ {
      "scheme" : "TS:ID",
      "value" : "e7c3ac16-d38d-4a45-8130-0d34227e2c30"
    } ],
      "AddressLines" : [ {
      "scheme" : "street",
      "value" : "Goh Chuan St"
    }, {
      "scheme" : "zip",
      "value" : "53213"
    }, {
      "scheme" : "city",
      "value" : "Singapore"
    }, {
      "scheme" : "state",
      "value" : "SG"
    }, {
      "scheme" : "buildingnumber",
      "value" : "76"
    },
    {
      "scheme" : "street",
      "value" : "Goh Chuan St"
    }, {
      "scheme" : "zip",
      "value" : "53213"
    }, {
      "scheme" : "city",
      "value" : "Singapore"
    }, {
      "scheme" : "state",
      "value" : "SG"
    }, {
      "scheme" : "buildingnumber",
      "value" : "77"
    }
    ],
      "AcceptingDocumentProfiles" : [ ],
      "LookingFor" : [ ],
      "Offering" : [ ],
      "PublicProfile" : false,
      "Created" : "2014-04-04T23:16:31.499Z"
    }
                             """)

    (accountInfo \\ "CompanyName") should be(JString("tradespoke"))
    (accountInfo \\ "Country") should be(JString("US"))

    val b = for {JField("AddressLines", addr) <- accountInfo
                 JField("value", JString(v)) <- addr
    } yield v

    val addresses = for (i <- 0 until b.length by 5)
    yield TsAddress(b(i), b(i + 1), b(i + 2), b(i + 3), b(i + 4))

    (addresses.head) should be(TsAddress("Goh Chuan St", "53213", "Singapore", "SG", "76"))
    (addresses.tail.head) should be(TsAddress("Goh Chuan St", "53213", "Singapore", "SG", "77"))
  }

  implicit val formats = new DefaultFormats {
    override def dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z")
  }
}