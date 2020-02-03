package globus.services

import com.orientechnologies.orient.core.id.ORID
import globus.commands.infrastructure.graph.{KbClassExistingChecker, TermCommand}
import globus.domain.Term
import globus.infrastructure.langApi.rop._

class TermsDictionaryProvider {
  private val classExistingChecker = new KbClassExistingChecker

  private val command = new TermCommand

  def addTerms(terms: Array[Term]): R[Array[ORID], ServiceError] = {
    try{
      classExistingChecker check "Term"
      classExistingChecker check "TermType"
      val termIds = new Array[ORID](terms.length)
      for ((term, i) <- terms.zipWithIndex) {
        val id: ORID = command addVertex term match {
          case Succ(data) => data
          case Fail(msg) => return fail(new ServiceError(msg.message))
        }
        termIds(i) = id
      }
      succeed(termIds)
    } catch {
      case e: Exception => fail(new ServiceError("Error until adding terms to KB"))
    }
  }
}
