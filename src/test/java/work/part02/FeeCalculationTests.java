package work.part02;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
public class FeeCalculationTests {
    @Test
    public void testElementSearchMethods() {
        open("https://slqa.ru/cases/fc/v01/");
        $(By.name("sum")).setValue("10")
        .click();
    }
}