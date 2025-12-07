package work.part02;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AuthorizationTests {
    @Test
    public void test01LoginSuccess() {
        open("https://slqamsk.github.io/cases/slflights/v01/");
        $(By.id("username")).setValue("standard_user");
        $(By.id("password")).setValue("stand_pass1");
        $(By.id("loginButton")).click();
        $(By.id("greeting")).shouldHave(exactText("Добро пожаловать, Иванов Иван Иванович!"));
        $(By.id("logoutButton")).click();
    }
    @Test
    public void test02LoginWrongPassword() {
        open("https://slqamsk.github.io/cases/slflights/v01/");
        $(By.id("username")).setValue("standard_user");
        $(By.id("password")).setValue("wrong_password");
        $(By.id("loginButton")).click();
        $(".message.error").shouldHave(exactText("Неверное имя пользователя или пароль."));
    }
}