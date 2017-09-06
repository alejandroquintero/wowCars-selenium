/*
The MIT License (MIT)

Copyright (c) 2015 Los Andes University

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package co.edu.uniandes.csw.concesionario.tests.selenium;

import co.edu.uniandes.csw.concesionario.dtos.minimum.ClientDTO;
import co.edu.uniandes.csw.concesionario.resources.ClientResource;
import co.edu.uniandes.csw.auth.conexions.AuthenticationApi;
import co.edu.uniandes.csw.concesionario.resources.CarResource;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.thoughtworks.selenium.Wait;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import static java.util.concurrent.TimeUnit.SECONDS;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.glassfish.pfl.basic.tools.argparser.ElementParser.factory;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.page.InitialPage;
import org.jboss.arquillian.graphene.page.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import static org.hamcrest.core.Is.is;
import org.jboss.arquillian.container.test.api.RunAsClient;
import static org.jboss.arquillian.graphene.Graphene.guardAjax;
import static org.jboss.arquillian.graphene.Graphene.waitGui;
import static org.jboss.arquillian.graphene.Graphene.waitModel;
import org.jboss.arquillian.graphene.wait.FluentBuilder;
import org.jboss.arquillian.graphene.wait.FluentWait;
import org.jboss.arquillian.graphene.wait.WebDriverWait;
import org.junit.After;
import static org.junit.Assert.assertThat;

@RunWith(Arquillian.class)
public class ClientIT {
    
    
     @ArquillianResource
    private URL deploymentURL;

 @Drone
  private WebDriver driver;
 
 private static PodamFactory factory = new PodamFactoryImpl();

 
 
 private static  Properties prop;
    private static InputStream input = null;
    private static final String path = System.getenv("AUTH0_PROPERTIES");

static {
            prop = new Properties();
        try {
            input =  new FileInputStream(path);
            prop.load(input);
            
        } catch (FileNotFoundException ex) {
            Logger.getAnonymousLogger().info("no se encontro archivo");
        } catch (IOException ex) {
            Logger.getAnonymousLogger().info("no se encontro archivo");
        }
           
    }
 
     @Deployment(testable = true)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                // Se agrega las dependencias
                .addAsLibraries(Maven.resolver().loadPomFromFile("pom.xml")
                        .importRuntimeDependencies().resolve()
                        .withTransitivity().asFile())
                // Se agregan los compilados de los paquetes de servicios
                .addPackage(ClientResource.class.getPackage())
                 // archivo de propiedades para autenticacion de auth0
                .addPackage("co.edu.uniandes.csw.auth.properties")
                // El archivo que contiene la configuracion a la base de datos.
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                // El archivo beans.xml es necesario para injeccion de dependencias.
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"))
                // El archivo web.xml es necesario para el despliegue de los servlets
                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"))
                .merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class)
                        .importDirectory("src/main/webapp").as(GenericArchive.class), "/");
    }
@Before
public void setup() throws InterruptedException{
try {
             driver = new RemoteWebDriver(
                     new URL("http://localhost:4445/wd/hub"), DesiredCapabilities.chrome()
             );
         } catch (MalformedURLException ex) {
             Logger.getLogger(BrandIT.class.getName()).log(Level.SEVERE, null, ex);
         }  
    login();
}
@After
public void unload(){

driver.quit();
}

public  void login() throws InterruptedException{
    
        driver.manage().window().maximize(); 
        driver.get(deploymentURL.toExternalForm()+"#/login");   
        driver.manage().deleteAllCookies();
        WebElement usernameInput = driver.findElement(By.id("username-input"));
        WebElement passwordInput = driver.findElement(By.id("password-input"));
        WebElement registerBtn = driver.findElement(By.id("log-in-btn"));
        waitModel().until().element(usernameInput).is().visible();
        usernameInput.clear();
        passwordInput.clear();
        usernameInput.sendKeys(prop.getProperty("username").trim());
        passwordInput.sendKeys(prop.getProperty("password").trim()); 
        registerBtn.click();
      
            
}


    @Test
    @InSequence(0)
    @RunAsClient
    public void createClient() throws InterruptedException  {
      
      
       Logger.getAnonymousLogger().info("waiting");
       driver.manage().timeouts().implicitlyWait(5, SECONDS);
       Integer expected = 0;
         WebElement brandRefresh = driver.findElement(By.id("refresh-brand"));
       waitModel().until().element(brandRefresh).is().visible();
       brandRefresh.click();
      WebElement clients = driver.findElement(By.id("clients"));
      waitModel().until().element(clients).is().visible();
      clients.click();
       Integer countClients = driver.findElements(By.cssSelector("tbody > tr")).size();
       Assert.assertEquals(expected,countClients);
       
       WebElement createBtn = driver.findElement(By.id("create-client"));
       waitModel().until().element(createBtn).is().visible();
       createBtn.click();
       ClientDTO expected_client = factory.manufacturePojo(ClientDTO.class);
       WebElement nameInput = driver.findElement(By.id("name"));
       WebElement emailInput = driver.findElement(By.id("email"));
       WebElement phoneInput = driver.findElement(By.id("phone"));
       WebElement saveBtn = driver.findElement(By.id("save-client"));
       waitGui().until().element(nameInput).is().visible();
       nameInput.clear();
       emailInput.clear();
       phoneInput.clear();
       nameInput.sendKeys(expected_client.getName());
       emailInput.sendKeys(expected_client.getEmail());
       phoneInput.sendKeys(expected_client.getPhone().toString());
       saveBtn.click();
       WebElement  nameDetail = driver.findElement(By.id("name-detail")); 
       waitGui().until().element(nameDetail).is().visible();     
       ClientDTO actual_client = new ClientDTO();
       actual_client.setName(nameDetail.getText());
       Assert.assertEquals(expected_client.getName(), actual_client.getName());
      
    }
 
    @Test
   @InSequence(1)
    @RunAsClient
    public void editClient() throws InterruptedException {
        
       Logger.getAnonymousLogger().info("waiting");
       driver.manage().timeouts().implicitlyWait(5, SECONDS);
        ClientDTO expected_client = factory.manufacturePojo(ClientDTO.class);
         WebElement brandRefresh = driver.findElement(By.id("refresh-brand"));
       waitModel().until().element(brandRefresh).is().visible();
       brandRefresh.click();
      WebElement clients = driver.findElement(By.id("clients"));
      waitModel().until().element(clients).is().visible();
      clients.click();
        WebElement editBtn = driver.findElement(By.id("0-edit-btn"));
        waitGui().until().element(editBtn).is().visible();
        editBtn.click();
        WebElement nameEdit = driver.findElement(By.id("name-edit"));
        WebElement emailEdit = driver.findElement(By.id("email-edit"));
        WebElement phoneEdit = driver.findElement(By.id("phone-edit"));
        waitGui().until().element(nameEdit).is().visible();
        WebElement saveBtn = driver.findElement(By.id("save-client"));
        nameEdit.clear();
        emailEdit.clear();
        phoneEdit.clear();
        nameEdit.sendKeys(expected_client.getName());
        emailEdit.sendKeys(expected_client.getEmail());
        phoneEdit.sendKeys(expected_client.getPhone().toString());
        saveBtn.click();
         WebElement  nameDetail = driver.findElement(By.id("name-detail")); 
       waitGui().until().element(nameDetail).is().visible(); 
        ClientDTO actual_client = new ClientDTO();
       actual_client.setName(nameDetail.getText());
       Assert.assertEquals(expected_client.getName(), actual_client.getName());
    }

    @Test
    @InSequence(2)
    @RunAsClient
    public void deleteClient() throws InterruptedException {
       
       Logger.getAnonymousLogger().info("waiting");
       driver.manage().timeouts().implicitlyWait(5, SECONDS);
        WebElement brandRefresh = driver.findElement(By.id("refresh-brand"));
       waitModel().until().element(brandRefresh).is().visible();
       brandRefresh.click();
      WebElement clients = driver.findElement(By.id("clients"));
      waitModel().until().element(clients).is().visible();
      clients.click();
       WebElement deleteBtn = driver.findElement(By.id("0-delete-btn"));
        waitGui().until().element(deleteBtn).is().visible();
        deleteBtn.click();
        WebElement confirmDel = driver.findElement(By.id("confirm-delete"));
       waitGui().until().element(confirmDel).is().visible();
        confirmDel.click();
        Integer expected = 0;
        Integer countClients = driver.findElements(By.cssSelector("tbody > tr")).size();
        Assert.assertEquals(expected, countClients);
    }

}