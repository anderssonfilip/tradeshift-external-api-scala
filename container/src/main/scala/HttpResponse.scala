package container

import scala.concurrent.Future

abstract case class HttpResponse[T](res: Future[(Int, String)])(implicit man: Manifest[T]) {

  import scala.concurrent.ExecutionContext.Implicits.global

  def code: Future[Int] = res.map(r => r._1)

  def body: Future[String] = res.map(r => r._2)

  def create: Any
}