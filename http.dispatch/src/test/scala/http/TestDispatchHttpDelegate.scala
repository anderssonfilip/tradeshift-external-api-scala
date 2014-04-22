package http

import container.HttpRequest
import org.scalatest.{Matchers, FlatSpec}
import scala.concurrent.{Await, Future}
import com.typesafe.config.ConfigFactory
import scala.util.{Failure, Success}
import scala.concurrent.duration._
import sun.reflect.generics.reflectiveObjects.NotImplementedException


class TestDispatchHttpDelegate extends FlatSpec with Matchers {

  val config = ConfigFactory.load("TsCredentials.conf")
  val consumerKey = config.getString("ConsumerKey")
  val consumerSecret = config.getString("ConsumerSecret")
  val token = config.getString("Token")
  val tokenSecret = config.getString("TokenSecret")
  val tenantId = config.getString("TenantId")

  "A a delete document for random documentId" should "return HTTP 404" in {

    val httpDelegate = new DispatchHttpDelegate("https://sandbox.tradeshift.com/tradeshift/rest/")

    httpDelegate.sign(consumerKey, consumerSecret, token, tokenSecret)

    val req = new HttpRequest("documents/" + java.util.UUID.randomUUID.toString)
    req.addHeader("User-Agent", "tradesshift-external-api-scala/0.1")
    req.addHeader("Accept", "application/json")
    req.addHeader("X-Tradeshift-TenantId", tenantId)

    val response: Future[Int] = httpDelegate.doDelete(req)

    Await.ready(response, 5 seconds)
    response.value match {
      case Some(res) => {
        res match {
          case Success(s) => (s) should be(404)
          case Failure(f) => assert(false, f)
        }
      }
      case None => assert(false)
    }
  }


  "A a delete document for valid documentId" should "return HTTP 200" in {

    throw new NotImplementedException()

  }
}
