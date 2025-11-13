package ru.yandex.practicum.tests;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.yandex.practicum.pages.MainPage;
import ru.yandex.practicum.pages.OrderPage;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderTests {

    @Rule
    public DriverFactory factory = new DriverFactory();

    private final String testName;
    private final String name;
    private final String surname;
    private final String address;
    private final String phone;
    private final String rentalPeriod;
    private final String color;
    private final String comment;
    private final String orderButtonType;

    public OrderTests(String testName, String name, String surname, String address,
                      String phone, String rentalPeriod, String color, String comment, String orderButtonType) {
        this.testName = testName;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phone = phone;
        this.rentalPeriod = rentalPeriod;
        this.color = color;
        this.comment = comment;
        this.orderButtonType = orderButtonType;
    }

    @Parameterized.Parameters(name = "{0} через {8} кнопку")
    public static Object[][] getOrderData() {
        return new Object[][] {
                {
                        "Заказ черного самоката",
                        "Иван",
                        "Иванов",
                        "ул. Ленина, д. 1",
                        "+79991234567",
                        "сутки",
                        "black",
                        "Позвонить за час до доставки",
                        "top"
                },
                {
                        "Заказ серого самоката",
                        "Петр",
                        "Петров",
                        "ул. Кирова, д. 25, кв. 12",
                        "+79997654321",
                        "двое суток",
                        "grey",
                        "Оставить у двери",
                        "bottom"
                }
        };
    }

    @Test
    public void testOrderScooterPositiveFlow() {
        WebDriver driver = factory.getDriver();
        var mainPage = new MainPage(driver);

        // Переход на главную страницу
        mainPage.openMainPage();

        // Выбор кнопки
        OrderPage orderPage;
        if ("top".equals(orderButtonType)) {
            orderPage = mainPage.clickOrderButtonTop();
        } else {
            orderPage = mainPage.clickOrderButtonBottom();
        }

        // Ожидание загрузки страницы
        orderPage.waitForOrderPageLoaded();

        // Заполнение первой страницы заказа
        orderPage.fillFirstPage(name, surname, address, "Сокольники", phone);

        // Переход на вторую страницу
        orderPage.clickNextButton();

        // Заполнение второй страницы
        orderPage.fillSecondPage("завтра", rentalPeriod, color, comment);

        // Клик на кнопку Заказать
        orderPage.clickOrderButton();

        // Подтверждение заказа
        orderPage.confirmOrder();

        // Проверка успешного создания заказа
        assertTrue("Должно отображаться сообщение об успешном создании заказа",
                orderPage.isOrderSuccess());

        String successMessage = orderPage.getSuccessMessage();
        assertTrue("Сообщение должно содержать информацию о заказе",
                successMessage.contains("Заказ") || successMessage.contains("Номер"));
    }
}