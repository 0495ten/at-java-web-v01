package demo.part04;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class iframeTask {
    @Test
    void test01IFrame() {
        //Configuration.pageLoadTimeout = 120_000;
        Configuration.pageLoadStrategy = "eager";

        open("https://demoqa.com/frames");
        getWebDriver().manage().window().maximize();
        switchTo().frame($x("//iframe[@id='frame1']"));
        $x("//h1[contains(text(), 'This is a sample page')]").shouldBe(visible);
    }
}