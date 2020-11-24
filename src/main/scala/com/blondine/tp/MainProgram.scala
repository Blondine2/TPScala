package com.blondine.tp

import java.util.logging.{Level, Logger}

import com.blondine.tp.reader.SparkReaderWriter
import com.blondine.tp.services.{Service1, Service2, Service3}
import org.apache.spark.sql.SparkSession


object MainProgram {
  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.OFF)
    val inputPath = "data/donnees.csv"
    val inputFormat = "CSV"
    val clientId = 292494523

    implicit val spark: SparkSession = SparkSession.builder().master("local").getOrCreate()

    val df = SparkReaderWriter.readData(inputPath,inputFormat)
    val result = Service1.deleteByClientId(df,clientId)
    println("Resultat après suppresssion : "+result.count())
    val result2 = Service2.AnonymizeByClientId(df, clientId)
        result2.show()
    Service3.mailClientDataCSV(clientId,"estheticadamazonafrika@gmail.com", "estheticaboutique2020")




  }

}
