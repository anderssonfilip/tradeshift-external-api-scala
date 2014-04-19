package http

import scala.collection.mutable.HashMap

case class TSHttpRequest(path: String, parameters: HashMap[String, String]) {

  def this(path: String) {
    this(path, new HashMap[String, String])
  }

  def addHeader(key: String, value: String): TSHttpRequest = {
    this
  }

  def addParameter(key: String, value: String): TSHttpRequest = {
    parameters += (key -> value)
    this
  }
}