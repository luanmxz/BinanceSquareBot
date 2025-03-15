package com.luanmxz.BinanceSquarePostBot.infrastructure;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SeleniumHandler {

    public WebDriver createWebDriver() {
        WebDriverManager.firefoxdriver().setup();

        FirefoxOptions options = createOptions();
        WebDriver driver = new FirefoxDriver(options);
        driver.manage().window().maximize();

        return driver;
    }

    public WebDriverWait createWait(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15), Duration.ofSeconds(1));
        return wait;
    }

    public WebDriverWait createWait(WebDriver driver, Duration timeout, Duration interpolling) {
        WebDriverWait wait = new WebDriverWait(driver, timeout, interpolling);
        return wait;
    }

    private FirefoxOptions createOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options = setBrowserProfile(options);
        options.addArguments("--disable-blink-features=AutomationControlled");

        return options;
    }

    private FirefoxOptions setBrowserProfile(FirefoxOptions opts) {
        ProfilesIni profiles = new ProfilesIni();
        FirefoxProfile profile = profiles.getProfile("default-release");
        opts.setProfile(profile);
        return opts;
    }

    public void shutdown(WebDriver webDriver) {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
