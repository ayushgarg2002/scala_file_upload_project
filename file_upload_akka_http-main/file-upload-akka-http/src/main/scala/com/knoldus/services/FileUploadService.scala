package com.knoldus.services

import akka.stream.Materializer
import akka.stream.scaladsl.{ FileIO, Source }
import com.knoldus.models.FileSource
import java.nio.file.{Path, Paths}
import com.typesafe.config.Config
import scala.concurrent.{ExecutionContext, Future}


class FileUploadService(
                         conf: Config
                       )(
                         implicit executionContext: ExecutionContext
                       ) {

  val uploadPath: String = conf.getString("path")

  def uploadFiles(source: Source[FileSource, Any])(
    implicit materializer: Materializer
  ): Future[Unit] = {
    source.runFoldAsync(()) { (_, fileSource) =>
      val filePath: Path = Paths.get(s"$uploadPath/${ fileSource.fileName }")
      fileSource.source.runWith(FileIO.toPath(filePath)).map(_ => ())
    }
  }

}
