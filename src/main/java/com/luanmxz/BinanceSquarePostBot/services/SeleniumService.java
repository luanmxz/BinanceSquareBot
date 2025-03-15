package com.luanmxz.BinanceSquarePostBot.services;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.luanmxz.BinanceSquarePostBot.infrastructure.SeleniumHandler;

import io.github.cdimascio.dotenv.Dotenv;

public class SeleniumService {

        private static final Dotenv dotenv = Dotenv.load();
        private final SeleniumHandler seleniumHandler;
        private final WebDriver webDriver;

        public SeleniumService(SeleniumHandler seleniumHandler) {
                this.seleniumHandler = seleniumHandler;
                webDriver = seleniumHandler.createWebDriver();
        }

        public void execute() {
                webDriver.get("https://www.binance.com/pt-BR/square");

                login();

        }

        private void login() {

                WebDriverWait wait = seleniumHandler.createWait(webDriver);

                WebElement loginButton = wait.until(ExpectedConditions
                                .elementToBeClickable(By.xpath(
                                                "/html/body/div[1]/div[1]/div[1]/div/div/div[2]/div[1]/div[1]/button")));

                loginButton.click();

                WebElement emailInput = wait
                                .until(ExpectedConditions.elementToBeClickable(By.name("username")));
                emailInput.sendKeys(dotenv.get("BINANCE_EMAIL"));

                WebElement nextButton = wait.until(
                                ExpectedConditions.elementToBeClickable(
                                                By.xpath("/html/body/div[2]/div/main/div/div[1]/form/button")));
                nextButton.click();

                WebElement passInput = wait
                                .until(ExpectedConditions.elementToBeClickable(By.name("password")));
                passInput.sendKeys(dotenv.get("BINANCE_PASSWORD"));

                nextButton = wait.until(
                                ExpectedConditions.elementToBeClickable(
                                                By.xpath("/html/body/div[2]/div/main/div/div/form/button")));

                nextButton.click();

                try {
                        WebElement googleAuthCodeField = webDriver.findElements(By.cssSelector(".mfa-option-box"))
                                        .get(0);

                        googleAuthCodeField.click();
                } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                        WebElement doAuthWithPasskeysButton = wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath("/html/body/div[2]/div/main/div/div/div[2]/div/div/div/div[5]/div")));
                        doAuthWithPasskeysButton.click();

                        WebElement googleAuthCodeFieldRetry = webDriver.findElements(By.cssSelector(".mfa-option-box"))
                                        .get(0);

                        googleAuthCodeFieldRetry.click();
                }

                /*
                 * WebElement googleAuthCodeInput =
                 * wait.until(ExpectedConditions.elementToBeClickable(
                 * By.tagName("input")));
                 * googleAuthCodeInput.sendKeys("123");
                 * 
                 * WebElement googleAuthSendButton =
                 * wait.until(ExpectedConditions.elementToBeClickable(
                 * By.linkText("Enviar")));
                 * 
                 * googleAuthSendButton.click();
                 * 
                 * WebElement phoneNumberAuthField = wait
                 * .until(ExpectedConditions.elementToBeClickable(By.
                 * linkText("Número de Telefone")));
                 * phoneNumberAuthField.click();
                 * 
                 * WebElement phoneNumberAuthCodeInput =
                 * wait.until(ExpectedConditions.elementToBeClickable(
                 * By.tagName("input")));
                 * phoneNumberAuthCodeInput.sendKeys("123");
                 * 
                 * WebElement phoneNumberAuthSendButton =
                 * wait.until(ExpectedConditions.elementToBeClickable(
                 * By.linkText("Enviar")));
                 * 
                 * phoneNumberAuthSendButton.click();
                 */
        }
}
