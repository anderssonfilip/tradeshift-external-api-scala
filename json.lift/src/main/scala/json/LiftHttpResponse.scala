package json

import container._
import scala.concurrent.{Future, Await}
import scala.reflect.runtime.universe._
import scala.concurrent.duration._

class LiftHttpResponse[T](res: Future[(Int, String)])(implicit man: Manifest[T]) extends HttpResponse[T](res: Future[(Int, String)]) {

  override def create: Any = {

    if (typeOf[T] =:= typeOf[String]) {
      Await.result(body, 0 nanos)
    }
    else if (typeOf[T] =:= typeOf[TsAccountInfo]) {
      //new TsAccountInfo(body)
      None
    }
    else
      None
  }
}
