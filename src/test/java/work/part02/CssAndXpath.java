package work.part02;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class CssAndXpath {
    @Test
    public void test01LoginSuccess() {
        open("https://slqa.ru/cases/xPathSimpleForm/");
        $(".unique_class").$$(".not_unique_class").shouldHave(CollectionCondition.texts("Добро пожаловать,"));
        // Здесь была лишняя закрывающая скобка }
    }  // Эта скобка закрывает метод test01LoginSuccess()

    @Test
    public void test02LoginWrongPassword() {
        open("https://slqamsk.github.io/cases/slflights/v01/");
        $(By.id("username")).setValue("standard_user");
        $(By.id("password")).setValue("wrong_password");
        $(By.id("loginButton")).click();
        $(".message.error").shouldHave(exactText("Неверное имя пользователя или пароль."));
    }
}