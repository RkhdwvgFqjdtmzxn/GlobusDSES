package globus.domain

import scala.collection.mutable.ArrayBuffer

object EdgeVector extends Enumeration {
  type EdgeVector = Value
  val in, out = Value
}
