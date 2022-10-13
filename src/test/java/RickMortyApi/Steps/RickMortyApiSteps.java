package RickMortyApi.Steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class RickMortyApiSteps {


    private static final RequestSpecBuilder builder = new RequestSpecBuilder();
    private static Response response;
    private static String speciesMorty;
    private static String locationMorty;
    private static String lastEpisode;
    private static String speciesLastCharacter;
    private static String locationLastCharacter;


    @Given("^Установить базовый url  ([^\"]*)$")
    public static void setBaseUrl(String url) {
        builder.setBaseUri(url);
        //builder.addFilter(new AllureRestAssured());
    }

    @Given("^Установить header  ([^\"]*), ([^\"]*)$")
    public static void setHeader(String type, String accept) {
        builder.setContentType(type);
        builder.setAccept(accept);
    }

    @Given("^Передать параметры запроса для персонажа ([^\"]*)$")
    public static void getInfoCharacterByName(String name) {
        builder.addQueryParam("name", name);
    }

    @And("^Отправить ([^\"]*) запрос")
    public static void sendRequestByMethod(String requestMethod) {

        requestSpecification = builder.log(LogDetail.METHOD).log(LogDetail.PARAMS).build();

        String method = requestMethod.toLowerCase();

        switch (method) {
            case "get": {
                response = given()
                        .filter(new AllureRestAssured())
                        .when()
                        .get().
                        then().
                        extract().response();
                break;
            }
        }
    }

    @Given("^Установить эндпоинт ([^\"]*)$")
    public static void setEndpoint(String endpoint) {

        builder.setBasePath(endpoint);
    }

    @Then("^Статус код (\\d+)$")
    public static void checkByStatus(int expectedStatus) {
        int actualStatus = response.getStatusCode();
        assertEquals(expectedStatus, actualStatus);
    }

    @And("Вывести информацию о персонаже")
    public static void getInfo() {

        String morty = response.body().jsonPath().getJsonObject("results[0]").toString();
        speciesMorty = response.body().jsonPath().get("results[0].species").toString();
        locationMorty = response.body().jsonPath().get("results[0].location.name").toString();

        System.out.println("Информация о Morty Smith: " + morty);
        System.out.println("Раса Morty: " + speciesMorty);
        System.out.println("Местонахождение Morty: " + locationMorty);

    }

    @Then("Получить информацию о последнем эпизоде")
    public void getEpisode() {
        List<Object> episodes = response.body().jsonPath().getList("results[0].episode");
        lastEpisode = episodes.get(episodes.size() - 1).toString();
        System.out.println(lastEpisode);
    }

    @And("Получить информацию о последнем персонаже эпизода")
    public void lastCharacterInfo() {

        List<String> characters = given()
                .filter(new AllureRestAssured())
                .expect().log().body()
                .when()
                .get(lastEpisode)
                .then().statusCode(200)
                .extract()
                .jsonPath().getList("characters");

        String lastCharacter = characters.get(characters.size() - 1);

        response = given()
                .filter(new AllureRestAssured())
                .when()
                .get(lastCharacter)
                .then()
                .extract().response();

        String nameLastCharacter = response.body().jsonPath().getJsonObject("name").toString();
        speciesLastCharacter = response.body().jsonPath().getJsonObject("species").toString();
        locationLastCharacter = response.body().jsonPath().getJsonObject("location.name").toString();

        System.out.printf("Информация о последнем персонаже:\nимя: %s\nраса: %s\nместонахождение: %s", nameLastCharacter, speciesLastCharacter, locationLastCharacter);
    }

    @Then("Сравнить расу и местонахождение персонажей")
    public void compareCharacters() {
        assertEquals(speciesMorty, speciesLastCharacter);
        assertNotEquals(locationMorty, locationLastCharacter);
    }
}
