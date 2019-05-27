package stellarsdks

import cucumber.api.scala.{EN, ScalaDsl}

class StepDefinitions extends ScalaDsl with EN {

  Given("""^account ([A-Za-z]+) with balance of ([0-9]+) XLM$""") { (accountName: String, lumenBalance: Int) =>
    println(s"Account $accountName with balance of $lumenBalance XLM")
  }

  Given("""^account ([A-Za-z]+) with balance of ([0-9]+\.[0-9]+) XLM$""") { (accountName: String, lumenBalance: Double) =>
    println(s"Account $accountName with balance of $lumenBalance XLM")
  }

  When("""^account ([A-Za-z]+) pays account ([A-Za-z]+) ([0-9]+) XLM$""") {
    (fromAccountName: String, toAccountName: String, lumenBalance: Int) =>
      println(s"Account $fromAccountName pays account $toAccountName $lumenBalance XLM")
  }

  When("""^account ([A-Za-z]+) pays account ([A-Za-z]+) ([0-9]+\.[0-9]+) XLM$""") {
    (fromAccountName: String, toAccountName: String, lumenBalance: Double) =>
      println(s"Account $fromAccountName pays account $toAccountName $lumenBalance XLM")
  }

  Then("""account ([A-Za-z]+) must have balance ([0-9]+) XLM$""") { (accountName: String, lumenBalance: Int) =>
    println(s"Account $accountName must have balance $lumenBalance XLM")
  }

  Then("""account ([A-Za-z]+) must have balance ([0-9]+\.[0-9]+) XLM$""") { (accountName: String, lumenBalance: Double) =>
    println(s"Account $accountName must have balance $lumenBalance XLM")
  }
}

