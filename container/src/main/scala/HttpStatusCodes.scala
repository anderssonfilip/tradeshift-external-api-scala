package container

import scala.collection.mutable

object HttpStatusCodes {

  def description(code: Int): String = {

    if (map.contains(code))
      map(code)
    else
      code.toString
  }

  private val map = new mutable.HashMap[Int, String]()

  map += (200 -> "OK")
  map += (401 -> "Unauthorized")
  map += (403 -> "Forbidden")
  map += (404 -> "Not Found")
  map += (405 -> "Method Not Allowed")

}
