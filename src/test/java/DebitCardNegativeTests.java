import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DebitCardNegativeTests {
    private WebDriver driver;

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void testDebitCardInvalidName(){
        driver.findElement(By.cssSelector("[data-test-id= name ] input")).sendKeys("Ilya Petrov");
        driver.findElement(By.cssSelector("[data-test-id= phone] input")).sendKeys("+79869010560");
        driver.findElement(By.cssSelector("[data-test-id= agreement]")).click();
        driver.findElement(By.className("button_size_m")).click();

        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id= name].input_invalid .input__sub")).getText().trim();

        Assertions.assertEquals(expected, actual);
    }


    @Test
    public void testDebitCardEmptyName(){
        driver.findElement(By.cssSelector("[data-test-id= name ] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id= phone] input")).sendKeys("+79869010560");
        driver.findElement(By.cssSelector("[data-test-id= agreement]")).click();
        driver.findElement(By.className("button_size_m")).click();

        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id= name].input_invalid .input__sub")).getText().trim();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testDebitCardNumbersInName(){
        driver.findElement(By.cssSelector("[data-test-id= name ] input")).sendKeys("1234567890");
        driver.findElement(By.cssSelector("[data-test-id= phone] input")).sendKeys("+79869010560");
        driver.findElement(By.cssSelector("[data-test-id= agreement]")).click();
        driver.findElement(By.className("button_size_m")).click();

        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id= name].input_invalid .input__sub")).getText().trim();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testDebitCardLettersInPhone(){
        driver.findElement(By.cssSelector("[data-test-id= name ] input")).sendKeys("Илья Ильиф-Петров");
        driver.findElement(By.cssSelector("[data-test-id= phone] input")).sendKeys("Илья Ильиф-Петров");
        driver.findElement(By.cssSelector("[data-test-id= agreement]")).click();
        driver.findElement(By.className("button_size_m")).click();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id= phone].input_invalid .input__sub")).getText().trim();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testDebitCardLongPhone(){
        driver.findElement(By.cssSelector("[data-test-id= name ] input")).sendKeys("Илья Ильиф-Петров");
        driver.findElement(By.cssSelector("[data-test-id= phone] input")).sendKeys("+123456789012");
        driver.findElement(By.cssSelector("[data-test-id= agreement]")).click();
        driver.findElement(By.className("button_size_m")).click();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id= phone].input_invalid .input__sub")).getText().trim();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testDebitWithoutPlusPhone(){
        driver.findElement(By.cssSelector("[data-test-id= name ] input")).sendKeys("Илья Ильиф-Петров");
        driver.findElement(By.cssSelector("[data-test-id= phone] input")).sendKeys("79869010560");
        driver.findElement(By.cssSelector("[data-test-id= agreement]")).click();
        driver.findElement(By.className("button_size_m")).click();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id= phone].input_invalid .input__sub")).getText().trim();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testDebitEmptyPhone(){
        driver.findElement(By.cssSelector("[data-test-id= name ] input")).sendKeys("Илья Ильиф-Петров");
        driver.findElement(By.cssSelector("[data-test-id= phone] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id= agreement]")).click();
        driver.findElement(By.className("button_size_m")).click();

        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id= phone].input_invalid .input__sub")).getText().trim();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testDebitEmptyCheckbox(){
        driver.findElement(By.cssSelector("[data-test-id= name ] input")).sendKeys("Илья Ильиф-Петров");
        driver.findElement(By.cssSelector("[data-test-id= phone] input")).sendKeys("+79869010560");
        //driver.findElement(By.cssSelector("[data-test-id= agreement]")).click();
        driver.findElement(By.className("button_size_m")).click();

        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        String actual = driver.findElement(By.cssSelector("[data-test-id= agreement].input_invalid .checkbox__text")).getText().trim();

        Assertions.assertEquals(expected, actual);
    }
}