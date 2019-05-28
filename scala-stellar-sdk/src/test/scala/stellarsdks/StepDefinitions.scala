package stellarsdks

import java.net.URI

import cucumber.api.scala.{EN, ScalaDsl}
import org.junit.Assert.assertEquals
import stellar.sdk.model.op.{CreateAccountOperation, PaymentOperation}
import stellar.sdk.model.{Amount, NativeAmount, NativeAsset, Transaction}
import stellar.sdk.{KeyPair, Network, StandaloneNetwork}

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.sys.process._

class StepDefinitions extends ScalaDsl with EN {

  "../bin/stellar_standalone.sh true".!

  private implicit val network: Network = StandaloneNetwork(URI.create("http://localhost:8000"))

  Given("""^account ([A-Za-z]+) with balance of ([0-9]+) XLM$""") { (accountName: String, lumenBalance: Int) =>
    fundAccount(accountName, lumenBalance)
  }

  Given("""^account ([A-Za-z]+) with balance of ([0-9]+\.[0-9]+) XLM$""") { (accountName: String, lumenBalance: Double) =>
    fundAccount(accountName, lumenBalance)
  }

  When("""^account ([A-Za-z]+) pays account ([A-Za-z]+) ([0-9]+) XLM$""") {
    (fromAccountName: String, toAccountName: String, amount: Int) =>
      pay(fromAccountName, toAccountName, amount)
  }

  When("""^account ([A-Za-z]+) pays account ([A-Za-z]+) ([0-9]+\.[0-9]+) XLM$""") {
    (fromAccountName: String, toAccountName: String, amount: Double) =>
      pay(fromAccountName, toAccountName, amount)
  }

  Then("""account ([A-Za-z]+) must have balance ([0-9]+) XLM$""") { (accountName: String, lumenBalance: Int) =>
    assertEquals(Some(NativeAmount(lumenBalance * math.pow(10, 7).toLong)), nativeAmount(accountName))
  }

  Then("""account ([A-Za-z]+) must have balance ([0-9]+\.[0-9]+) XLM$""") { (accountName: String, lumenBalance: Double) =>
    assertEquals(Some(NativeAmount((lumenBalance * math.pow(10, 7)).toLong)), nativeAmount(accountName))
  }

  private def fundAccount(accountName: String, lumenBalance: Double): Unit = {
    val account = KeyPair.fromPassphrase(accountName)
    Await.result(for {
      masterAccount <- network.account(network.masterAccount)
      resp <- Transaction(masterAccount, Seq(CreateAccountOperation(account, Amount.lumens(lumenBalance))))
        .sign(network.masterAccount).submit()
    } yield resp, 10.seconds)
  }

  private def pay(fromAccountName: String, toAccountName: String, amount: Double): Unit = {
    val fromAccountPair = KeyPair.fromPassphrase(fromAccountName)
    Await.result(for {
      fromAccount <- network.account(fromAccountPair)
      resp <- Transaction(fromAccount, Seq(PaymentOperation(KeyPair.fromPassphrase(toAccountName), Amount.lumens(amount))))
        .sign(fromAccountPair).submit()
    } yield resp, 10.seconds)
  }

  private def nativeAmount(accountName: String): Option[NativeAmount] = {
    Await.result(network.account(KeyPair.fromPassphrase(accountName)), 10.seconds).balances
      .find(_.amount.asset == NativeAsset)
      .map(_.amount.asInstanceOf[NativeAmount])
  }
}

