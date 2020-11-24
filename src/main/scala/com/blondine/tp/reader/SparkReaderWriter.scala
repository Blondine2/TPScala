package com.blondine.tp.reader

import org.apache.spark.sql.{DataFrame, SparkSession}

object SparkReaderWriter {
  def readData(inputPath: String, inputFormat: String)(implicit spark: SparkSession): DataFrame = {
    inputFormat match {
      case "JSON" => spark.read.json(inputPath)
      case "PARQUET" => spark.read.parquet(inputPath)
      case _ => spark.read.option("header", true).option("inferSchema", true).option("delimiter", ";").csv(inputPath)
    }
  }

  def writeData(df: DataFrame, outputPath: String, outputFormat: String): Unit = {

    df.write.format(outputFormat).save(outputPath)
  }
}
