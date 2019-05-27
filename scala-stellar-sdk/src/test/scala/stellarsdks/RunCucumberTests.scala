package stellarsdks

import cucumber.api.CucumberOptions
import org.junit.runner.RunWith
import cucumber.api.junit.Cucumber

@RunWith(classOf[Cucumber])
@CucumberOptions(
  plugin = Array("pretty"),
  features = Array("../features/"),
  glue = Array("stellarsdks")
)
class RunCucumberTests
