package com.blondine.tp.services

import com.blondine.tp.reader.SparkReaderWriter
import org.apache.spark.sql.{DataFrame, SparkSession}
object Service1 {

  def deleteByClientId (df: DataFrame, clientId: Int): DataFrame = {
    import org.apache.spark.sql.functions._
    df.filter(col("Client ID") =!= clientId)
  }



}
