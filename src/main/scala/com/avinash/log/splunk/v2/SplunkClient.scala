package com.avinash.log.splunk.v2

import io.circe._
import org.slf4j.Logger
import sttp.client3.circe._
import sttp.client3.okhttp.{OkHttpFutureBackend, OkHttpSyncBackend}
import sttp.client3.{Response, UriContext, basicRequest}

import java.io.{PrintWriter, StringWriter}
import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success, Try}

class SplunkClient(private val splunkUrl: String, private val authorizationCode: String)
                  (implicit log: Logger, ec: ExecutionContext) {

  def getExceptionMessageAsString(exception: Throwable): String = {
    val stringWriter = new StringWriter
    val printWriter = new PrintWriter(stringWriter)
    exception.printStackTrace(printWriter)
    printWriter.flush()
    stringWriter.toString
  }

  val headers: Map[String, String] = Map("Authorization" -> authorizationCode)

  def log(message: Json, async: Boolean = false): Unit = {
    if (async) {
      logAsyncToSplunk(message, headers)
    } else {
      logSyncToSplunk(message, headers)
    }
  }

  private def logSyncToSplunk[A](body: A, headers: Map[String, String] = Map.empty)
                                (implicit encoder: Encoder[A]): Unit = {
    val backend = OkHttpSyncBackend()
    val request = basicRequest
      .post(uri"$splunkUrl")
      .body(encoder.apply(body).deepDropNullValues)
      .headers(headers)

    val res = Try(backend.send(request))
    handleResponse(res)
    backend.close()
  }

  private def logAsyncToSplunk[A](body: A, headers: Map[String, String] = Map.empty)
                                 (implicit encoder: Encoder[A]): Unit = {
    val backend = OkHttpFutureBackend()

    val request = basicRequest
      .post(uri"$splunkUrl")
      .body(encoder.apply(body).deepDropNullValues)
      .headers(headers)

    backend.send(request)
      .onComplete(res => {
        handleResponse(res)
        backend.close()
      })
  }

  private def handleResponse(res: Try[Response[Either[String, String]]]): Unit = {
    res match {
      case Success(res) =>
        (res.code.code, res.body) match {
          case (200, Right(_)) =>
            log.debug("Sent msg successfully")
          case (statusCode, Right(unknown)) =>
            log.error(s"ResponseCode $statusCode Invalid request sent to splunk $unknown")
          case (_, Left(error)) =>
            log.error(s"Exception occurred while logging message to splunk $error")
        }
      case Failure(exception) =>
        log.error(s"Exception occurred while logging to splunk ${getExceptionMessageAsString(exception)}")
    }
  }
}
