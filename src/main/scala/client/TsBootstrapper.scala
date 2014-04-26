package client

import rest._
import container._
import http._
import json.LiftHttpResponse
import com.typesafe.config.ConfigFactory
import rest.TsConnection
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
  val SandboxBootstrapURL = "https://sandbox.tradeshift.com/tradeshift/rest/external/"

  // can the http delegate be injected?
  val httpDelegate: HttpDelegate = new DispatchHttpDelegate(BootstrapURL)

  httpDelegate.sign(consumerKey, consumerSecret, token, tokenSecret)

  val connectionId = "5824e12e-e4aa-4c8d-97dc-f84703df9b73"
  //val req = new HttpRequest("network/connections/" + connectionId)
  val req = new HttpRequest("documents")
  req.addHeader("User-Agent", "tradeshift-external-api-scala/0.1")
  req.addHeader("Accept", "application/json")
  req.addHeader("X-Tradeshift-TenantId", tenantId)

  val response: Future[(Int, String)] = httpDelegate.doGet(req)
   val w = new LiftHttpResponse[List[TsDocument]](response)

  val result = cast[List[TsDocument]](w.create._2)

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


// userAgent: identifies the client and client version
class TsBootstrapper(useSandBox: Boolean = false, userAgent: String, tenant: TsConfig) {

  if (userAgent.isEmpty)
    throw new NoSuchElementException

  def cast[A <: AnyRef : Manifest](a: Any): A = manifest[A].runtimeClass.cast(a).asInstanceOf[A]

  val base = "https://api.tradeshift.com/tradeshift/rest/external/"

  val httpDelegate: HttpDelegate = new DispatchHttpDelegate(base)
  httpDelegate.sign(tenant.consumerKey, tenant.consumerSecret, tenant.token, tenant.tokenSecret)

  def loadConnections: List[TsConnection] = {

    val req = new HttpRequest("network/connections")
    req.addHeader("User-Agent", userAgent)
    req.addHeader("Accept", "application/json")
    req.addHeader("X-Tradeshift-TenantId", tenant.tenantId)

    val res = new LiftHttpResponse[List[TsConnection]](httpDelegate.doGet(req))

    res.create match {
      case (true, list) => cast[List[TsConnection]](res.create._2)
      case (false, list) => List[TsConnection]()
    }
  }

  def loadDocuments: List[TsDocument] = {

    val req = new HttpRequest("documents")
    req.addHeader("User-Agent", userAgent)
    req.addHeader("Accept", "application/json")
    req.addHeader("X-Tradeshift-TenantId", tenant.tenantId)

    val res = new LiftHttpResponse[List[TsDocument]](httpDelegate.doGet(req))

    res.create match {
      case (true, list) => cast[List[TsDocument]](res.create._2)
      case (false, list) => List[TsDocument]()
    }
  }
}


