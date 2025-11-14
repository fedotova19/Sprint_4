package ru.yandex.practicum.tests;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.yandex.practicum.pages.MainPage;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class FaqTests {

    @Rule
    public DriverFactory factory = new DriverFactory();

    private final int questionIndex; // индекс вопроса для клика (0, 1, 2, 3)
    private final String expectedAnswerText; // ожидаемый текст ответа

    public FaqTests(int questionIndex, String expectedAnswerText) {
        this.questionIndex = questionIndex;
        this.expectedAnswerText = expectedAnswerText;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][] {
                {0, "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {1, "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                {2, "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                {3, "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                {4, "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                {5, "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                {6, "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
                {7, "Да, обязательно. Всем самокатов! И Москве, и Московской области."}
        };
    }


    @Test
    public void testFaqQuestionAnswerDisplay() {
        WebDriver driver = factory.getDriver();
        var mainPage = new MainPage(driver);

        // Переход на главную страницу
        mainPage.openMainPage();

        // Получаем объект для работы с FAQ разделом
        var faqPage = mainPage.getFaqPage();

        // Прокручивание страницы к разделу FAQ
        faqPage.scrollToFaqSection();

        // Ожидание загрузки раздела
        faqPage.waitForFaqSectionLoaded();

        // Клик на вопрос с заданным индексом
        faqPage.clickQuestion(questionIndex);

        // Проверка отображения ответа
        assertTrue("Ответ должен отображаться после клика на вопрос",
                faqPage.isAnswerDisplayed(questionIndex));

        // Проверка, что текст соответствует ожидаемому
        String actualAnswerText = faqPage.getAnswerText(questionIndex);
        assertTrue("Текст ответа должен содержать ожидаемую фразу. Актуальный текст: " + actualAnswerText,
                actualAnswerText.contains(expectedAnswerText));
    }
}