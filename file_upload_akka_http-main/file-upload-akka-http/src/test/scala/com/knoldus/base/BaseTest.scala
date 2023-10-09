package com.knoldus.base

import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.testkit.{ ImplicitSender, TestKit, TestKitBase }
import com.typesafe.config.{ Config, ConfigFactory }
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{ Seconds, Span }
import org.scalatest.{ Matchers, WordSpec }
import org.scalatestplus.mockito.MockitoSugar

trait BaseTest extends WordSpec
  with ScalatestRouteTest
  with TestKitBase
  with ImplicitSender
  with Matchers
  with MockitoSugar
  with ScalaFutures {

  override implicit val patienceConfig: PatienceConfig = PatienceConfig(Span(3, Seconds))

  protected val conf: Config = ConfigFactory.load()

  override def afterAll(): Unit = {
    super.afterAll()
    TestKit.shutdownActorSystem(system, verifySystemShutdown = true)
  }

}
