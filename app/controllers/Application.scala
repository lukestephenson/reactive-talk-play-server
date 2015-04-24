package controllers

import db.DatabaseOperations
import play.api.libs.concurrent.Execution.Implicits._
import play.api.mvc._

import scala.concurrent.Future

object Application extends Controller {

  def index = Action {
    Ok("Hello Play")
  }

  def dbOperationDbPool = Action.async { request =>
    for {dbResult <- DatabaseOperations.loadData()}
    yield Ok(dbResult)
  }

  def dbOperationPlayPool = Action.async { request =>
    Future {
      // Sleep for 1 second to simulate slow database access
      Thread.sleep(1000)

      Ok("Hello From DB")
    }
  }

  case class Product(name:String, description: String)

  
}