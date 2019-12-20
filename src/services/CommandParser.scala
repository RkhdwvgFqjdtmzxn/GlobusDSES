package globus.services

import java.util

import globus.app.AppError
import globus.app.authority.CurrentUser
import globus.commands.infrastructure.graph.internal.{ChangeVertexContext, DeleteVertexContext}
import globus.infrastructure.langApi.rop._
import org.petitparser.parser.Parser
import org.petitparser.parser.primitive.StringParser
import org.petitparser.parser.primitive.CharacterParser._

import scala.collection.mutable.ArrayBuffer

class CommandParser {
  def parseEdit (params: String): R[ChangeVertexContext, AppError] = {
    try {
      var paramsPart = params;
      val parser: Parser = StringParser.of("").seq(StringParser.of("")seq(noneOf(",;{([])} ").plus()).seq(whitespace().plus())).plus().seq((letter().or(digit()))
        .plus()).seq(whitespace().star())
        .seq(StringParser.of("=")).seq(whitespace().star()).seq(word().plus()).seq(StringParser.of(",").seq(whitespace().star()).seq((letter().or(digit())).plus())
        .seq(StringParser.of("=")).seq(word().plus()).star())
        .map((_: util.ArrayList[String]) => {
          def getIdPart: ArrayBuffer[String] = {
            var idPart = paramsPart.takeWhile(_ != '=').reverse.dropWhile(!_.isWhitespace).reverse
            var id = new ArrayBuffer[String]
            while (idPart.nonEmpty) {
              id += idPart.takeWhile(!_.isWhitespace)
              idPart = idPart.dropWhile(!_.isWhitespace).dropWhile(_.isWhitespace)
            }
            id
          }
          val id = getIdPart
          def getChangeVertexContext: ChangeVertexContext = {
            var names = new ArrayBuffer[String]
            var values = new ArrayBuffer[String]
            paramsPart = paramsPart.dropWhile(!_.isWhitespace).dropWhile(_.isWhitespace)
            while (paramsPart.contains("=")) {
              val preEqArr = paramsPart.dropWhile(_ == ' ').takeWhile(!Array('=').contains(_)).reverse.takeWhile(_ != ' ').reverse
              names += preEqArr
              paramsPart = paramsPart.dropWhile(_ != '=').dropWhile(_ == '=')
              val postEqArr = paramsPart.dropWhile(_ == ' ').takeWhile(!Array(',', ' ').contains(_))
              values += postEqArr
              paramsPart = paramsPart.dropWhile(!Array(',', ' ').contains(_)).dropWhile(Array(',', ' ').contains(_))
            }
            new ChangeVertexContext(id, names, values)
          }
          getChangeVertexContext
        })
      val result: ChangeVertexContext = GetParseResult(params, parser)
      succeed(result)
    } catch {
      case e: Exception => {
        StandardExceptionHandle(e)
      }
    }
  }

  def parseDelTrnmts(params: String): R[DeleteVertexContext, AppError] = {
    try {
      val parser: Parser = StringParser.of("{").seq(whitespace().star()).seq(StringParser.of("id")).seq(whitespace().plus()).
        seq(StringParser.of("in").or(StringParser.of("not").seq(whitespace().plus()).seq(StringParser.of("in"))))
        .seq(whitespace().star()).seq(StringParser.of("[")).seq(whitespace().star())
        .seq(noneOf(",;{([])}").plus()).seq(whitespace().star()).seq(StringParser.of(",").seq(whitespace().star()).seq(noneOf(",;{([])}").plus())
        .seq(whitespace().star()).star())
        .seq(StringParser.of("]")).seq(whitespace().star()).seq(StringParser.of("}")).seq(whitespace().star())
        .map((_: util.ArrayList[String]) => {
          val endIdIndex = params.indexOf("i") + 2
          val closeBraceIndex = params.indexOf("]") + 1
          new DeleteVertexContext("Trnmt", "id" + "\r\n" + params.substring(endIdIndex, closeBraceIndex))
        })
      val result: DeleteVertexContext = GetParseResult(params, parser)
      succeed(result)
    } catch {
      case e: Exception => {
        StandardExceptionHandle(e);
      }
    }
  }

  private def GetParseResult[T](params: String, parser: Parser): T = {
    val parseResult = parser.parse(params)
    val result = parseResult.get[T]()
    result
  }

  private def StandardExceptionHandle(e: Exception): R[Nothing, ParsingError] = {
    val exMessage = if (CurrentUser.isProgramEngineer) {
      ("\r\n" + e.getMessage)
    } else "";
    fail(new ParsingError("Parsing console command error. " + exMessage))
  }
}
