package ru.yandex.practicum.tests;

import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import ru.yandex.practicum.pages.MainPage;

import static org.junit.Assert.assertTrue;

// Класс для проверки видимости кнопок заказа на главной странице
public class OrderButtonsTest {

    @Rule
    public DriverFactory factory = new DriverFactory();

    @Test
    public void testBothOrderButtonsVisible() {
        WebDriver driver = factory.getDriver();
        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage();

        // Проверка, что обе кнопки "Заказать" видны
        assertTrue("Верхняя кнопка заказа должна быть видима",
                mainPage.isTopOrderButtonVisible());
        assertTrue("Нижняя кнопка заказа должна быть видима",
                mainPage.isBottomOrderButtonVisible());
    }
}