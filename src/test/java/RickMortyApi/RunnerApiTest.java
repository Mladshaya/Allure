package RickMortyApi;



import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"RickMortyApi.Steps", "ReqResApi"},
        tags = "@rickAndMorty or @reqRes",
        plugin =  {"pretty", "io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm", "json:target/cucumber.json", "html:test-output"}
)
public class RunnerApiTest {


}


