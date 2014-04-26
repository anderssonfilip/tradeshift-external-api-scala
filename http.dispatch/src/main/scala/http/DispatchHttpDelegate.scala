package http

import dispatch._, Defaults._
import dispatch.oauth._
import com.ning.http.client.oauth.{RequestToken, ConsumerKey}

import container._
import com.ning.http.client.Response


class DispatchHttpDelegate(base: String) extends HttpDelegate {

  override def doDelete(request: HttpRequest): Future[Int] = {

    val svc = url(base + request.path).DELETE

    //println((prep(svc, request)).toRequest.toString)

    Http(prep(svc, request)).map(r => r.getStatusCode)
  }

  override def doGet(request: HttpRequest): Future[(Int, String)] = {

    val svc = url(base + request.path).GET

    //println((prep(svc, request)).toRequest.toString)

    split(Http(prep(svc, request)))
  }

  override def doPost(request: HttpRequest): Future[(Int, String)] = {

    val svc = url(base + request.path).POST
    split(Http(svc))
  }

  // does not actually sign, just keeps the keys/tokens for later use when creating request
  override def sign(consumerKey: String, consumerSecret: String, token: String, tokenSecret: String) {

    _consumerKey = if (consumerKey.isEmpty) None else Some(consumerKey)
    _consumerSecret = if (consumerKey.isEmpty) None else Some(consumerSecret)
    _token = if (token.isEmpty) None else Some(token)
    _tokenSecret = if (tokenSecret.isEmpty) None else Some(tokenSecret)
  }

  def consumerKey = _consumerKey.getOrElse(throw new SecurityException("Empty OAuth parameter"))

  def consumerSecret = _consumerSecret.getOrElse(throw new SecurityException("Empty OAuth parameter"))

  def token = _token.getOrElse(throw new SecurityException("Empty OAuth parameter"))

  def tokenSecret = _tokenSecret.getOrElse(throw new SecurityException("Empty OAuth parameter"))

  var _consumerKey: Option[String] = None
  var _consumerSecret: Option[String] = None
  var _token: Option[String] = None
  var _tokenSecret: Option[String] = None

  private def prep(svc: Req, request: HttpRequest): Req = {

    svc <:< request.headers <@(new ConsumerKey(consumerKey, consumerKey), new RequestToken(token, tokenSecret)) // << request.parameters
  }

  private def split(svc: Future[Response]) = svc.map {
    response => (response.getStatusCode, response.getResponseBody)
  }

}
