package client

import http._
import http.TSHttpRequest
import http.Country


object TsBootstrapper extends App {

  val BootstrapURL = "https://api.tradeshift.com/tradeshift/rest/"
  val SandboxBootstrapURL = "https://sandbox.tradeshift.com/tradeshift/rest/"


  // can the http delegate be injected?
  val httpDelegate: HttpDelegate = new DispatchHttpDelegate("http://api.hostip.info/")
  val req = new TSHttpRequest("country.php")


  val response = httpDelegate.doGet(req)

  val w = new HttpResponseWrapper[String](response)

  def cast[A <: AnyRef : Manifest](a: Any): A = manifest[A].runtimeClass.cast(a).asInstanceOf[A]

  val cx = cast[String](w.response)
  println("cs result: " + cx)

  val country = w.response.asInstanceOf[String]
  println("string result: " + country)

  val response2 = httpDelegate.doGet(req)

  val w2 = new HttpResponseWrapper[Country](response2)

  val ax = cast[Country](w2.response)
  println("ax result: " + ax.code)

  val a = w2.response.asInstanceOf[Country]
  println("country result: " + a.code)


  println("exiting successfully...")
}

class TsBootstrapper(useSandBox: Boolean = false) {

  val base = if (useSandBox) TsBootstrapper.SandboxBootstrapURL else TsBootstrapper.BootstrapURL


}


