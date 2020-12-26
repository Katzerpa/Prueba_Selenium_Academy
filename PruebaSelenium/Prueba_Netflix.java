package PruebaSelenium;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.GetProperties;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;



public class Prueba_Netflix {

    public static WebDriver driver;
    private static String URL = "https://www.netflix.com/";
    Faker faker1 = new Faker(new Random(24));
    @BeforeMethod
    public void setUp() {
        GetProperties properties = new GetProperties();
        String chromeURL = properties.getString("CHROMEDRIVER_PATH");
        System.setProperty("webdriver.chrome.driver", chromeURL);
        driver = new ChromeDriver();
        driver.get(URL);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

    }

    @Test(groups = "successTests")
    public void validarTituloTest() {
        System.out.println(driver.getTitle());
        Assert.assertEquals(driver.getTitle(), "Netflix Argentina: Ve series online, ve películas online");
    }

    @Test(groups = "successTests")
    public void iniciarSesionPageTest() {
        driver.findElement(By.partialLinkText("Inicia")).click();
        Assert.assertNotEquals(driver.getTitle(), "Netflix Argentina: Ve series online, ve películas online");
        List<WebElement> listh1 = driver.findElements(By.tagName("h1"));
        String tituloInicio = "";
        for (WebElement elemento : listh1) {
            if (!!elemento.getText().isEmpty()) {
                continue;

            }
            System.out.println("--->" + elemento.getText());
            if (elemento.getText().equalsIgnoreCase("Inicia sesión")) {
                tituloInicio = elemento.getText();

            }
        }
        Assert.assertEquals(tituloInicio, "Inicia sesión");
        WebElement texFacebook = driver.findElement(By.xpath("//span[@class='fbBtnText']"));
        Assert.assertTrue(texFacebook.isDisplayed());

    }

    @Test(groups = "failTests")
    public void loginToNetflixErrorTest() {
        driver.findElement(By.partialLinkText("Inicia")).click();
        Assert.assertNotEquals(driver.getTitle(), "Netflix Argentina: Ve series online, ve películas online");
        driver.findElement(By.name("userLoginId")).sendKeys("tes@tes.com");
        driver.findElement(By.name("password")).sendKeys("Hola mundo");
        WebElement recuerdame = driver.findElement(By.xpath("//span[@class='login-remember-me-label-text']"));
        recuerdame.click();
        driver.findElement(By.xpath("//button[@class='btn login-button btn-submit btn-small']")).click();
        WebElement msjErrorContraseña = driver.findElement(By.xpath("//div[@data-uia='text']"));
        System.out.println(msjErrorContraseña.getText());
        WebElement clickRecuerdame = driver.findElement(By.xpath("//input[@type='checkbox']"));
        Assert.assertTrue(clickRecuerdame.isSelected());

    }

    @Test(groups = "successTests")
    public void fakeEmailTest(){
        driver.findElement(By.id("id_email_hero_fuji")).sendKeys(faker1.internet().emailAddress());
        driver.findElement(By.xpath("//button[@class='btn btn-red nmhp-cta nmhp-cta-extra-large btn-none btn-custom']")).click();

    }

    @Test(dataProvider = "correo electronico", dataProviderClass = DataProvierNexflix.class,groups = "successTests")
    public void dataProviderEmailTest(String unEmaill){
        driver.findElement(By.id("id_email_hero_fuji")).sendKeys(unEmaill);
        driver.findElement(By.xpath("//button[@class='btn btn-red nmhp-cta nmhp-cta-extra-large btn-none btn-custom']")).click();

    }




    @AfterMethod
    public void closedDriver() throws InterruptedException {
        Thread.sleep(2000);
         driver.quit();
    }
}
