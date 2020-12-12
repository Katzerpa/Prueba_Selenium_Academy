package PruebaSelenium;

import org.testng.annotations.Factory;

public class FactoryNexflix {
    @Factory
    public Object[] fatoryMethod(){
        return new Object[]{
                new Prueba_Netflix(),
                new Prueba_Netflix(),
                new Prueba_Netflix()};
    }
}
