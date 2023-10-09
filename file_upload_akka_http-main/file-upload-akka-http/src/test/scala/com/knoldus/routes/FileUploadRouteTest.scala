package com.knoldus.routes

import akka.http.scaladsl.model.Multipart.FormData
import akka.http.scaladsl.model.{Multipart, StatusCodes}
import akka.http.scaladsl.server.Route
import akka.stream.Materializer
import akka.stream.scaladsl.Source
import akka.util.ByteString
import com.knoldus.base.BaseTest
import com.knoldus.models.FileSource
import com.knoldus.services.FileUploadService
import org.mockito.Matchers.any
import org.mockito.Mockito.when

import scala.concurrent.Future

class FileUploadRouteTest extends BaseTest {

  val handler: FileUploadService = mock[FileUploadService]
  val routes: Route = new FileUploadRoute(handler).route

  "POST /uploadFile" should {

    "upload file" in {
      val firstFile: FormData.BodyPart = Multipart.FormData.BodyPart.Strict(
        "file", ByteString("1"), Map("filename" -> "test.png"), List()
      )
      val secondFile: FormData.BodyPart = Multipart.FormData.BodyPart.Strict(
        "file", ByteString("2"), Map("filename" -> "test2.png"), List()
      )
      val formData = Multipart.FormData(firstFile, secondFile)

      when(handler.uploadFiles(any[Source[FileSource, Any]])(any[Materializer])) thenReturn Future.successful(())

      Post("/fileUpload", formData) ~> Route.seal(routes) ~> check {
        status shouldEqual StatusCodes.OK
      }

    }

    "fail uploading file if any error occurs" in {
      val firstFile: FormData.BodyPart = Multipart.FormData.BodyPart.Strict(
        "file", ByteString("1"), Map("filename" -> "test.png"), List()
      )
      val secondFile: FormData.BodyPart = Multipart.FormData.BodyPart.Strict(
        "file", ByteString("2"), Map("filename" -> "test2.png"), List()
      )
      val formData = Multipart.FormData(firstFile, secondFile)

      when(handler.uploadFiles(any[Source[FileSource, Any]])(any[Materializer])) thenReturn Future.failed(
        new RuntimeException("Some Error")
      )

      Post("/fileUpload", formData) ~> Route.seal(routes) ~> check {
        status shouldEqual StatusCodes.InternalServerError
      }

    }

  }

}
