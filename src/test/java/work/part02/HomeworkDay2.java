package work.part02;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class HomeworkDay2 {
    @Test
    public void HomeWork() {
        open("https://slqa.ru/cases/xPathSimpleForm/");
        $$(".not_unique_class").findBy(text("Москва"))
                .shouldHave(text("250 единиц"));
        $x("//div[@class='not_unique_class'][contains(text(), 'Питер')]")
                .shouldHave(text("Показатели"));
        $x("//div[@class='not_unique_class'][starts-with(text(), 'Казахстан')]")
                .shouldHave(text("площадь 2 724 902"));
    }
}
