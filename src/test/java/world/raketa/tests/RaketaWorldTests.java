package world.raketa.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import world.raketa.pages.*;
import java.util.List;
import java.util.stream.Stream;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

@Epic("Ракета")
@Feature("Первоначальное тестирование")
@Owner("krivorotovnv")
@Link(value = "Testing", url = "https://raketa.world")
@Severity(SeverityLevel.BLOCKER)
public class RaketaWorldTests extends BaseTest {
    MainPage mainPage = new MainPage();
    CompanyPage companyPage = new CompanyPage();
    CareerPage careerPage = new CareerPage();
    VacancyListPage vacancyListPage = new VacancyListPage();
    VacancyQAPage vacancyQAPage = new VacancyQAPage();


    private static int count = 0;


    static Stream<Arguments> changeLocaleTest() {
        return Stream.of(
                Arguments.of(Locale.RU, List.of("КОМПАНИЯ", "ПРОДУКТЫ", "КЛИЕНТАМ", "СТАТЬ КЛИЕНТОМ", "КОНТАКТЫ", "ВОЙТИ")),
                Arguments.of(Locale.EN, List.of("COMPANY", "PRODUCTS", "FOR CLIENTS", "GET STARTED", "CONTACTS", "LOGIN"))
        );
    }
    @Story("Переключение локали")
    @DisplayName("Параметризованный тест проверки наличия списка основного меню в разных локалях")
    @MethodSource("changeLocaleTest")
    @Tag("raketa")
    @ParameterizedTest(name = "Проверка меню сайта при перелючении локали на {0} отображается меню {1}")
    void changeLocaleTest(Locale locale, List<String> list) {
        step("Открытие сайта", () -> {
            mainPage
                    .openPage();
        });

        step("Выбор локали {0}", () -> {
            mainPage
                    .setLocale(locale);

        });

        step("Проверка наличия элементов меню согласно списка, а,так же, соответствия с выбранным языком", () -> {
            mainPage
                    .shouldHaveTargetMenu(list);
        });
    }

    static Stream<Arguments> checkingСompositionMenu() {
        return Stream.of(
                Arguments.of("КОМПАНИЯ", List.of("О компании", "Команда", "Медиацентр", "Карьера", "Контакты")),
                Arguments.of("ПРОДУКТЫ", List.of("Цифровая платформа для командировок", "Мобильное приложение для бизнес-путешественников", "Управление персональными и корпоративными данными", "Управление расходами", "Автопарк")),
                Arguments.of("КЛИЕНТАМ", List.of("Бизнесу", "Государственным компаниям", "Интеграции", "Безопасность", "Получить межгалактический паспорт"))

        );
    }
    @Story("Соответствие элементов верхнего меню")
    @DisplayName("Параметризованный тест проверки списка элементов выпадающего меню при наведении на элементы основного меню.")
    @MethodSource("checkingСompositionMenu")
    @Tag("raketa")
    @ParameterizedTest(name = "Проверка наличия выпадающего списка элементов. При наведениина пункт меню  {0} отображается элементы списка {1}")
    void checkingСompositionMenu(String item, List<String> list) {
        step("Открытие сайта", () -> {
            mainPage
                    .openPage();
        });

        step("Ожидаем корректной загрузки стартовой страницы (появление определенного текста) ", () -> {
            mainPage
                    .waitingForTheSiteToLoad();
        });

        step("Проверка элементов выпадающего меню соответствию списка.", () -> {
            mainPage
                    .checkElementsPullDownMenu(list, count, item);
        });
        count++;


    }

    @ValueSource(
            strings = {
                    "QA automation engineer",
                    "QA Lead"
            }
    )
    @Story("Проверка наличия вакансий на странице вакансий")
    @DisplayName("Тест проверки ссылки на страницу вакансий и вакансий на странице согласно списка")
    @Tags({
            @Tag("raketa")
    })
    @ParameterizedTest(name = "Проверка наличия вакансии  =>  {0}")
    void availabilityLinkToVacanciesAndListVacancy(String vacancy) {
        step("Открытие сайта", () -> {
            mainPage
                    .openPage();
        });
        step("Ожидаем корректной загрузки стартовой страницы (появление определенного текста) ", () -> {
            mainPage
                    .waitingForTheSiteToLoad();
        });

        step("Первый пункт основного меню. В выпадающем списке находим Карьера, проверяем видимость и выбираем", () -> {
            mainPage
                    .career();
        });
        step("Ожидаем корректной загрузки страницы Карьера (появление определенного текста) ", () -> {
            careerPage
                    .waitingForThePageCareerLoad();
        });

        step("Проверка наличия ссылки на вакансии", () -> {
            careerPage
                    .linkVacancyEnabled();
        });

        step("После перехода по ссылке на страницу вакансии открывается новое окно, а фокус оствется на другом. Ищем windowHandle окон, закрываем не нужное, переходим на окно с вакансиями", () -> {
            careerPage
                    .selectTargetWindows();
        });

        step("Проверка наличия ссылки на вакансии QA automation engineer);", () -> {
            vacancyListPage
                    .findVacancy(vacancy);
        });


    }


    @Story("Проверка наличия главного условия в работе QA automation engineer")
    @Link(value = "Testing", url = "https://job.raketa.world/qaautomation")
    @DisplayName("Тестирование наличия опции Йога в обед в условиях работы")
    @Tags({
            @Tag("raketa"),
            @Tag("smoke"),

    })
    @Test
    void mainConditionOfWork() {
        step("Открытие сайта на странице карьера", () -> {
            open("https://job.raketa.world/qaautomation");
        });

        step("Поиск ключевого условия Йога в обед в предложениях вакансии", () -> {
            vacancyQAPage
                    .yogaAtLunch();
        });


    }


    @Story("Заполнение формы обратной связи")
    @DisplayName("Проверка выбора меню КОПМАНИЯ, вызова окна для ввода данных для связи, возможности ввода данных.")
    @Tags({
            @Tag("raketa")
    })
    @Test
    void checkingFeedbackForm() {
        step("Открытие сайта", () -> {
            mainPage
                    .openPage();
        });
        step("Ожидаем корректной загрузки стартовой страницы (появление определенного текста) ", () -> {
            mainPage
                    .waitingForTheSiteToLoad();
        });

        step("Находим пункт меню КОМПАНИЯ и кликаем на него", () -> {
            mainPage
                    .goToTheMenuItemCompany();
        });
        step("Ожидаем корректной загрузки страницы Компания и наличия в нем определенного текста ", () -> {
            companyPage
                    .waitingForTheSiteCompanyToLoad();
        });

        step("Нажимаем на кнопку вызова окна ввода данных для связи ", () -> {
            companyPage
                    .callingInpitTab();
        });

        step("Проверяем загрузку и видимость окна", () -> {
            companyPage
                    .checkVisibleInpitTab();
        });

        step("Заполняем поля формы. Поле email вводим не правильно, чтобы форма не отправлялась и жмем отправить", () -> {
            companyPage
                    .fillingTheForm();
        });

        step("Так как заполнено не корректно, проверяем, что форма видна ", () -> {
            companyPage
                    .checkVisibleInpitTab();
        });

    }

}
