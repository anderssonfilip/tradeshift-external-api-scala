package http

import scala.concurrent.Future

abstract trait HttpDelegate {

  //def doDelete(request: TSHttpRequest, clazz: Class[T]): HttpResponseWrapper[T]

  def doGet(request: TSHttpRequest): Future[(Int, String)]

  //def doPatch(request: TSHttpRequest, clazz: Class[T]): HttpResponseWrapper[T]

  def doPost(request: TSHttpRequest): Future[(Int, String)]
}
