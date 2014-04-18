package client.http

import json.Bar

class TestHttpDelegate[T] extends HttpDelegate[T]{


  override def doGet(request: TSHttpRequest, clazz: Class[T]): Unit //HttpResponseWrapper[T] = 
  = {

    //val body  = jsonParser.read(response, clazz)
    //new HttpResponseWrapper[T](400, new Object())
  }


}
