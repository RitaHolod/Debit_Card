import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DebitCardPositiveTests {

    private WebDriver driver;

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }
    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }
    @Test
    void happyPathTest() {

        driver.findElement(By.cssSelector("[data-test-id= name ] input")).sendKeys("Илья Ильиф-Петров");
        driver.findElement(By.cssSelector("[data-test-id= phone] input")).sendKeys("+79869010560");
        driver.findElement(By.cssSelector("[data-test-id= agreement]")).click();
        driver.findElement(By.className("button_size_m")).click();

        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id= order-success]")).getText().trim();

        Assertions.assertEquals(expected, actual);



    }
}
