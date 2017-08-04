import javax.inject.Inject

import play.api.libs.ws.WSClient

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._

class GitHubClient(ws: WSClient, baseUrl: String)(implicit ec: ExecutionContext) {
  @Inject def this(ws: WSClient, ec: ExecutionContext) = this(ws, "https://api.github.com")(ec)

  def repositories(): Future[Seq[String]] = {
    ws.url(baseUrl + "/repositories").withRequestTimeout(3 seconds).get().map { response =>
      (response.json \\ "full_name").map(_.as[String])
    }
  }
}

