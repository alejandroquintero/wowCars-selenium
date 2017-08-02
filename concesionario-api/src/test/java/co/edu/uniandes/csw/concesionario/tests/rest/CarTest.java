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
package co.edu.uniandes.csw.concesionario.tests.rest;

import co.edu.uniandes.csw.auth.conexions.AuthenticationApi;
import co.edu.uniandes.csw.auth.model.UserDTO;
import co.edu.uniandes.csw.concesionario.entities.CarEntity;
import co.edu.uniandes.csw.concesionario.entities.BrandEntity;
import co.edu.uniandes.csw.concesionario.dtos.detail.CarDetailDTO;
import co.edu.uniandes.csw.concesionario.resources.CarResource;
import co.edu.uniandes.csw.concesionario.tests.Utils;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.inject.Inject;
import javax.transaction.UserTransaction;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/*
 * Testing URI: brands/{carId: \\d+}/car/
 */
@RunWith(Arquillian.class)
public class CarTest {

    private WebTarget target;
    private final String apiPath = Utils.apiPath;
    private final String username = Utils.username;
    private final String password = Utils.password;
    PodamFactory factory = new PodamFactoryImpl();

    private final int Ok = Status.OK.getStatusCode();
    private final int Created = Status.CREATED.getStatusCode();
    private final int OkWithoutContent = Status.NO_CONTENT.getStatusCode();

    private final static List<CarEntity> oraculo = new ArrayList<>();
    private  AuthenticationApi auth;

    private final String brandPath = "brands";
    private final String carPath = "car";

    BrandEntity fatherBrandEntity;

    @ArquillianResource
    private URL deploymentURL;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                // Se agrega las dependencias
                .addAsLibraries(Maven.resolver().loadPomFromFile("pom.xml")
                        .importRuntimeDependencies().resolve()
                        .withTransitivity().asFile())
                // Se agregan los compilados de los paquetes de servicios
                .addPackage(CarResource.class.getPackage())
                .addPackage("co.edu.uniandes.csw.auth.properties")
                // El archivo que contiene la configuracion a la base de datos.
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                // El archivo beans.xml es necesario para injeccion de dependencias.
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"))
                
