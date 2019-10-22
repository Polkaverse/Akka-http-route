package com.knoldus

import scala.concurrent.Future
import scala.concurrent.duration._

import akka.Done
import akka.http.scaladsl.model.{HttpEntity, HttpMethods, HttpRequest, MediaTypes}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.{RouteTestTimeout, ScalatestRouteTest}
import akka.util.ByteString
import com.knoldus.database.mysql.{Number, User, UserImpl}
import org.mockito.Mockito.when
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{Matchers, WordSpec}

/**
 * Created by manjot on 7/6/18.
 */
class RestSpec extends WordSpec with Matchers with ScalatestRouteTest with MockitoSugar {


  implicit val timeout = RouteTestTimeout(3 seconds)
  val mockUserImpl = mock[UserImpl]
//  val mockUser = mock[User]


  object TestObject extends RestService {
    val userImpl = mockUserImpl
  }



  "The service" should {


    "return a user name for GET requests /read" in {
      when(mockUserImpl.returnMysqlData).thenReturn("Jackson")


      // tests:
      Get("/read") ~> TestObject.route ~> check {
        responseAs[String] shouldEqual "Jackson"
        handled shouldBe true
      }
    }




    "return user added as response for a Post request to /adduser" in {
      when(mockUserImpl.addUser(User(2, "test"))).thenReturn(Future.successful(Done))

      // tests:
      val jsonRequest = ByteString(
        s"""
           |{
           |    "id":2,
           |    "name":"test"
           |}
        """.stripMargin)
      val postRequest = HttpRequest(
        HttpMethods.POST,
        uri = "/adduser",
        entity = HttpEntity(MediaTypes.`application/json`, jsonRequest))

      postRequest ~>  Route.seal(TestObject.route) ~> check {
       // val r = Await.result(s,10.seconds)
        status.isSuccess() shouldEqual true
        responseAs[String] shouldEqual "user added"
      }

    }
    "return a user updated response for a Put request to /updateuser" in {
      when(mockUserImpl.updateUser(User(2, "updatetest"))).thenReturn(Future.successful(Done))
      // tests:
      val jsonRequest = ByteString(
        s"""
           |{
           |    "id":2,
           |    "name":"updatetest"
           |}
        """.stripMargin)
      val postRequest = HttpRequest(
        HttpMethods.PUT,
        uri = "/updateuser",
        entity = HttpEntity(MediaTypes.`application/json`, jsonRequest))

      postRequest ~> TestObject.route ~> check {
        status.isSuccess() shouldEqual true
        responseAs[String] shouldEqual "user updated"
      }

    }



    "delete an existing user for a Delete request to /deleteuser" in {
      when(mockUserImpl.deleteUser(Number(2))).thenReturn(Future.successful(Done))
      // tests:
      val jsonRequest = ByteString(
        s"""
           |{
           |    "id":2
           |}
        """.stripMargin)
      val postRequest = HttpRequest(
        HttpMethods.DELETE,
        uri = "/deleteuser",
        entity = HttpEntity(MediaTypes.`application/json`, jsonRequest))

      postRequest ~> TestObject.route ~> check {
        status.isSuccess() shouldEqual true
        responseAs[String] shouldEqual "user deleted"
      }

    }

    "leave GET requests to other paths unhandled" in {
      // tests:
      Get("/kermit") ~> TestObject.route ~> check {
        handled shouldBe false
      }
    }

//    "return a MethodNotAllowed error for PUT requests to the root path" in {
//      // tests:
//      Put() ~> Route.seal(smallRoute) ~> check {
//        status shouldEqual StatusCodes.MethodNotAllowed
//        responseAs[String] shouldEqual "HTTP method not allowed, supported methods: GET"
//      }
//    }
  }

}
