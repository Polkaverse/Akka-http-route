package com.knoldus.database.mysql

import java.sql.{Connection, DriverManager}


/**
 * Created by manjot on 6/6/18.
 */
object JdbcConnection {
  val url = "jdbc:mysql://localhost:3306/demo"
  val driver = "com.mysql.jdbc.Driver"
  val username = "root"
  val password = "root"
  var connection:Connection = _

  def getDbConnection(): Connection = DriverManager.getConnection(url, username, password)

}