                // El archivo web.xml es necesario para el despliegue de los servlets
                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"));
    }

    private WebTarget createWebTarget() {
        return ClientBuilder.newClient().target(deploymentURL.toString()).path(apiPath);
    }

    @PersistenceContext(unitName = "ConcesionarioPU")
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private void clearData() {
        em.createQuery("delete from CarEntity").executeUpdate();
        em.createQuery("delete from BrandEntity").executeUpdate();
        oraculo.clear();
    }

   /**
     * Datos iniciales para el correcto funcionamiento de las pruebas.
     *
     * @generated
     */
    public void insertData() {
        fatherBrandEntity = factory.manufacturePojo(BrandEntity.class);
        fatherBrandEntity.setId(1L);
        em.persist(fatherBrandEntity);

        for (int i = 0; i < 3; i++) {            
            CarEntity car = factory.manufacturePojo(CarEntity.class);
            car.setId(i + 1L);
            car.setBrand(fatherBrandEntity);
            em.persist(car);
            oraculo.add(car);
        }
    }

    /**
     * Configuración inicial de la prueba.
     *
     * @generated
     */
    @Before
    public void setUpTest() {
        try {
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        target = createWebTarget()
                .path(brandPath)
                .path(fatherBrandEntity.getId().toString())
                .path(carPath);
    }

    /**
     * Login para poder consultar los diferentes servicios
     *
     * @param username Nombre de usuario
     * @param password Clave del usuario
     * @return Cookie con información de la sesión del usuario
     * @generated
     */
   public String login() throws IOException, UnirestException, JSONException, InterruptedException, ExecutionException { 
        auth=new AuthenticationApi();
        UserDTO user = new UserDTO();
        user.setUserName(auth.getProp().getProperty("username").trim());
        user.setPassword(auth.getProp().getProperty("password").trim());
        JSONObject json = new JSONObject(auth.authenticationToken(user).getBody()); 
        return (String)json.get("id_token");
    }
   
    public String getUsername() throws IOException, UnirestException, JSONException, InterruptedException, ExecutionException{
     auth=new AuthenticationApi();
    return auth.getProp().getProperty("username").trim();
    }

    /**
     * Prueba para crear un Car
     *
     * @generated
     */
    @Test
    public void createCarTest() throws IOException, UnirestException, JSONException, InterruptedException, ExecutionException {
        CarDetailDTO car = factory.manufacturePojo(CarDetailDTO.class);
        String token= login();

        Response response = target
            .request()
             .cookie("username",getUsername())
             .cookie("id_token",token)
            .post(Entity.entity(car, MediaType.APPLICATION_JSON));

        CarDetailDTO  carTest = (CarDetailDTO) response.readEntity(CarDetailDTO.class);

        Assert.assertEquals(Created, response.getStatus());

        Assert.assertEquals(car.getName(), carTest.getName());
        Assert.assertEquals(car.getImage(), carTest.getImage());
        Assert.assertEquals(car.getPrice(), carTest.getPrice());
        Assert.assertEquals(car.getRevisions(), carTest.getRevisions());
        Assert.assertEquals(car.getWarranty(), carTest.getWarranty());

        CarEntity entity = em.find(CarEntity.class, carTest.getId());
        Assert.assertNotNull(entity);
    }

    /**
     * Prueba para consultar un Car
     *
     * @generated
     */
    @Test
    public void getCarByIdTest() throws IOException, UnirestException, JSONException, InterruptedException, ExecutionException {
        String token= login();

        CarDetailDTO carTest = target
            .path(oraculo.get(0).getId().toString())
            .request()
            .cookie("username",getUsername())
            .cookie("id_token",token)
            .get(CarDetailDTO.class);
        
        Assert.assertEquals(carTest.getId(), oraculo.get(0).getId());
        Assert.assertEquals(carTest.getName(), oraculo.get(0).getName());
        Assert.assertEquals(carTest.getImage(), oraculo.get(0).getImage());
        Assert.assertEquals(carTest.getPrice(), oraculo.get(0).getPrice());
        Assert.assertEquals(carTest.getRevisions(), oraculo.get(0).getRevisions());
        Assert.assertEquals(carTest.getWarranty(), oraculo.get(0).getWarranty());
    }

    /**
     * Prueba para consultar la lista de Cars
     *
     * @generated
     */
    @Test
    public void listCarTest() throws IOException, UnirestException, JSONException, InterruptedException, ExecutionException {
         String token= login();

        Response response = target
            .request()
            .cookie("username",getUsername())
            .cookie("id_token",token)
            .get();

        String listCar = response.readEntity(String.class);
        List<CarDetailDTO> listCarTest = new ObjectMapper().readValue(listCar, List.class);
        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(oraculo.size(), listCarTest.size());
    }

    /**
     * Prueba para actualizar un Car
     *
     * @generated
     */
    @Test
    public void updateCarTest() throws IOException, UnirestException, JSONException, InterruptedException, ExecutionException {
        String token= login();
        CarDetailDTO car = new CarDetailDTO(oraculo.get(0));

        CarDetailDTO carChanged = factory.manufacturePojo(CarDetailDTO.class);

        car.setName(carChanged.getName());
        car.setImage(carChanged.getImage());
        car.setPrice(carChanged.getPrice());
        car.setRevisions(carChanged.getRevisions());
        car.setWarranty(carChanged.getWarranty());

        Response response = target
            .path(car.getId().toString())
            .request()
            .cookie("username",getUsername())
            .cookie("id_token",token)
            .put(Entity.entity(car, MediaType.APPLICATION_JSON));

        CarDetailDTO carTest = (CarDetailDTO) response.readEntity(CarDetailDTO.class);

        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(car.getName(), carTest.getName());
        Assert.assertEquals(car.getImage(), carTest.getImage());
        Assert.assertEquals(car.getPrice(), carTest.getPrice());
        Assert.assertEquals(car.getRevisions(), carTest.getRevisions());
        Assert.assertEquals(car.getWarranty(), carTest.getWarranty());
    }

  
}
