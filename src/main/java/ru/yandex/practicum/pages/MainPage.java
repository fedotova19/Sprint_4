package ru.yandex.practicum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

// Класс для главной страницы Яндекс Самокат
public class MainPage {

    // Драйвер для управления браузером
    private final WebDriver driver;

    // Ожидание
    private final WebDriverWait wait;
    private By orderField = By.cssSelector(".Input_Input__1iN_Z.Header_Input__xIoUq");
    private By statusButton = By.cssSelector(".Header_Link__1TAG7");
    private By goButton = By.cssSelector(".Button_Button__ra12g.Header_Button__28dPO");
    // Верхняя кнопка Заказать (в хедере)
    private By orderButtonTop = By.xpath(".//button[@class='Button_Button__ra12g' and text()='Заказать']");
    // Нижняя кнопка Заказать (в середине страницы)
    private By orderButtonBottom = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM' and text()='Заказать']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // ожидание с таймаутом 10 секунд
    }

    public StatusPage clickOnGoButton() {
        WebElement goBtn = wait.until(ExpectedConditions.elementToBeClickable(goButton));

        // Прокрутка
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", goBtn);
        wait.until(ExpectedConditions.elementToBeClickable(goBtn));
        goBtn.click();

        return new StatusPage(driver);
    }


    public void enterOrderIn(String orderNumber) {
        WebElement orderInput = wait.until(ExpectedConditions.visibilityOfElementLocated(orderField));
        orderInput.clear();
        orderInput.sendKeys(orderNumber);
    }

    public void clickOnStatusButton() {
        WebElement statusBtn = wait.until(ExpectedConditions.elementToBeClickable(statusButton));
        statusBtn.click();
    }

    public void openMainPage() {
        driver.get(util.EnvConfig.BASE_URL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(orderButtonTop));
    }

    public FaqPage getFaqPage() {
        return new FaqPage(driver);
    }

    public OrderPage clickOrderButtonTop() {
        WebElement topBtn = wait.until(ExpectedConditions.elementToBeClickable(orderButtonTop));
        topBtn.click();
        return new OrderPage(driver);
    }

    public OrderPage clickOrderButtonBottom() {
        WebElement bottomButton = wait.until(ExpectedConditions.presenceOfElementLocated(orderButtonBottom));

        // Прокрутка
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", bottomButton);

        // Ожидание кликабельности после прокрутки
        WebElement clickableBottomBtn = wait.until(ExpectedConditions.elementToBeClickable(bottomButton));
        clickableBottomBtn.click();

        return new OrderPage(driver);
    }

    // Проверка видимости верхней кнопки Заказать
    public boolean isTopOrderButtonVisible() {
        try {
            WebElement topBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(orderButtonTop));
            return topBtn.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Проверка видимости нижней кнопки Заказать после прокрутки
    public boolean isBottomOrderButtonVisible() {
        try {
            WebElement bottomButton = wait.until(ExpectedConditions.presenceOfElementLocated(orderButtonBottom));

            // Прокрутка
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", bottomButton);

            // Ожидание видимости после прокрутки
            WebElement visibleButton = wait.until(ExpectedConditions.visibilityOf(bottomButton));
            return visibleButton.isDisplayed();

        } catch (Exception e) {
            return false;
        }
    }
}