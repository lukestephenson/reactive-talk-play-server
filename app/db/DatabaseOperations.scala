package db

import play.libs.Akka

import scala.concurrent.{Future, ExecutionContext}

object DatabaseOperations {

  implicit val dbExecutionContext: ExecutionContext = Akka.system.dispatchers.lookup("db-context")

  def loadData(): Future[String] = {
    Future {
      // Sleep for 1 second to simulate slow database access
      Thread.sleep(1000)

      "Hello From DB"
    }
  }
}
