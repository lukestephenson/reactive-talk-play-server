package controllers

import akka.stream.scaladsl.{Flow, Keep, Sink}
import akka.util.ByteString
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.streams.Accumulator
import play.api.mvc._

import scala.concurrent.Future

class FileUploadController extends Controller {

  def reportPostUploadSize = Action(countLengthBodyParser) { request =>
    val bodySize: Int = request.body // only to show what type this is.
    Ok("Play - Size of body is " + bodySize)
  }

  private def countLengthBodyParser: BodyParser[Int] = BodyParser("count length") { request =>
    val sink: Sink[ByteString, Future[Int]] = Flow[ByteString]
      .toMat(Sink.fold(0)((count, bytes) => count + bytes.size))(Keep.right)

    // Convert the body to a Right either
    Accumulator(sink).map(Right.apply)
  }

  def saveTempAndReportPostUploadSize = Action(BodyParsers.parse.temporaryFile) { request =>
    val bodySize: Long = request.body.file.length() // only to show what type this is.
    Ok("Play - Size of body is " + bodySize)
  }


}
