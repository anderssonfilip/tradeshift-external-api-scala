package container

import scala.collection.mutable

object HttpStatusCodes {

  def description(code: Int): String = map(code)

  private val map = new mutable.HashMap[Int, String]()

  map += (200 -> "OK")
  map += (403 -> "Forbidden")
  map += (404 -> "Not Found")
  map += (405 -> "Method Not Allowed")

}
