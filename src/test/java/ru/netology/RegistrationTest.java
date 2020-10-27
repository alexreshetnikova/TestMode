package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.UserGenerator.*;

class RegistrationTest {
    @BeforeEach
    void Setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSubmitActiveUser() {
        Registration validValidActiveUser = getValidActiveUser();
        $("[name=login]").setValue(validValidActiveUser.getLogin());
        $("[name=password]").setValue(validValidActiveUser.getPassword());
        $(".button__text").click();
        $(withText("Личный кабинет")).shouldBe(exist);
    }

    @Test
    void shouldNotSubmitBlockedUser() {
        Registration validValidAcBlockedUser = getValidBlockedUser();
        $("[name=login]").setValue(validValidAcBlockedUser.getLogin());
        $("[name=password]").setValue(validValidAcBlockedUser.getPassword());
        $(".button__text").click();
        $(".notification_status_error").waitUntil(visible, 5000);
        $(".notification_visible[data-test-id=error-notification]").shouldHave(matchesText("Ошибка! Пользователь заблокирован"));
    }

    @Test
    void shouldNotSubmitWithIncorrectPassword() {
        Registration userWithIncorrectPassword = getUserWithIncorrectPassword();
        $("[name=login]").setValue(userWithIncorrectPassword.getLogin());
        $("[name=password]").setValue(userWithIncorrectPassword.getPassword());
        $(".button__text").click();
        $(".notification_status_error").waitUntil(visible, 5000);
        $(".notification_visible[data-test-id=error-notification]").shouldHave(matchesText("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldNotSubmitWithIncorrectLogin() {
        Registration userWithIncorrectLogin = getUserWithIncorrectLogin();
        $("[name=login]").setValue(userWithIncorrectLogin.getLogin());
        $("[name=password]").setValue(userWithIncorrectLogin.getPassword());
        $(".button__text").click();
        $(".notification_status_error").waitUntil(visible, 5000);
        $(".notification_visible[data-test-id=error-notification]").shouldHave(matchesText("Ошибка! Неверно указан логин или пароль"));
    }
}