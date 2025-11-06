package ru.yandex.practicum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

// Класс для работы с разделом "Вопросы о важном" (FAQ)
public class FaqPage {

    // Драйвер для управления браузером
    private final WebDriver driver;
    // Ожидание
    private final WebDriverWait wait;


    private By faqSectionTitle = By.className("Home_SubHeader__zwi_E");
    // Кнопки вопросов (стрелочки)
    private By questionButtons = By.cssSelector("[data-accordion-component='AccordionItemButton']");
    private By answerTexts = By.cssSelector("[data-accordion-component='AccordionItemPanel']");

    public FaqPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // ожидание с таймаутом 10 секунд
    }

    public void scrollToFaqSection() {
        WebElement faqElement = wait.until(ExpectedConditions.visibilityOfElementLocated(faqSectionTitle));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", faqElement);
    }

    public int getQuestionsCount() {
        return driver.findElements(questionButtons).size(); // находим все элементы вопросов и возвращаем их количество
    }


    public void clickQuestion(int index) {
        // Ждем загрузки всех вопросов
        List<WebElement> questions = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(questionButtons));

        if (index >= 0 && index < questions.size()) {
            WebElement question = questions.get(index);

            // Прокручиваем к вопросу
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", question);

            // Ждем кликабельности
            WebElement clickableQuestion = wait.until(ExpectedConditions.elementToBeClickable(question));
            clickableQuestion.click();

            // Ожидание появления ответа
            waitForAnswerToBeVisible(index);
        }
    }

    private void waitForAnswerToBeVisible(int index) {
        List<WebElement> answers = driver.findElements(answerTexts);
        if (index >= 0 && index < answers.size()) {
            // Ожидание пока конкретный ответ станет видимым на странице
            wait.until(ExpectedConditions.visibilityOf(answers.get(index)));
        }
    }

    public String getAnswerText(int index) {
        List<WebElement> answers = driver.findElements(answerTexts);
        if (index >= 0 && index < answers.size()) {
            // Возвращаем текст ответа
            return answers.get(index).getText();
        }
        return "";
    }

    public boolean isAnswerDisplayed(int index) {
        List<WebElement> answers = driver.findElements(answerTexts);
        if (index >= 0 && index < answers.size()) {
            WebElement answer = answers.get(index);
            // Проверяем что ответ видим и содержит текст
            return answer.isDisplayed() && !answer.getText().isEmpty();
        }
        return false;
    }

    // Ожидание полной загрузки FAQ раздела
    public void waitForFaqSectionLoaded() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(faqSectionTitle));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(questionButtons, 0));
    }
}