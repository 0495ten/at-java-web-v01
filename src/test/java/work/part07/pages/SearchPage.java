package work.part07.pages;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class SearchPage {
    SelenideElement
            cityFrom = $("#departureCity"),
            cityTo = $("#arrivalCity"),
            departureDate = $("#departureDate"),
            findButton = $x("//button[.='Найти']"),
            message = $("#searchMessage"),
            logoutButton = $("#logoutButton");

    @Step("Поиск рейсов (задаём только дату)")
    public void search(String departureDate) {

        this.departureDate.setValue(makeDateCorrect(departureDate));
        this.findButton.click();
    }

    @Step("Поиск рейсов")
    public void search(String departureDate, String from, String to) {
        this.departureDate.setValue(makeDateCorrect(departureDate));
        this.cityFrom.selectOption(from);
        this.cityTo.selectOption(to);
        this.findButton.click();
    }

    @Step("Проверка, что дата не указана")
    public void isDepartureDateEmpty() {
        this.message.shouldHave(text("Пожалуйста, укажите дату вылета."));
    }

    @Step("Проверка, что дата в прошлом")
    public void isDateInPast(){
        this.message.shouldHave(text("Невозможно осуществить поиск: выбранная дата уже прошла."));
    }
    @Step("Проверка, что кнопка logout существует")
    public void logoutExist(){
        this.logoutButton.shouldBe(visible);
    }
    @Step("Проверка, что кнопка 'Найти' работает")
    public void fidnButtonWork(){
        this.findButton.click();
    }
    @Step("Проверка, что кнопка 'Найти' работает")
    public void SetPastDate(){
        String date = "01.01.2020";
        String correctedDate = makeDateCorrect(date);
        this.departureDate.setValue(correctedDate);
    }

    private String makeDateCorrect(String date) {
        //Передаём дату в формате DD.MM.YYYY
        if (Configuration.browser == "firefox" && date != null && date.length() == 10) {
            return date.substring(6, 10) + "-" +
                    date.substring(3, 5) + "-" +
                    date.substring(0, 2);
        }
        return date;
    }
}