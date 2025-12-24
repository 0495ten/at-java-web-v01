package work.part06;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class HomeWorkDay3 {
    @Test
    void Part1() {
        Configuration.pageLoadStrategy = "eager";
        open("https://www.specialist.ru/");
        $(By.id("cookieConsent")).shouldBe(visible);
        $(By.id("cookieConsent__ok")).click();
        $("a[href='/learning-formats']").click();
        $("a[href='/free-learning-new']").click();
        $(".page-button.banner-button").shouldHave(exactText("Выбрать курс")).click();
        $(By.id("Filter_CategoriesDirectionFilter")).selectOption("Программирование");
        $(By.id("sendBtn")).click();
    }
    @ParameterizedTest
    @CsvFileSource(resources = "film_PositiveTest.csv", numLinesToSkip=1)
    void Part2Step1Positive(String age, Long plusdays, String time, String film) {
        open("http://92.51.36.108:7777/sl.qa/cinema/index.php/");
        $("input[name='age']").setValue(age);
        LocalDate futureLocalDate = LocalDate.now().plusDays(plusdays);
        String futureDate;
        futureDate = futureLocalDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("input[name='date']").setValue(futureDate);
        $("input[name='session'][value='" + time + "']").click();
        $("input[name='film'][value='" + film + "']").click();
        $("input[type='submit']").click();
        $("div").shouldNotHave(text("надо указать"));
    }
    @Test
    void Part2Step2Negative() {
        open("http://92.51.36.108:7777/sl.qa/cinema/index.php/");
        $("input[type='submit']").click();
        $("div")
                .shouldHave(text("надо указать возраст"))
                .shouldHave(text("надо указать фильм"))
                .shouldHave(text("надо указать дату сеанса"))
                .shouldHave(text("надо указать сеанс"));
    }
    @ParameterizedTest
    @CsvFileSource(resources = "film_NegativeTest.csv", numLinesToSkip=1)
    void Part2Step3Negative(String age, String film, String ageerror) {
        open("http://92.51.36.108:7777/sl.qa/cinema/index.php/");
        $("input[name='age']").setValue(age);
        $("input[name='film'][value='" + film + "']").click();
        $("input[type='submit']").click();
        $("div").shouldNotHave(text(ageerror));
    }
}