package ru.yandex.practicum.tests;
import org.junit.rules.ExternalResource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

//
public class DriverFactory extends ExternalResource {

    public WebDriver getDriver() {
        return driver;

    }
    public WebDriver driver;

    public void initDriver(){

        if ("firefox".equals(System.getProperty("browser"))){
            startFirefox();
        } else {
            startChrome();
        }
    }

    private void startChrome() {
        driver = new ChromeDriver();
        //настраиваем неявное "ожидание" во время теста
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(util.EnvConfig.IMPLICITY_TIMEOUT));
        driver.manage().window().maximize();
    }

    private void startFirefox() {
        driver = new FirefoxDriver();
        //настраиваем неявное "ожидание" во время теста
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(util.EnvConfig.IMPLICITY_TIMEOUT));
        driver.manage().window().maximize();
    }

    public void before(){
        initDriver();

    }

    public void after(){
        driver.quit();

    }
}
