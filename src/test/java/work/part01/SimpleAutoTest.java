package work.part01;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SimpleAutoTest {
    @Test
    void test01() {
        open("http://92.51.36.108:7777/sl.qa");
        $("body").shouldHave(text("Учебные приложения"));
    }
    @Test
    void test02() {
        open("http://92.51.36.108:7777/sl.qa");
        SelenideElement a = $("body");
        a.shouldHave(text("Промышленные приложения"));
    }
}