package client

import container.{HttpDelegate, HttpRequest}
import org.scalatest.{Matchers, FlatSpec}
import json.LiftHttpResponse
import com.typesafe.config.ConfigFactory
import http.DispatchHttpDelegate
import rest.TsConnection

class TestTsBootstrapper extends FlatSpec with Matchers {

  "A query for List[TsConnection]" should "return a List or None" in {

    val config = ConfigFactory.load("TsCredentials.conf")

    val consumerKey = config.getString("ConsumerKey")
    val consumerSecret = config.getString("ConsumerSecret")

    val token = config.getString("Token")
    val tokenSecret = config.getString("TokenSecret")

    val tenantId = config.getString("TenantId")

    val httpDelegate: HttpDelegate = new DispatchHttpDelegate("https://sandbox.tradeshift.com/tradeshift/rest/external/")

    httpDelegate.sign(consumerKey, consumerSecret, token, tokenSecret)

    val req = new HttpRequest("network/connections/")
    req.addHeader("User-Agent", "tradesshift-external-api-scala/0.1")
    req.addHeader("Accept", "application/json")
    req.addHeader("X-Tradeshift-TenantId", tenantId)

    val res = new LiftHttpResponse[TsConnection](httpDelegate.doGet(req))

    res.create match {
      case (true, _) => {
        val list = TsBootstrapper.cast[List[TsConnection]]()

        (list) should be(List[TsConnection]())

      }
      case (false, _) => println("no")
    }


  }

}
