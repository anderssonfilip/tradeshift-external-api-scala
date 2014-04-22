package json

import container._
import scala.concurrent.{Await, Future}
import scala.reflect.runtime.universe._
import net.liftweb.json._
import java.text.SimpleDateFormat
import scala.util.{Failure, Success}
import scala.concurrent.duration._

class LiftHttpResponse[T](res: Future[(Int, String)])(implicit man: Manifest[T]) extends HttpResponse[T](res: Future[(Int, String)]) {


  implicit val formats = new DefaultFormats {
    override def dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  }

  override def create: Any = {

    Await.ready(res, Duration.Inf)
    res.value match {
      case Some(res) => {
        res match {
          case Success(pair) => {
            if (typeOf[T] =:= typeOf[String]) {
              pair._2
            }
            else if (typeOf[T] =:= typeOf[TsAccountInfo]) {
              //JsonParser.parse(json._2).extract[TsAccountInfo]
              buildTsAccountInfo(pair._2)
            }
            else if (typeOf[T] =:= typeOf[List[TsDocumentInfo]]) {
              //JsonParser.parse(json._2).extract[TsAccountInfo]
              buildDocuments(pair._2)
            }
            else if (typeOf[T] =:= typeOf[TsConnectionDetail]) {
              buildConnectionDetail(pair._2)
            }
            else if (typeOf[T] =:= typeOf[List[TsConnection]]) {
              //JsonParser.parse(json._2).extract[TsAccountInfo]
              buildConnections(pair._2)
            }
            else
              None
          }
          case Failure(f) => println(f)
        }
      }
      case None => println("Empty response")
    }
  }


  // endpoint: network/connections/
  def buildConnections(connections: String): List[TsConnection] = {
    for {JField("Connection", doc) <- parse(connections)
         JObject(o) <- doc
         JField("ConnectionId", JString(a)) <- o
         JField("ConnectionType", JString(b)) <- o
         JField("FromCompanyAccountId", JString(c)) <- o
         JField("CompanyName", JString(d)) <- o
         JField("Country", JString(e)) <- o
         JField("Email", JString(g)) <- o
    } yield TsConnection(a, b, c, d, e, List(), g)
  }


  def buildConnectionDetail(json: String): TsConnectionDetail = {

    (parse(json) transform {
      case JField("ConnectionType", x) => JField("connectionType", x)
      case JField("ConnectionId", x) => JField("connectionId", x)
      case JField("CompanyName", x) => JField("companyName", x)
      case JField("Country", x) => JField("country", x)
      case JField("Identifiers", x) => JField("identifiers", x)
      case JField("DispatchChannelID", x) => JField("dispatchChannelId", x)
      case JField("Email", x) => JField("email", x)
    }).extract[TsConnectionDetail]
  }

  // endpoint: documents/
  def buildDocuments(documents: String): List[TsDocumentInfo] = {

    for {JField("Document", doc) <- parse(documents)
         JObject(o) <- doc
         JField("DocumentId", JString(documentId)) <- o
         JField("ID", JString(id)) <- o
         JField("URI", JString(uri)) <- o
    } yield TsDocumentInfo(documentId, id, uri)
  }

  def buildTsAccountInfo(accountInfo: String): TsAccountInfo = {

    val p = (for {
      JObject(json) <- parse(accountInfo)
      JField("CompanyName", JString(companyName)) <- json
      JField("Country", JString(country)) <- json
      JField("CompanyAccountId", JString(companyAccountId)) <- json
      JField("PublicProfile", JBool(publicProfile)) <- json
      JField("Created", created) <- json
    } yield (companyName, country, companyAccountId, publicProfile, created.extract[java.util.Date])).head

    val b = for {JField("AddressLines", addr) <- parse(accountInfo)
                 JField("value", JString(v)) <- addr
    } yield v

    val addresses = for (i <- 0 until b.length by 5)
    yield TsAddress(b(i), b(i + 1), b(i + 2), b(i + 3), b(i + 4))

    TsAccountInfo(p._1,
      p._2,
      p._3,
      Map(),
      addresses.toList,
      List(),
      List(),
      List(),
      p._4,
      p._5)
  }
}


