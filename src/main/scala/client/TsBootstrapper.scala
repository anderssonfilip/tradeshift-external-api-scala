package client

import container._
import http._
import json.LiftHttpResponse
import com.typesafe.config.ConfigFactory
import scala.concurrent.Future


object TsBootstrapper extends App {

  def cast[A <: AnyRef : Manifest](a: Any): A = manifest[A].runtimeClass.cast(a).asInstanceOf[A]

  val config = ConfigFactory.load("TsCredentials.conf")

  val consumerKey = config.getString("ConsumerKey")
  val consumerSecret = config.getString("ConsumerSecret")

  val token = config.getString("Token")
  val tokenSecret = config.getString("TokenSecret")

  val tenantId = config.getString("TenantId")

  val BootstrapURL = "https://api.tradeshift.com/tradeshift/rest/external/"
  val SandboxBootstrapURL = "https://sandbox.tradeshift.com/tradeshift/rest/"

  // can the http delegate be injected?
  val httpDelegate: HttpDelegate = new DispatchHttpDelegate(BootstrapURL) //"http://api.hostip.info/")

  httpDelegate.sign(consumerKey, consumerSecret, token, tokenSecret)


  val connectionId = "5824e12e-e4aa-4c8d-97dc-f84703df9b73"
  val req = new HttpRequest("network/connections/" + connectionId)
  req.addHeader("User-Agent", "tradesshift-external-api-scala/0.1")
  req.addHeader("Accept", "application/json")
  req.addHeader("X-Tradeshift-TenantId", tenantId)

  val response: Future[(Int, String)] = httpDelegate.doGet(req)
  val w = new LiftHttpResponse[TsConnectionDetail](response)

  val result = cast[TsConnectionDetail](w.create)

  println(result)

  /*
    val req = new HttpRequest("account/info")
    req.addHeader("User-Agent", "tradesshift-external-api-scala/0.1")
    req.addHeader("Accept", "application/json")
    req.addHeader("X-Tradeshift-TenantId", tenantId)

    val response: Future[(Int, String)] = httpDelegate.doGet(req)
    val w: HttpResponse[TsAccountInfo] = new LiftHttpResponse[TsAccountInfo](response)

    val accountInfo = cast[TsAccountInfo](w.create)

    println(accountInfo)*/

  /*
    val cx = cast[String](w.response)
    println("cs result: " + cx)

    val country = w.response.asInstanceOf[String]
    println("string result: " + country)

    val response2 = httpDelegate.doGet(req)

    val w2 = new HttpResponseWrapper[Country](response2)

    val ax = cast[Country](w2.response)
    println("ax result: " + ax.code)

    val a = w2.response.asInstanceOf[Country]
    println("country result: " + a.code)*/


  println("exiting successfully...")
}

class TsBootstrapper(useSandBox: Boolean = false) {


  val base = if (useSandBox) TsBootstrapper.SandboxBootstrapURL else TsBootstrapper.BootstrapURL


}


