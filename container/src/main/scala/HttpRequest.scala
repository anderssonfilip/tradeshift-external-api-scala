package container

import scala.collection.mutable.HashMap

case class HttpRequest(path: String, headers: HashMap[String, String], parameters: HashMap[String, String]) {

  def this(path: String) {
    this(path, new HashMap[String, String], new HashMap[String, String])
  }

  def addHeader(key: String, value: String) = {
    headers += (key -> value)
    this
  }

  def addParameter(key: String, value: String): HttpRequest = {
    parameters += (key -> value)
    this
  }
}