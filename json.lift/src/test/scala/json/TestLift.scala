import container.{TsDocumentInfo, TsAddress}
import java.text.SimpleDateFormat
import json.LiftHttpResponse
import org.scalatest._

import net.liftweb.json._
import scala.concurrent.Future

class TestLift extends FlatSpec with Matchers {

  def cast[A <: AnyRef : Manifest](a: Any): A = manifest[A].runtimeClass.cast(a).asInstanceOf[A]

  import scala.concurrent.ExecutionContext.Implicits.global

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

  "A valid json string" should "parse to List[TsDocument]" in {

    val json = """{"itemsPerPage" : 25,
    "itemCount" : 2,
    "indexing" : false,
    "numPages" : 1,
    "pageId" : 0,
    "Document" : [ {
    "DocumentId" : "dba6c462-70d8-4a3d-b79a-08b7c3ea4a7b",
    "ID" : "abc123",
    "URI" : "https://api.tradeshift.com/tradeshift/rest/external/documents/dba6c462-70d8-4a3d-b79a-08b7c3ea4a7b",
    "DocumentType" : {
      "mimeType" : "text/xml",
      "documentProfileId" : "ubl.invoice.2.1.us",
      "type" : "invoice"
    },
    "State" : "LOCKED",
    "LastEdit" : "2014-04-19T00:37:35.164Z",
    "ReceiverCompanyName" : "Overstock",
    "Tags" : {
      "Tag" : [ ]
    },
    "ItemInfos" : [ {
      "type" : "document.description",
      "value" : "test"
    }, {
      "type" : "document.total",
      "value" : "1074.92"
    }, {
      "type" : "document.currency",
      "value" : "USD"
    }, {
      "type" : "document.issuedate",
      "value" : "2014-04-19"
    } ],
    "LatestDispatch" : {
      "DispatchId" : "c66dd894-a769-4ba5-b27f-7edd22a98447",
      "ObjectId" : "dba6c462-70d8-4a3d-b79a-08b7c3ea4a7b",
      "Created" : "2014-04-19T00:37:41.496Z",
      "SenderUserId" : "e479651d-ac6b-4b75-8f6d-13d506d12514",
      "DispatchState" : "COMPLETED",
      "LastStateChange" : "2014-04-19T00:37:41.496Z",
      "ReceiverConnectionId" : "5824e12e-e4aa-4c8d-97dc-f84703df9b73",
      "DispatchChannel" : "TRADESHIFT"
    },
    "SentReceivedTimestamp" : "2014-04-19T00:37:38.972Z",
    "ProcessState" : "PENDING",
    "ConversationStates" : [ {
      "Axis" : "PROCESS",
      "State" : "PENDING",
      "Timestamp" : "2014-04-19T00:37:36.550Z"
    }, {
      "Axis" : "OTHERPART",
      "State" : "OTHER_PENDING",
      "Timestamp" : "2014-04-19T00:37:41.547Z"
    }, {
      "Axis" : "DELIVERY",
      "State" : "SENT",
      "Timestamp" : "2014-04-19T00:37:41.552Z"
    } ],
    "UnifiedState" : "DELIVERED",
    "Properties" : [ {
      "scheme" : "emailBody",
      "value" : ""
    }, {
      "scheme" : "emailSubject",
      "value" : ""
    } ]
  }, {
    "DocumentId" : "c92268ac-00c2-47ff-8ef8-4a1fb2cfeed7",
    "ID" : "TEST INVOICE 1",
    "URI" : "https://api.tradeshift.com/tradeshift/rest/external/documents/c92268ac-00c2-47ff-8ef8-4a1fb2cfeed7",
    "DocumentType" : {
      "mimeType" : "text/xml",
      "documentProfileId" : "ubl.invoice.2.1.us",
      "type" : "invoice"
    },
    "State" : "LOCKED",
    "LastEdit" : "2014-04-04T23:18:26.692Z",
    "ReceiverCompanyName" : "Test Invoice receiver",
    "Tags" : {
      "Tag" : [ "testdocument" ]
    },
    "ItemInfos" : [ {
      "type" : "document.description",
      "value" : "my good"
    }, {
      "type" : "document.total",
      "value" : "322.80"
    }, {
      "type" : "document.currency",
      "value" : "USD"
    }, {
      "type" : "document.issuedate",
      "value" : "2014-04-04"
    }, {
      "type" : "invoice.due",
      "value" : "2014-04-04"
    } ],
    "LatestDispatch" : {
      "DispatchId" : "2bb78e9b-26ad-4211-9444-b00d485b7441",
      "ObjectId" : "c92268ac-00c2-47ff-8ef8-4a1fb2cfeed7",
      "Created" : "2014-04-04T23:18:59.980Z",
      "SenderUserId" : "e479651d-ac6b-4b75-8f6d-13d506d12514",
      "DispatchState" : "COMPLETED",
      "LastStateChange" : "2014-04-04T23:18:59.980Z",
      "ReceiverConnectionId" : "ecc9bba8-e23b-449d-a938-680967ecd114",
      "DispatchChannel" : "EMAIL"
    },
    "SentReceivedTimestamp" : "2014-04-04T23:18:54.103Z",
    "ProcessState" : "PAID",
    "ConversationStates" : [ {
      "Axis" : "PROCESS",
      "State" : "PAID",
      "Timestamp" : "2014-04-14T23:36:06.155Z"
    }, {
      "Axis" : "DELIVERY",
      "State" : "SENT",
      "Timestamp" : "2014-04-04T23:19:00.366Z"
    } ],
    "UnifiedState" : "PAID_CONFIRMED",
    "Properties" : [ {
      "scheme" : "tags",
      "value" : "testdocument"
    }, {
      "scheme" : "emailBody",
      "value" : ""
    }, {
      "scheme" : "emailSubject",
      "value" : ""
    } ]
  } ]
  }"""

    val w = new LiftHttpResponse[List[TsDocumentInfo]](Future[(Int, String)]((200, json)))

    val documents = cast[List[TsDocumentInfo]](w.create)

    (documents.length) should be(2)
    (documents.tail.head.documentId) should be("c92268ac-00c2-47ff-8ef8-4a1fb2cfeed7")
    (documents.tail.head.id) should be("TEST INVOICE 1")
    (documents.tail.head.uri.drop(documents.tail.head.uri.lastIndexOf("/"))) should be("/c92268ac-00c2-47ff-8ef8-4a1fb2cfeed7")
  }

  implicit val formats = new DefaultFormats {
    override def dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z")
  }
}