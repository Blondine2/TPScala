package com.blondine.tp.services

import org.apache.spark.sql.functions.col
import org.apache.spark.sql.{DataFrame, SparkSession}

object Service2 {
  def AnonymizeByClientId (df: DataFrame, clientId: Int): DataFrame= {
    val sparkSession = SparkSession.builder().master("local").getOrCreate()
    import sparkSession.implicits._

    val d1 = df.filter(col("Client ID") === clientId)
    import org.apache.spark.sql.functions._
    val d2 = d1.select($"Region", $"Country", $"Item Type", $"Sales Channel", $"Order Priority", $"Order Date", hash($"Client ID").alias("Client ID"),
      $"Ship Date", $"Units Sold", $"Unit Price", $"Unit Cost", $"total Revenue", $"Total Cost", $"Total Profit")
    println("Resultats du service 2 après cryptage de l'id du client : ")
    d2.show()
   return d2
  }



}
