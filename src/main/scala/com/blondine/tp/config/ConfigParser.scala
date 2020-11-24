package com.blondine.tp.config

import scopt.{DefaultOParserSetup, OParserBuilder, OParserSetup}


case class ConfigParser(inputPath: String = "", inputFormat: String= "",
                        outputPath: String = "", outputFormat: String= "", service: String = "")

object ConfigParser {
  private val setup: OParserSetup = new DefaultOParserSetup {
    override def showUsageOnError: Option[Boolean] = Some(true)
    override def errorOnUnknownArgument = false
  }
  import scopt.OParser
  val builder: OParserBuilder[ConfigParser] = OParser.builder[ConfigParser]

  val parser: OParser[Unit, ConfigParser] = {
    import builder._

    OParser.sequence(
      programName("GDPR Compliance"),

      opt[String]("inputPath")
        .required()
        .action((x, c) => c.copy(inputPath = x))
        .text("this is the inputPath "),

      opt[String]("inputFormat")
        .required()
        .action((x, c) => c.copy(inputFormat = x))
        .text("This is the inputPath"),
      opt[String]("outputPath")
        .required()
        .action((x, c) => c.copy(outputPath = x))
        .text("Here is the output path"),
      opt[String]("outputFormat")
        .action((x, c) => c.copy(outputFormat = x))
        .text("Here is the output format"),
      opt[String]("service")
        .action((x, c) => c.copy(service = x))
        .text("calling the service ")


    )
  }

  def parser(arguments: Array[String]): Option[ConfigParser] = {
    OParser.parse(ConfigParser.parser, arguments, ConfigParser(), setup)
  }

  def getConfigArgs(args: Array[String]): ConfigParser = {
    ConfigParser.parser(args) match {
      case Some(config) => config
      case _ => {
        print("cannot parse conf")
        sys.exit(1)
      }
    }
  }
}
