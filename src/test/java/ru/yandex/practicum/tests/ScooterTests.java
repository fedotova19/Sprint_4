package ru.yandex.practicum.tests;

import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import ru.yandex.practicum.pages.MainPage;
import ru.yandex.practicum.pages.StatusPage;

import static org.junit.Assert.assertTrue;

public class ScooterTests {

    @Rule
    public DriverFactory factory = new DriverFactory();

    @Test
    public void testNonExistingOrderNotFound(){

        WebDriver driver = factory.getDriver();
        var mainPage = new MainPage(driver);

        // Переходим на сайт
        mainPage.openMainPage();
        // Находим кнопку "Статус заказа" и нажимаем её
        mainPage.clickOnStatusButton();
        // Переходим в поле ввода "Введите номер заказа"
        mainPage.enterOrderIn("123");
        // Нажать на кнопку Go!
        StatusPage statusPage = mainPage.clickOnGoButton();
        // Результат теста - картинка с ошибкой видна
        boolean isErrorHandled = statusPage.isErrorImageDisplayed() ||
                "Not found".equals(statusPage.getErrorText());
        assertTrue("Система должна корректно обрабатывать несуществующий заказ",
                isErrorHandled);
    }
}
