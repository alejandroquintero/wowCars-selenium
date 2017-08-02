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
package co.edu.uniandes.csw.concesionario.test.logic;

import co.edu.uniandes.csw.concesionario.ejbs.CarLogic;
import co.edu.uniandes.csw.concesionario.api.ICarLogic;
import co.edu.uniandes.csw.concesionario.entities.CarEntity;
import co.edu.uniandes.csw.concesionario.entities.BrandEntity;
import co.edu.uniandes.csw.concesionario.persistence.CarPersistence;
import co.edu.uniandes.csw.concesionario.entities.CategoryEntity;
import co.edu.uniandes.csw.concesionario.entities.BrandEntity;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.junit.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class CarLogicTest {

    /**
     * @generated
     */
    BrandEntity fatherEntity;

    /**
     * @generated
     */
    private PodamFactory factory = new PodamFactoryImpl();

    /**
     * @generated
     */
    @Inject
    private ICarLogic carLogic;

    /**
     * @generated
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * @generated
     */
    @Inject
    private UserTransaction utx;

    /**
     * @generated
     */
    private List<CarEntity> data = new ArrayList<CarEntity>();
    /**
     * @generated
     */
    private List<CategoryEntity> categoryData = new ArrayList<>();
    /**
     * @generated
     */
    private List<BrandEntity> brandData = new ArrayList<>();

    /**
     * @generated
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CarEntity.class.getPackage())
                .addPackage(CarLogic.class.getPackage())
                .addPackage(ICarLogic.class.getPackage())
                .addPackage(CarPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Configuración inicial de la prueba.
     *
     * @generated
     */
    @Before
    public void configTest() {
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
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     *
     * @generated
     */
    private void clearData() {
        em.createQuery("delete from CarEntity").executeUpdate();
        em.createQuery("delete from CategoryEntity").executeUpdate();
        em.createQuery("delete from BrandEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     *
     * @generated
     */
    private void insertData() {
            for (int i = 0; i < 3; i++) {
                CategoryEntity category = factory.manufacturePojo(CategoryEntity.class);
                em.persist(category);
                categoryData.add(category);
            }
    
            fatherEntity = factory.manufacturePojo(BrandEntity.class);
            fatherEntity.setId(1L);
            em.persist(fatherEntity);
        for (int i = 0; i < 3; i++) {
            CarEntity entity = factory.manufacturePojo(CarEntity.class);
                entity.setBrand(fatherEntity);
    
                entity.setCategory(categoryData.get(0));

            em.persist(entity);
            data.add(entity);
        }
    }
   /**
     * Prueba para crear un Car
     *
     * @generated
     */
    @Test
    public void createCarTest() {
        CarEntity newEntity = factory.manufacturePojo(CarEntity.class);
        CarEntity result = carLogic.createCar(fatherEntity.getId(), newEntity);
        Assert.assertNotNull(result);
        CarEntity entity = em.find(CarEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
        Assert.assertEquals(newEntity.getImage(), entity.getImage());
        Assert.assertEquals(newEntity.getPrice(), entity.getPrice());
        Assert.assertEquals(newEntity.getRevisions(), entity.getRevisions());
        Assert.assertEquals(newEntity.getWarranty(), entity.getWarranty());
    }

    /**
     * Prueba para consultar la lista de Cars
     *
     * @generated
     */
    @Test
    public void getCarsTest() {
        List<CarEntity> list = carLogic.getCars(fatherEntity.getId());
        Assert.assertEquals(data.size(), list.size());
        for (CarEntity entity : list) {
            boolean found = false;
            for (CarEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    
    /**
     * Prueba para consultar un Car
     *
     * @generated
     */
    @Test
    public void getCarTest() {
        CarEntity entity = data.get(0);
        CarEntity resultEntity = carLogic.getCar(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getName(), resultEntity.getName());
        Assert.assertEquals(entity.getImage(), resultEntity.getImage());
        Assert.assertEquals(entity.getPrice(), resultEntity.getPrice());
        Assert.assertEquals(entity.getRevisions(), resultEntity.getRevisions());
        Assert.assertEquals(entity.getWarranty(), resultEntity.getWarranty());
    }

    /**
     * Prueba para eliminar un Car
     *
     * @generated
     */
    @Test
    public void deleteCarTest() {
        CarEntity entity = data.get(0);
        carLogic.deleteCar(entity.getId());
        CarEntity deleted = em.find(CarEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un Car
     *
     * @generated
     */
    @Test
    public void updateCarTest() {
        CarEntity entity = data.get(0);
        CarEntity pojoEntity = factory.manufacturePojo(CarEntity.class);

        pojoEntity.setId(entity.getId());

        carLogic.updateCar(fatherEntity.getId(), pojoEntity);

        CarEntity resp = em.find(CarEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getName(), resp.getName());
        Assert.assertEquals(pojoEntity.getImage(), resp.getImage());
        Assert.assertEquals(pojoEntity.getPrice(), resp.getPrice());
        Assert.assertEquals(pojoEntity.getRevisions(), resp.getRevisions());
        Assert.assertEquals(pojoEntity.getWarranty(), resp.getWarranty());
    }
}

