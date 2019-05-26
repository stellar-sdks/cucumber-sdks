package stellarsdks

import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import io.inbot.kotlinstellar.KotlinStellarWrapper
import io.inbot.kotlinstellar.TokenAmount
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import org.stellar.sdk.AssetTypeNative
import org.stellar.sdk.KeyPair
import org.stellar.sdk.Server
import org.stellar.sdk.findAccount
import org.stellar.sdk.responses.AccountResponse
import org.stellar.sdk.responses.balanceAmount
import java.io.File
import java.nio.charset.Charset
import java.security.MessageDigest
import java.util.concurrent.TimeUnit

class StepDefinitions {

    private val server = Server("http://localhost:8000")
    private val wrapper = KotlinStellarWrapper(server)

    init {
        // TODO (jmawson) - reset the stellar instance, quickly, instead of restarting/upgrading image
        //      DB statements via `psql` in bash script?
        "bin/stellar_standalone.sh true".runCommand()
    }

    @Given("""^account ([A-Za-z]+) with balance of ([0-9]+) XLM$""")
    fun `given account with a balance`(accountName: String, lumenBalance: Int)  =
        `given account with a balance`(accountName, lumenBalance.toDouble())

    @Given("""^account ([A-Za-z]+) with balance of ([0-9]+\.[0-9]+) XLM$""")
    fun `given account with a balance`(accountName: String, lumenBalance: Double) {
        wrapper.createAccount(TokenAmount.of(lumenBalance), newAccount = account(accountName))
    }

    @When("""^account ([A-Za-z]+) pays account ([A-Za-z]+) ([0-9]+) XLM$""")
    fun `account makes a payment`(fromAccountName: String, toAccountName: String, lumenBalance: Int) =
            `account makes a payment`(fromAccountName, toAccountName, lumenBalance.toDouble())

    @When("""^account ([A-Za-z]+) pays account ([A-Za-z]+) ([0-9]+\.[0-9]+) XLM$""")
    fun `account makes a payment`(fromAccountName: String, toAccountName: String, lumenBalance: Double) {
        wrapper.pay(account(fromAccountName), account(toAccountName), amount = TokenAmount.of(lumenBalance))
    }

    @Then("""account ([A-Za-z]+) must have balance ([0-9]+) XLM$""")
    fun `assert account balance`(accountName: String, lumenBalance: Int) =
            `assert account balance`(accountName, lumenBalance.toDouble())

    @Then("""account ([A-Za-z]+) must have balance ([0-9]+\.[0-9]+) XLM$""")
    fun `assert account balance`(accountName: String, lumenBalance: Double) {
        val accountResponse = server.findAccount(account(accountName))
        accountResponse shouldNotBe null
        accountResponse?.balances?.size shouldBe 1
        val actual: AccountResponse.Balance = accountResponse?.balances?.get(0)!!
        actual.asset shouldBe AssetTypeNative()
        actual.balanceAmount() shouldBe TokenAmount.of(lumenBalance)
    }

    private fun account(name: String): KeyPair = KeyPair.fromSecretSeed(sha256(name))

    private fun sha256(s: String): ByteArray {
        val md = MessageDigest.getInstance("SHA-256")
        md.update(s.toByteArray(Charset.forName("UTF-8")))
        return md.digest()
    }

    private fun String.runCommand() {
        ProcessBuilder(*split(" ").toTypedArray())
                .directory(File("."))
                .redirectOutput(ProcessBuilder.Redirect.INHERIT)
                .redirectError(ProcessBuilder.Redirect.INHERIT)
                .start()
                .waitFor(60, TimeUnit.MINUTES)
    }

}