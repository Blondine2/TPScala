package com.blondine.tp.services

import com.blondine.tp.reader.SparkReaderWriter
import javax.mail.internet.{InternetAddress, MimeBodyPart, MimeMessage, MimeMultipart}
import javax.mail.{Authenticator, Message,PasswordAuthentication, Session, Transport}
import org.apache.spark.sql.SparkSession
import javax.activation.{DataHandler, FileDataSource}



object Service3 {


  def mailClientDataCSV(idClient: Int, clientMail: String, clientPassword: String): Unit = {
    import org.apache.spark.sql.functions._
    val inputPath = "data/donnees.csv"
    val inputFormat = "CSV"

    implicit val spark: SparkSession = SparkSession.builder().master("local").getOrCreate()
    val df = SparkReaderWriter.readData(inputPath, inputFormat)
    val df2 = df.filter(col("Client ID") === idClient)
    val df3 = df2.write.csv("data/data2.csv")

    val smtpHost = "smtp.gmail.com"

    //client authentification
    val properties = System.getProperties
    properties.put("mail.smtp.host", smtpHost)
    properties.put("mail.smtp.user", clientMail);
    properties.put("mail.smtp.password", clientPassword);
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.port", "587")
    properties.put("mail.smtp.starttls.enable", "true");

    val auth:Authenticator = new Authenticator() {
      override def getPasswordAuthentication = new
          PasswordAuthentication(clientMail, clientPassword)
    }
    // constructing  the message
    val session = Session.getInstance(properties,auth)
    val message = new MimeMessage(session)
    val multipart = new MimeMultipart();

    val fileName = "clientdata.csv"
    val messageBodyPart = new MimeBodyPart()
    messageBodyPart.setFileName(fileName)
    val pathToAttachment = "data/data.csv/part-00000-39d31cb6-4f81-4af7-b9e5-c6a3e0d38988-c000.csv"
    val source: FileDataSource = new FileDataSource(pathToAttachment)
    messageBodyPart.setDataHandler(new DataHandler(source))
    multipart.addBodyPart(messageBodyPart)
    message.setFrom(new InternetAddress("pascalefouenang@gmail.com"))
    message.setRecipients(Message.RecipientType.TO, clientMail)
    message.setHeader("Content-Type", "text/html")
    message.setSubject("Total of all your transactions")
    message.setContent(multipart)

    // sending the message
    println("Resultats du service 3 envoyés par mail au client ")
    Transport.send(message)
  }
}
