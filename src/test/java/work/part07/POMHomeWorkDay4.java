package work.part07;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import demo.part07.pages.FlightsListPage1;
import work.part07.pages.LoginPage;
import work.part07.pages.SearchPage;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

@Execution(ExecutionMode.CONCURRENT)
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class POMHomeWorkDay4 {
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

    @Test
    void test01EmptyLoginData() {
        LoginPage myLoginPage = new LoginPage();
        myLoginPage.login("", "");
        myLoginPage.isLoginUnsuccessful();
    }

    // 2. Не задана дата
    @Test
    void test02LogoutButton() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("standard_user", "stand_pass1");
        loginPage.isLoginSuccessful("Иванов Иван Иванович");

        SearchPage searchPage = new SearchPage();
        searchPage.logoutExist();
    }
    @Test
    void test03ReturnToSearchPage() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("standard_user", "stand_pass1");
        loginPage.isLoginSuccessful("Иванов Иван Иванович");

        SearchPage searchPage = new SearchPage();
        searchPage.fidnButtonWork();

        FlightsListPage1 flightsList = new FlightsListPage1();
        flightsList.newSearch();

        searchPage.logoutExist();
    }
}