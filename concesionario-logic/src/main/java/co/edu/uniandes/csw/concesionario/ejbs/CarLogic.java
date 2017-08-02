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
package co.edu.uniandes.csw.concesionario.ejbs;

import co.edu.uniandes.csw.concesionario.api.ICarLogic;
import co.edu.uniandes.csw.concesionario.entities.CarEntity;
import co.edu.uniandes.csw.concesionario.persistence.CarPersistence;
import co.edu.uniandes.csw.concesionario.api.IBrandLogic;
import co.edu.uniandes.csw.concesionario.entities.BrandEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;

/**
 * @generated
 */
@Stateless
public class CarLogic implements ICarLogic {

    @Inject private CarPersistence persistence;

    @Inject
    private IBrandLogic brandLogic;

    /**
     * Obtiene el número de registros de Car.
     *
     * @return Número de registros de Car.
     * @generated
     */
    public int countCars() {
        return persistence.count();
    }

    /**
     * Obtiene la lista de los registros de Car que pertenecen a un Brand.
     *
     * @param brandid id del Brand el cual es padre de los Cars.
     * @return Colección de objetos de CarEntity.
     * @generated
     */
    @Override
    public List<CarEntity> getCars(Long brandid) {
        BrandEntity brand = brandLogic.getBrand(brandid);
        return brand.getCar();
        
    }

    /**
     * Obtiene la lista de los registros de Car que pertenecen a un Brand indicando los datos para la paginación.
     *
     * @param page Número de página.
     * @param maxRecords Número de registros que se mostraran en cada página.
     * @param brandid id del Brand el cual es padre de los Cars.
     * @return Colección de objetos de CarEntity.
     * @generated
     */
    @Override
    public List<CarEntity> getCars(Integer page, Integer maxRecords, Long brandid) {
        return persistence.findAll(page, maxRecords, brandid);
    }

    /**
     * Obtiene los datos de una instancia de Car a partir de su ID.
     *
     * @pre La existencia del elemento padre Brand se debe garantizar.
     * @param carid) Identificador del Car a consultar
     * @return Instancia de CarEntity con los datos del Car consultado.
     * @generated
     */
    @Override
    public CarEntity getCar(Long carid) {
        try {
            return persistence.find(carid);
        }catch(NoResultException e){
            throw new IllegalArgumentException("El Car no existe");
        }
    }

    /**
     * Se encarga de crear un Car en la base de datos.
     *
     * @param entity Objeto de CarEntity con los datos nuevos
     * @param brandid id del Brand el cual sera padre del nuevo Car.
     * @return Objeto de CarEntity con los datos nuevos y su ID.
     * @generated
     */
    @Override
    public CarEntity createCar(Long brandid, CarEntity entity) {
        BrandEntity brand = brandLogic.getBrand(brandid);
        entity.setBrand(brand);
        entity = persistence.create(entity);
        return entity;
    }

    /**
     * Actualiza la información de una instancia de Car.
     *
     * @param entity Instancia de CarEntity con los nuevos datos.
     * @param brandid id del Brand el cual sera padre del Car actualizado.
     * @return Instancia de CarEntity con los datos actualizados.
     * @generated
     */
    @Override
    public CarEntity updateCar(Long brandid, CarEntity entity) {
        BrandEntity brand = brandLogic.getBrand(brandid);
        entity.setBrand(brand);
        return persistence.update(entity);
    }

    /**
     * Elimina una instancia de Car de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     * @param brandid id del Brand el cual es padre del Car.
     * @generated
     */
    @Override
    public void deleteCar(Long id) {
        CarEntity old = getCar(id);
        persistence.delete(old.getId());
    }
  
}
