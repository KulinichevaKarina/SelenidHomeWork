import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardOrderingServiceTest {
    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void bankingServiceTest() throws InterruptedException {
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("[data-test-id='city'] input").setValue("Нижний Новгород");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME, Keys.DELETE));
        String curentDate = generateDate(8, "dd.MM.yyyy");
        form.$("[data-test-id='date'] input").sendKeys(curentDate);
        form.$("[data-test-id='name'] input").setValue("Салтыков-Щедрин Михаил");
        form.$("[data-test-id='phone'] input").setValue("+79990001122");
        form.$(".checkbox__box").click();
        form.$(".button__text").click();
        $(".notification__title")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Успешно!"));
        $(".notification__content")
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + curentDate));
    }

}
