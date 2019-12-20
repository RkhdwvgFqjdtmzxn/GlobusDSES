package globus.services

class ConsoleHelpShell extends ConsoleShell {

  def help(): Unit = {
    writeLine("For more info about one of commands print help <command name>")
    writeLine("fillKb. Creating knowledge base for Saturn.")
    writeLine("setAppFilesDir. Set default directory for app files")
    writeLine("newTerm. Input info about new terms")
    writeLine("getTermInfo. Show detailed info about defined term.")
    writeLine("ti. Show detailed info about defined term")
    writeLine("getAllTerms or tl. Show list of all terms")
    writeLine("tc. Terms card. th/tj. Navigation by card.")
    writeLine("editTerm. Change defined attributes of term")
    writeLine("delTerm. Delete defined term")
    writeLine("delTerms. Delete defined list of terms")
    writeLine("exit. Exit from application")
  }

  def help(helpingCommand: String): Unit = {
    helpingCommand match {
      case "setAppFilesDir" => helpSetAppFilesDir
      case "newTerm" => helpNewTerm
      case "getTermInfo" => helpGetTermInfo
      case "ti" => helpGetTermInfo
      case "getAllTerms" => helpGetAllTerms
      case "tl" => helpGetAllTerms
      case "tc" => helpTermsCard
      case "th" => helpTermsCard
      case "tj" => helpTermsCard
      case "editTerm" => helpEditTerm
      case "delTerm" => helpDelTerm
      case "delTerms" => helpDelTerms
      case _ => writeLine("Help for this command not found")
    }
  }

  def helpFillKb: Unit = {
    writeLine("Creating knowledge base for Saturn.")
  }

  def helpSetAppFilesDir(): Unit = {
    writeLine("Set default directory for app files")
    writeLine("setAppFilesDir directory")
  }

  def helpNewTerm(): Unit = {
    writeLine("Creating new term")
    writeLine("newTerm name type definition/newTerm [attributes]. Term type may be object, characteristic, event")
    writeLine("attributes: ")
    writeLine("-n: name, -t: type, -d: definition")
    writeLine("Examples: newterm Trnmt -t event -d the defined spt event")
    writeLine("newterm SptCmnd object spt cmnd")
    writeLine("newterm -t characteristic -n team-work -d abcdef")
  }

  def helpGetTermInfo(): Unit = {
    writeLine("Show detailed info about defined term")
    writeLine("getTermInfo/ti name")
  }

  def helpGetAllTerms(): Unit = {
    writeLine("Get all terms")
    writeLine("getAllTerms")
    writeLine("tl")
  }

  def helpTermsCard(): Unit = {
    writeLine("Terms card")
    writeLine("Tool for browsing paginated info about terms. One page - one term.")
    writeLine("For start working with termsCard write tc/th/tj")
    writeLine("tc is command for browse info about first term in list, for start to work. th: up by list; tj: down by list (vim analogy)")
  }

  def helpEditTerm(): Unit = {
    writeLine("Change defined attributes of term")
    writeLine("editTerm name changeCommandsSet. Elements of changeCommandsSet for Term: name, type, definition")
    writeLine("Examples:")
    writeLine("editTerm SptCmnd name=spotCmnd, type=object, definition=default_definition")
  }

  def helpDelTerm(): Unit = {
    writeLine("Delete defined term")
    writeLine("delTerm name")
  }

  def helpDelTerms(): Unit = {
    writeLine("Delete defined list of terms")
    writeLine("delTerms [names]. \r\n Examples: \r\n delTerms Ferrum, Calcium")
  }
}

