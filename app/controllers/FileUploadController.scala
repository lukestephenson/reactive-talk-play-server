package controllers

import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.iteratee._
import play.api.mvc._

object FileUploadController extends Controller {

  def reportPostUploadSize = Action(countLengthBodyParser) { request =>
    val bodySize: Int = request.body // only to show what type this is.
    Ok("Size of body is " + bodySize)
  }

  def saveTempAndReportPostUploadSize = Action(BodyParsers.parse.temporaryFile) { request =>
    val bodySize: Long = request.body.file.length() // only to show what type this is.
    Ok("Size of body is " + bodySize)
  }

  private def bodySizeCountingIteratee: Iteratee[Array[Byte], Int] = {
    def step(total: Int): Input[Array[Byte]] => Iteratee[Array[Byte], Int] = {
      case Input.EOF | Input.Empty => Done(total, Input.EOF)
      case Input.El(e) => Cont(step(total + e.size))
    }
    Cont(step(0))
  }

  private def countLengthBodyParser: BodyParser[Int] = BodyParser("count length") { request =>
//    val bodySizeCountingIteratee: Iteratee[Array[Byte], Int] = Iteratee.fold(0) { (state: Int, chunk: Array[Byte]) => state + chunk.size}

    bodySizeCountingIteratee map (size => Right(size))
  }

  // as above, but without the intermediate variables
  private def countLengthBodyParserCompact: BodyParser[Int] = BodyParser("count length compact") { request =>
    Iteratee.fold(0) { (state, chunk: Array[Byte]) => state + chunk.size} map (size => Right(size))
  }


}