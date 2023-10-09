package com.knoldus.models

import com.knoldus.services.FileUploadService
import com.typesafe.config.Config
import akka.actor.ActorSystem

import scala.concurrent.ExecutionContext

class Handlers(conf: Config)(
  implicit actorSystem: ActorSystem,
  executionContext: ExecutionContext
) {

  lazy val fileUploadHandler: FileUploadService = new FileUploadService(conf.getConfig("file-upload"))

}
