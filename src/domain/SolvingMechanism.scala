package globus.domain

import scala.collection.mutable.ArrayBuffer

class SolvingMechanism (val terms: Option[ArrayBuffer[Term]], val termKinds: Option[ArrayBuffer[TermKind]],
                        val aspects: Option[ArrayBuffer[SolvingAspect]]) {

}
