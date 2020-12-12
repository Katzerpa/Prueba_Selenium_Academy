package PruebaSelenium;

import org.testng.annotations.DataProvider;

public class DataProvierNexflix {
    @DataProvider(name = "correo electronico")
    public Object[] emailValidos() {
        return new Object[][]{
                {"katzerpa@gmail.com"},
                {"katzerpa@hotmail.com"},
                {"capricornio.zcarrillo@gmail.com"},

        };
    }

}
