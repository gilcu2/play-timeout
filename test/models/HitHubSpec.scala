import org.specs2.concurrent.ExecutionEnv
import play.core.server.Server
import play.api.mvc._
import play.api.routing._
import play.api.routing.sird._
import play.api.mvc._
import play.api.libs.json._
import play.api.test._

import scala.concurrent.Await
import scala.concurrent.duration._
import org.specs2.mutable.Specification

class GithubWsSpec(implicit ee: ExecutionEnv) extends PlaySpecification {

  "get all repositories" in {

    Server.withRouter() {
      case _ => Action {
        println("Sleeping")
        Thread.sleep(8000)
        println("wake")
        Results.Ok(Json.arr(Json.obj("full_name" -> "octocat/Hello-World")))
      }
    } { implicit port =>
      WsTestClient.withClient { client =>
        val result = Await.result(
          new GitHubClient(client, "").repositories(), 10.seconds)
        result must_== Seq("octocat/Hello-World")
      }
    }
  }


}