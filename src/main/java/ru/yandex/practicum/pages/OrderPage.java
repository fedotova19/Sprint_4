package ru.yandex.practicum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

// Класс для страницы оформления заказа
public class OrderPage {

    // Драйвер для управления браузером
    private final WebDriver driver;
    // Ожидание
    private final WebDriverWait wait;


    private By nameField = By.xpath(".//input[@placeholder='* Имя']");
    private By surnameField = By.xpath(".//input[@placeholder='* Фамилия']");
    private By addressField = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
    private By metroField = By.xpath(".//input[@placeholder='* Станция метро']");

    // Список доступных станций метро
    private By metroStationOption = By.xpath(".//div[@class='select-search__select']//button");
    private By phoneField = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");

    // Кнопка перехода на вторую страницу заказа
    private By nextButton = By.xpath(".//button[text()='Далее']");
    private By deliveryDateField = By.xpath(".//input[@placeholder='* Когда привезти самокат']");

    // Дата Завтра в календаре
    private By rentalPeriodField = By.xpath(".//div[text()='* Срок аренды']");

    // Срок аренды в выпадающем списке
    private By rentalPeriodOption = By.xpath(".//div[@class='Dropdown-option']");
    private By colorBlackCheckbox = By.id("black");
    private By colorGreyCheckbox = By.id("grey");
    private By commentField = By.xpath(".//input[@placeholder='Комментарий для курьера']");

    // Кнопка оформления заказа (на второй странице)
    private By orderButton = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM' and text()='Заказать']");

    // Кнопка подтверждения заказа в окошке
    private By confirmOrderButton = By.xpath(".//button[text()='Да']");

    // Окно успешного оформления заказа
    private By successOrderModal = By.xpath(".//div[contains(@class, 'Order_ModalHeader')]");


    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // ожидание с таймаутом 10 секунд
    }

    // Заполнение первой страницы формы заказа
    public void fillFirstPage(String name, String surname, String address, String metroStation, String phone) {
        // Ожидание загрузки формы перед заполнением
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameField));

        driver.findElement(nameField).sendKeys(name);
        driver.findElement(surnameField).sendKeys(surname);
        driver.findElement(addressField).sendKeys(address);

        // Выбор станции метро
        // Клик на поле выбора метро
        driver.findElement(metroField).click();

        // Ожидание когда список станций загрузится
        wait.until(ExpectedConditions.visibilityOfElementLocated(metroStationOption));

        // Выбор первой доступной станции
        WebElement firstStation = wait.until(ExpectedConditions.elementToBeClickable(
                driver.findElements(metroStationOption).get(0) // выбираем первую станцию из списка
        ));
        firstStation.click();

        // Заполняем телефон
        driver.findElement(phoneField).sendKeys(phone);
    }

    // Клик на кнопку Далее для перехода на вторую страницу заказа
    public void clickNextButton() {
        // Ожидание кликабельности кнопки
        WebElement nextBtn = wait.until(ExpectedConditions.elementToBeClickable(nextButton));
        nextBtn.click();

        // Ожидание загрузки второй страницы
        wait.until(ExpectedConditions.visibilityOfElementLocated(deliveryDateField));
    }

    //Заполнение второй страницы формы заказа (с 08.11 перестал работать локатор для выбора даты, пришлось добавить обходное решение, изначальный код закомментирован)
    public void fillSecondPage(String deliveryDate, String rentalPeriod, String color, String comment) {
        // Выбор даты доставки
        // Клик на поле даты
        WebElement dateField = wait.until(ExpectedConditions.elementToBeClickable(deliveryDateField));

        // Кликаем и сразу вводим дату
        dateField.click();
        dateField.sendKeys(deliveryDate);

        dateField.sendKeys(Keys.ENTER);

        // ВЫбор срока аренды
        // Клик на поле срока аренды
        WebElement periodField = wait.until(ExpectedConditions.elementToBeClickable(rentalPeriodField));
        periodField.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(rentalPeriodOption));

        // Ищем и выбираем нужный срок аренды из списка
        for (WebElement period : driver.findElements(rentalPeriodOption)) {
            if (period.getText().equals(rentalPeriod)) {
                wait.until(ExpectedConditions.elementToBeClickable(period)).click();
                break;
            }
        }

        // Выбор цвета самоката
        if ("black".equals(color)) {
            WebElement blackCheckbox = wait.until(ExpectedConditions.elementToBeClickable(colorBlackCheckbox));
            blackCheckbox.click();
        } else if ("grey".equals(color)) {
            WebElement greyCheckbox = wait.until(ExpectedConditions.elementToBeClickable(colorGreyCheckbox));
            greyCheckbox.click();
        }

        WebElement commentElement = wait.until(ExpectedConditions.elementToBeClickable(commentField));
        commentElement.clear();
        commentElement.sendKeys(comment);
    }

    //Нажатие кнопки Заказать на второй странице
    public void clickOrderButton() {
        WebElement orderBtn = wait.until(ExpectedConditions.elementToBeClickable(orderButton));
        orderBtn.click();
    }

    // Подтверждение заказа
    public void confirmOrder() {
        WebElement confirmBtn = wait.until(ExpectedConditions.elementToBeClickable(confirmOrderButton));
        confirmBtn.click();
    }

    // Проверка успешного создания заказа
    public boolean isOrderSuccess() {
        try {
            // Ожидание окна успеха
            wait.until(ExpectedConditions.visibilityOfElementLocated(successOrderModal));

            // Проверка отображения сообщения
            return driver.findElement(successOrderModal).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    //Получение текста сообщения об успешном оформлении заказа
    public String getSuccessMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successOrderModal)).getText();
    }

    //Ожидание полной загрузки страницы оформления заказа
    public void waitForOrderPageLoaded() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameField));
    }
}