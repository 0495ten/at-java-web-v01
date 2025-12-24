package demo.part07;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import demo.part07.pages.FlightsListPage1;
import demo.part07.pages.LoginPage1;
import demo.part07.pages.RegistrationPage1;
import demo.part07.pages.SearchPage1;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

@Execution(ExecutionMode.CONCURRENT)
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class POMFlightsTests {
    @BeforeAll
    static void beforeAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        Configuration.browser = "firefox";
    }

    @BeforeEach
    void setUp() {
        open("https://slqamsk.github.io/cases/slflights/v01/");
        getWebDriver().manage().window().maximize();
        sleep(10_000);
    }
    // ... Автотесты
    // 1. Неуспешный логин
    @Test
    void test01WrongPassword() {
        LoginPage1 myLoginPage1 = new LoginPage1();
        myLoginPage1.login("standard_user", "WrongPassword");
        myLoginPage1.isLoginUnsuccessful();
    }

    // 2. Не задана дата
    @Test
    void test02NoDate() {
        LoginPage1 loginPage1 = new LoginPage1();
        loginPage1.login("standard_user", "stand_pass1");
        loginPage1.isLoginSuccessful("Иванов Иван Иванович");

        SearchPage1 searchPage1 = new SearchPage1();
        searchPage1.search("");
        searchPage1.isDepartureDateEmpty();
    }
    // 3. Не найдены рейсы
    @Test
    void test03FlightsNotFound() {
        LoginPage1 loginPage1 = new LoginPage1();
        loginPage1.login("standard_user", "stand_pass1");
        loginPage1.isLoginSuccessful("Иванов Иван Иванович");

        SearchPage1 searchPage1 = new SearchPage1();
        searchPage1.search("16.03.2026", "Казань", "Париж");

        FlightsListPage1 flightsList = new FlightsListPage1();
        flightsList.isNoFlights();
    }

    //4. Успешная регистрация с данными по умолчанию
    @Test
    void test04SuccessRegistrationDefault() {
        // Страница логина
        LoginPage1 loginPage1 = new LoginPage1();
        loginPage1.login("standard_user", "stand_pass1");
        loginPage1.isLoginSuccessful("Иванов Иван Иванович");

        // Страница поиска рейсов
        SearchPage1 searchPage1 = new SearchPage1();
        searchPage1.search("16.03.2026", "Москва", "Нью-Йорк");

        // Страница со списком найденных рейсов
        FlightsListPage1 flightsList = new FlightsListPage1();
        flightsList.registerToFirstFlight();

        // Страница регистрации на рейс
        RegistrationPage1 registrationPage1 = new RegistrationPage1();
        registrationPage1.isFlightDataCorrect("Москва", "Нью-Йорк");
        registrationPage1.successRegistration();
    }

    // 5. Пустые поля
    @Test
    void test05EmptyField() {
        // Страница логина
        LoginPage1 loginPage1 = new LoginPage1();
        loginPage1.login("standard_user", "stand_pass1");
        loginPage1.isLoginSuccessful("Иванов Иван Иванович");

        // Страница поиска рейсов
        SearchPage1 searchPage1 = new SearchPage1();
        searchPage1.search("16.03.2026", "Москва", "Нью-Йорк");

        // Страница со списком найденных рейсов
        FlightsListPage1 flightsList = new FlightsListPage1();
        flightsList.registerToFirstFlight();

        // Страница регистрации на рейс
        RegistrationPage1 registrationPage1 = new RegistrationPage1();
        registrationPage1.isFlightDataCorrect("Москва", "Нью-Йорк");
        registrationPage1.registration("", "", "", "");
        registrationPage1.isErrorFillAllFied();
    }

    // 6. Успешный логин под разными пользователями.
    @ParameterizedTest
    @CsvFileSource (resources = "logins.csv")
    void test06MuliLogin(String userName, String passWord, String fio) {
        LoginPage1 lp = new LoginPage1();
        lp.login(userName,passWord);
        lp.isLoginSuccessful(fio);
        sleep(5000);
    }

    // 7. Проверка, что при поиске даты в прошлом - ошибка

    @Test
    void test07DateInPast() {
        LoginPage1 lp = new LoginPage1();
        lp.login("standard_user", "stand_pass1");

        SearchPage1 sp = new SearchPage1();
        sp.search("01.01.2024");
        sp.isDateInPast();
    }

    //(*) Напишите автотест для теста:
    // "Поиск - не найдены рейсы -
    // возврат на страницу поиска - найдены рейсы -
    // Регистрация на 1-й рейс в списке - не задан номер паспорта -
    // повторный ввод паспорта с корректными данными - успешная регистрация."

    @Test
    void test08LongTest() {
        LoginPage1 lp = new LoginPage1();
        lp.login("standard_user", "stand_pass1");

        // "Поиск - не найдены рейсы -
        SearchPage1 searchPage1 = new SearchPage1();
        searchPage1.search("16.03.2026", "Казань", "Париж");

        FlightsListPage1 flightsList = new FlightsListPage1();
        flightsList.isNoFlights();

        // возврат на страницу поиска - найдены рейсы -
        flightsList.newSearch();
        searchPage1.search("16.03.2026", "Москва", "Нью-Йорк");

        // Регистрация на 1-й рейс в списке - не задан номер паспорта -
        flightsList.registerToFirstFlight();

        RegistrationPage1 registrationPage1 = new RegistrationPage1();
        registrationPage1.isFlightDataCorrect("Москва", "Нью-Йорк");
        registrationPage1.registration("Иван Петров", "", "test@mail.ru", "+79999999999");
        registrationPage1.isErrorFillAllFied();

        // повторный ввод паспорта с корректными данными - успешная регистрация."
        registrationPage1.registration("Иван Петров", "1234 123456", "test@mail.ru", "+79999999999");
        registrationPage1.successRegistration();
    }

    @Test
    void test09DemoCollection() {
        LoginPage1 loginPage1 = new LoginPage1();
        loginPage1.login("standard_user", "stand_pass1");

        SearchPage1 searchPage1 = new SearchPage1();
        searchPage1.search("16.03.2026", "Москва", "Нью-Йорк");

        // Страница со списком найденных рейсов
        FlightsListPage1 flightsList = new FlightsListPage1();
        flightsList.sortByPrice();
        flightsList.isTimeSorted();
    }
}