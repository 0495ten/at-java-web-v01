package work.part05;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ParamatrisezedTest2 {
    @ParameterizedTest
    @CsvFileSource(resources = "com_test.csv", numLinesToSkip=1)
    void test02Positive(String login, String password, String name) {
        open("https://slqamsk.github.io/cases/slflights/v01/");
        $(By.id("username")).setValue(login);
        $(By.id("password")).setValue(password);
        $(By.id("loginButton")).click();
        $(By.id("logoutButton")).shouldBe(visible);
        $(".greeting").shouldHave(text("Добро пожаловать, " + name));
    }
}
