package com.knoldus.models

import akka.http.scaladsl.server.Route
import com.knoldus.routes.FileUploadRoute


class Routes(handlers: Handlers) {

  private val fileUploadRoute: FileUploadRoute = new FileUploadRoute(handlers.fileUploadHandler)

  lazy val routes: Route = fileUploadRoute.route
}
