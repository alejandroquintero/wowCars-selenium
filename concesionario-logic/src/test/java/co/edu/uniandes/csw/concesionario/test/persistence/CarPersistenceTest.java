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
package co.edu.uniandes.csw.concesionario.test.persistence;
import co.edu.uniandes.csw.concesionario.entities.BrandEntity;
import co.edu.uniandes.csw.concesionario.entities.CarEntity;
import co.edu.uniandes.csw.concesionario.persistence.CarPersistence;
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
public class CarPersistenceTest {

    /**
     * @generated
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CarEntity.class.getPackage())
                .addPackage(CarPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * @generated
     */
    BrandEntity fatherEntity;

    /**
     * @generated
     */
    @Inject
    private CarPersistence carPersistence;

    /**
     * @generated
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * @generated
     */
    @Inject
    UserTransaction utx;

    /**
     * Configuración inicial de la prueba.
     *
     * @generated
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            em.joinTransaction();
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
        em.createQuery("delete from BrandEntity").executeUpdate();
    }

    /**
     * @generated
     */
    private List<CarEntity> data = new ArrayList<CarEntity>();

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     *
     * @generated
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
            fatherEntity = factory.manufacturePojo(BrandEntity.class);
            fatherEntity.setId(1L);
            em.persist(fatherEntity);
        for (int i = 0; i < 3; i++) {
            CarEntity entity = factory.manufacturePojo(CarEntity.class);
            
            entity.setBrand(fatherEntity);
            em.persist(entity);
            data.add(entity);
        }
    }
    /**
     * Prueba para crear un Car.
     *
     * @generated
     */
    @Test
    public void createCarTest() {
		PodamFactory factory = new PodamFactoryImpl();
        CarEntity newEntity = factory.manufacturePojo(CarEntity.class);
        newEntity.setBrand(fatherEntity);
        CarEntity result = carPersistence.create(newEntity);

        Assert.assertNotNull(result);

        CarEntity entity = em.find(CarEntity.class, result.getId());

        Assert.assertEquals(newEntity.getName(), entity.getName());
        Assert.assertEquals(newEntity.getImage(), entity.getImage());
        Assert.assertEquals(newEntity.getPrice(), entity.getPrice());
        Assert.assertEquals(newEntity.getRevisions(), entity.getRevisions());
        Assert.assertEquals(newEntity.getWarranty(), entity.getWarranty());
    }

    /**
     * Prueba para consultar la lista de Cars.
     *
     * @generated
     */
    @Test
    public void getCarsTest() {
        List<CarEntity> list = carPersistence.findAll(null, null, fatherEntity.getId());
        Assert.assertEquals(data.size(), list.size());
        for (CarEntity ent : list) {
            boolean found = false;
            for (CarEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Car.
     *
     * @generated
     */
    @Test
    public void getCarTest() {
        CarEntity entity = data.get(0);
        CarEntity newEntity = carPersistence.find(entity.getBrand().getId(), entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getName(), newEntity.getName());
        Assert.assertEquals(entity.getImage(), newEntity.getImage());
        Assert.assertEquals(entity.getPrice(), newEntity.getPrice());
        Assert.assertEquals(entity.getRevisions(), newEntity.getRevisions());
        Assert.assertEquals(entity.getWarranty(), newEntity.getWarranty());
    }

    /**
     * Prueba para eliminar un Car.
     *
     * @generated
     */
    @Test
    public void deleteCarTest() {
        CarEntity entity = data.get(0);
        carPersistence.delete(entity.getId());
        CarEntity deleted = em.find(CarEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un Car.
     *
     * @generated
     */
    @Test
    public void updateCarTest() {
        CarEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        CarEntity newEntity = factory.manufacturePojo(CarEntity.class);

        newEntity.setId(entity.getId());

        carPersistence.update(newEntity);

        CarEntity resp = em.find(CarEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getName(), resp.getName());
        Assert.assertEquals(newEntity.getImage(), resp.getImage());
        Assert.assertEquals(newEntity.getPrice(), resp.getPrice());
        Assert.assertEquals(newEntity.getRevisions(), resp.getRevisions());
        Assert.assertEquals(newEntity.getWarranty(), resp.getWarranty());
    }
}
