package com.avinash

import java.io.{PrintWriter, StringWriter}
import scala.collection.mutable

package object log {
  val noGroup = ""
  def emptyMap: mutable.Map[String, String] = mutable.Map.empty

  def exceptionAsString(exception: Throwable): String = {
    val writer = new StringWriter
    exception.printStackTrace(new PrintWriter(writer))
    writer.toString
  }
}
