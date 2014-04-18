package client.http

import scala.collection.mutable.HashMap

class TSHttpRequest(path: String, parameters: HashMap[String, String]) {

  def this(path: String) {
    this(path, new HashMap[String, String])
  }

  def addParameter(key: String, value: String): TSHttpRequest = {
    parameters += (key -> value)
    this
  }
}