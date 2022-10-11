package PageObject.PageSteps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;

import static PageObject.PageElements.AuthorizationPageElements.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.open;


public class AuthorizationPageSteps {

    @Given("Открываем главную страницу jira {string}")
    public static void openUrl(String url) {
        open(url);
    }

    @And("Вводим логин {string} и пароль {string}")
    public static void authorization(String login, String password) {
        loginLane.shouldBe(visible).sendKeys(login);
        passwordLane.sendKeys(password);
        buttonLogin.shouldBe(enabled).click();
    }
}

