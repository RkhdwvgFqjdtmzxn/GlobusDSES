package globus.extensions

import scala.collection.mutable.ArrayBuffer

object StringExtensions {

  implicit class ExtendedString(val value: String) {
    def splitByWhitespace(): ArrayBuffer[String] = {
      val preResult = value.split(" +")
      ArrayBuffer(preResult: _*)
    }

  }

}
