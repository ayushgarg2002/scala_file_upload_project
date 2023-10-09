package com.knoldus.models

import akka.stream.scaladsl.Source
import akka.util.ByteString

case class FileSource(fileName: String, source: Source[ByteString, Any])