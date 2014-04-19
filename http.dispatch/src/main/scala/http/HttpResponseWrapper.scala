package http

import scala.reflect._
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import reflect.runtime.universe._


class HttpResponseWrapper[T](res: Future[(Int, String)])(implicit man: Manifest[T]) {

  import scala.concurrent.ExecutionContext.Implicits.global

  def code: Future[Int] = res.map(r => r._1)

  def body: Future[String] = res.map(r => r._2)

  def response: Any = {

    if (typeOf[T] =:= typeOf[String]) {
      Await.result(body, 0 nanos)
    }
    else if (typeOf[T] =:= typeOf[Country]) {
      new Country(body)
    }
    else
      None
  }
}