package RickMortyApi;


import com.codeborne.selenide.logevents.SelenideLogger;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"Hooks", "RickMortyApi"},
        tags = "@all",
        plugin =  {"pretty","io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm","json:target/cucumber.json","html:test-output"}
)

public class RunnerApiCucumberTest {

    @BeforeClass
    public static void before() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(false));
    }
}
