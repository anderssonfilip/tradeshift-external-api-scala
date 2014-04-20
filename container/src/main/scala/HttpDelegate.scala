package container

import scala.concurrent.Future

abstract trait HttpDelegate {

  //def doDelete(request: TSHttpRequest, clazz: Class[T]): HttpResponseWrapper[T]

  def doGet(request: HttpRequest): Future[(Int, String)]

  //def doPatch(request: TSHttpRequest, clazz: Class[T]): HttpResponseWrapper[T]

  def doPost(request: HttpRequest): Future[(Int, String)]

  def sign(consumerKey: String, consumerSecret: String, token: String, tokenSecret: String)
}
