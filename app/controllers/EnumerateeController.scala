package controllers

import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.iteratee._
import play.api.mvc._

object EnumerateeController extends Controller {

  def enumerateeExample = Action.async { request =>
    val iterator: Iteratee[Int, Int] = Iteratee.fold(0) { (total: Int, elt: Int) => total + elt}

    val enumerator: Enumerator[Int] = Enumerator(100, 200, 300)
    val enumerator2: Enumerator[Int] = Enumerator(1, 5, 10)

    for {total1 <- enumerator.run(iterator)
         total2 <- enumerator2.run(iterator)}
    yield Ok("Sum of enumerator one is " + total1 + " sum of enumerator two is " + total2)
  }
}