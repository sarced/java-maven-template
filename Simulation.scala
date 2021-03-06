package performance

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class UserSimulation extends Simulation {

  val httpConf = http
    .baseUrl("${APP_URL}")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")

  val scn1 = scenario("Scenario1")
    .exec(Crawl.crawl)

  val userCount = Integer.getInteger("users",1)
  val durationInSeconds  = java.lang.Long.getLong("duration", 10L)
  setUp(
    scn1.inject(rampUsers(userCount) during (durationInSeconds seconds))
  ).protocols(httpConf)
}

object Crawl {

  val feeder = csv("/opt/gatling/user-files/urls.csv").random

  val crawl = exec(feed(feeder)
    .exec(http("${loc}")
    .get("${loc}")
    ))
}