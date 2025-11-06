package ru.yandex.practicum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class StatusPage {
    private final WebDriver driver;
    private By errorImage = By.cssSelector("img[alt='Not found']");

    public StatusPage(WebDriver driver) {
        this.driver = driver;
    }


    // Метод проверяет, отображается ли картинка с ошибкой
    public boolean isErrorImageDisplayed() {
        try {
            WebElement errorImg = driver.findElement(errorImage);
            return errorImg.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Метод для получения текста ошибки
    public String getErrorText() {
        try {
            WebElement errorImg = driver.findElement(errorImage);
            return errorImg.getAttribute("alt");
        } catch (Exception e) {
            return "Error element not found";
        }
    }
}