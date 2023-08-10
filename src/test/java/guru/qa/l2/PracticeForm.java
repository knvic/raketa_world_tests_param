package guru.qa.l2;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class PracticeForm {

    @BeforeAll
    public static void setUpAll() {

        Configuration.browserSize = "1920x1800";
        Configuration.pageLoadStrategy = "eager";
        Configuration.browser = "chrome";
        Configuration.holdBrowserOpen = true;

    }

    @Test
    void txtForm() {
        open("https://demoqa.com/automation-practice-form");
        $("#firstName").setValue("firstName");
        $("#lastName").setValue("lastName");
        $("#userEmail").setValue("aaa@bbb.cc");
        $("#userNumber").setValue("1234567890");

        $("#gender-radio-1").parent().click();

        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").selectOption("July");
        $(".react-datepicker__year-select").selectOptionByValue("2022");
        $(".react-datepicker__day--027:not(react-datepicker__day--outside-month)") .click();


        $("#subjectsInput").setValue("Chemistry").pressEnter();

        $("#hobbiesWrapper").$(byText("Reading")).click();

       File picture = new File("src/test/resources/img/picture.png");
       $("#uploadPicture").uploadFile(picture);

        $("#currentAddress").setValue("Russia");

        executeJavaScript("$('footer').remove()");

        $("#state").click();
        $("#stateCity-wrapper").$(byText("Uttar Pradesh")).click();

        $("#city").click();
        $("#stateCity-wrapper").$(byText("Agra")).click();

        $("#submit").click();

        $(".modal-dialog").should(Condition.appear);
        $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
        $(".table-responsive").shouldHave(text("firstName"),text("lastName"),text("aaa@bbb.cc"));
    }


}
