package globus.services

import globus.app.AppError
import globus.domain.{TechProcess, Term}
import globus.factories.constructContexts.OperationConstructContext
import globus.infrastructure.langApi.rop.R

import scala.collection.mutable.ArrayBuffer

trait TechProcessCreatable {
  def create(name: String, term: Term, applyingTerm: Term, startOpName: String, opNames: Option[ArrayBuffer[ArrayBuffer[String]]],
             opValueNums: Option[Map[(Int, Int), (Int, Int)]] = None)
    : R[TechProcess, AppError]

  /*def create(name: String, startOpName: String, opConstructContexts: Option[ArrayBuffer[ArrayBuffer[OperationConstructContext]]])
    : R[TechProcess, AppError]

  def create(name: String, startOpName: String, opNames: Option[ArrayBuffer[Map[Int, ArrayBuffer[Map[Int, String]]]]],
             opConstructContexts: Option[ArrayBuffer[Map[Int, ArrayBuffer[Map[Int, OperationConstructContext]]]]])
    : R[TechProcess, AppError]

  def create(name: String, startOpConstructContext: OperationConstructContext, opNames: Option[ArrayBuffer[ArrayBuffer[String]]])
    : R[TechProcess, AppError]

  def create(name: String, term: Term, applyingTerm: Term, startOpConstructContext: OperationConstructContext,
             opConstructContexts: Option[ArrayBuffer[ArrayBuffer[OperationConstructContext]]],
             opValueNums: Option[Map[(Int, Int), (Int, Int)]] = None)
    : R[TechProcess, AppError]

  def create(name: String, startOpConstructContext: OperationConstructContext, opNames: Option[ArrayBuffer[Map[Int, ArrayBuffer[Map[Int, String]]]]],
             opConstructContexts: Option[ArrayBuffer[Map[Int, ArrayBuffer[Map[Int, OperationConstructContext]]]]])
    : R[TechProcess, AppError]*/
}
