package com.knoldus.services

import akka.stream.scaladsl.Source
import akka.util.ByteString
import com.knoldus.bootstrap.Application.conf
import com.knoldus.models.FileSource
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import com.knoldus.base.BaseTest


class FileUploadServiceTest extends BaseTest {

  val handler = new FileUploadService(conf.getConfig("file-upload"))

  "FileUploadHandler#uploadFile" should {
    "upload file without error" in {
      val fileSource: Source[FileSource, Any] =
        Source.single(FileSource("file-name", Source.single(ByteString("1"))))
      whenReady(handler.uploadFiles(fileSource)){
        _ shouldBe ()
      }
    }

  }
}

