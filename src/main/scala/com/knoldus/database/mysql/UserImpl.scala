package com.knoldus.database.mysql

import scala.concurrent.Future

import akka.Done
import com.knoldus.database.mysql.JdbcConnection.{connection, driver, getDbConnection}
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by manjot on 7/6/18.
 */

object UserImpl extends UserImpl

trait UserImpl {
  def returnMysqlData: String = {
    var demo: String = "Demo"
    try {
      Class.forName(driver)
      println("getting connection for returnMysqlData method")
      connection = getDbConnection()
      val statement = connection.createStatement
      val rs = statement.executeQuery("SELECT name FROM user")
      while (rs.next) {
        //val host = rs.getString("host")
        demo = rs.getString("name")
        //println("host = %s, user = %s".format(demo))
      }

    } catch {
      case e: Exception => e.printStackTrace
    }
    connection.close
    demo
  }



  def addUser(user: User) : Future[Done]  = {
    try {
      Class.forName(driver)
      println("getting connection for addUser method")
      connection = getDbConnection()

      val sql = "INSERT INTO user (id, name) VALUES (?, ?)"

      val statement = connection.prepareStatement(sql)
      statement.setInt(1, user.id)
      statement.setString(2, user.name)

      val rowsInserted = statement.executeUpdate
      if (rowsInserted > 0)
        println("A new user was inserted successfully!")
    }

    catch {
      case e: Exception => e.printStackTrace
    }
    connection.close

    Future { Done }
  }

  def updateUser(user: User) : Future[Done]  = {
    try {
      Class.forName(driver)
      println("getting connection for updateUser method")
      connection = getDbConnection()

      val sql = "update user set name = ? where id = ?"

      val statement = connection.prepareStatement(sql)
      statement.setString(1, user.name)
      statement.setInt(2, user.id)

      val rowsInserted = statement.executeUpdate
      if (rowsInserted > 0)
        println("A new user has been updated successfully!")
    }
    catch {
      case e: Exception => e.printStackTrace
    }
    connection.close

    Future { Done }
  }

  def deleteUser(value: Number) : Future[Done]  = {
    try {
      Class.forName(driver)
      println("getting connection for deleteUser method")
      connection = getDbConnection()

      val sql = "DELETE from user WHERE id = ?"

      val statement = connection.prepareStatement(sql)
      statement.setInt(1, value.id)


      val rowsInserted = statement.executeUpdate
      if (rowsInserted > 0)
        println("An existing user has been deleted successfully!")
    }
    catch {
      case e: Exception => e.printStackTrace
    }
    connection.close

    Future { Done }
  }


}
