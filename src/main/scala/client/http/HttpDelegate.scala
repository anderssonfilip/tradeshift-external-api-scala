package client.http

abstract trait HttpDelegate[T] {

  //def doPost(request: TSHttpRequest, clazz: Class[T]): HttpResponseWrapper[T]

  def doGet(request: TSHttpRequest, clazz: Class[T]): Unit //HttpResponseWrapper[T]

  //def doPatch(request: TSHttpRequest, clazz: Class[T]): HttpResponseWrapper[T]

  //def doDelete(request: TSHttpRequest, clazz: Class[T]): HttpResponseWrapper[T]
}
