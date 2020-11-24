package com.blondine.tp.config

import spray.json.{DefaultJsonProtocol, JsonFormat}

case class JsonConfig(inputPath: String, filterColumn: String, filterValue: String, outputFormat: String,
                      outputPath: String)


object JsonConfigProtocol extends DefaultJsonProtocol {
  implicit val jsonConfig: JsonFormat[JsonConfig] = lazyFormat(jsonFormat5(JsonConfig))
}
