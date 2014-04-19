package http

import dispatch._, Defaults._
import com.ning.http.client.Response

class Country(f: Future[String]) {

  def code: String = f()
}

class DispatchHttpDelegate(base: String) extends HttpDelegate {

  override def doGet(request: TSHttpRequest): Future[(Int, String)] = {

    val svc = url(base + request.path).GET
    split(Http(svc))
  }

  override def doPost(request: TSHttpRequest): Future[(Int, String)] = {

    val svc = url(base + request.path).POST
    split(Http(svc))
  }

  private def split(svc: Future[Response]) = svc.map {
    response => (response.getStatusCode, response.getResponseBody)
  }

}
