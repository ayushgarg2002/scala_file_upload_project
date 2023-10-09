package com.knoldus.routes

import java.util.UUID
import akka.http.scaladsl.model.{Multipart, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.BasicDirectives.extractRequestContext
import com.knoldus.services.FileUploadService
import com.knoldus.models.FileSource
import scala.util.{Failure, Success}
import scala.concurrent.{ExecutionContext, Future}

class FileUploadRoute(
                       fileUploadHandler: FileUploadService
                     ) {

  val route: Route =
    path("fileUpload") {
      (post & withoutSizeLimit) {
        extractRequestContext { ctx =>
          import ctx.materializer
          entity(as[Multipart.FormData]) { data =>
            val fileSource = data.parts.collect { case bodyPart =>
              val fileName = bodyPart.filename.fold(s"${UUID.randomUUID()}")(identity)
              FileSource(fileName, bodyPart.entity.dataBytes)
            }
            onComplete(fileUploadHandler.uploadFiles(fileSource)) {
              case Success(_) => complete("Uploaded")
              case Failure(exception) => complete(StatusCodes.InternalServerError -> exception.getMessage)
            }
          }
        }
      }
    }
}
