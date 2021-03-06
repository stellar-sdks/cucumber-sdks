package stellarsdks

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
        plugin = ["pretty"],
        features = ["../features/"],
        glue = ["stellarsdks"])
class RunCucumberTests
