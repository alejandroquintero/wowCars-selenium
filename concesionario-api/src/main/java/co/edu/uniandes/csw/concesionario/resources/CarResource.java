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
package co.edu.uniandes.csw.concesionario.resources;

import co.edu.uniandes.csw.auth.filter.StatusCreated;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import co.edu.uniandes.csw.concesionario.api.ICarLogic;
import co.edu.uniandes.csw.concesionario.dtos.detail.CarDetailDTO;
import co.edu.uniandes.csw.concesionario.entities.CarEntity;
import java.util.ArrayList;
import javax.ws.rs.WebApplicationException;

/**
 * URI: brands/{brandsId: \\d+}/car
 * @generated
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CarResource {

    @Inject private ICarLogic carLogic;
    @Context private HttpServletResponse response;
    @QueryParam("page") private Integer page;
    @QueryParam("limit") private Integer maxRecords;
    @PathParam("brandsId") private Long brandsId;

   
    /**
     * Convierte una lista de CarEntity a una lista de CarDetailDTO
     *
     * @param entityList Lista de CarEntity a convertir
     * @return Lista de CarDetailDTO convertida
     * @generated
     */
    private List<CarDetailDTO> listEntity2DTO(List<CarEntity> entityList){
        List<CarDetailDTO> list = new ArrayList<>();
        for (CarEntity entity : entityList) {
            list.add(new CarDetailDTO(entity));
        }
        return list;
    }


    /**
     * Obtiene la lista de los registros de Car asociados a un Brand
     *
     * @return Colección de objetos de CarDetailDTO
     * @generated
     */
    @GET
    public List<CarDetailDTO> getCars() {
        if (page != null && maxRecords != null) {
            this.response.setIntHeader("X-Total-Count", carLogic.countCars());
            return listEntity2DTO(carLogic.getCars(page, maxRecords, brandsId));
        }
        return listEntity2DTO(carLogic.getCars(brandsId));
    }

    /**
     * Obtiene los datos de una instancia de Car a partir de su ID asociado a un Brand
     *
     * @param carId Identificador de la instancia a consultar
     * @return Instancia de CarDetailDTO con los datos del Car consultado
     * @generated
     */
    @GET
    @Path("{carId: \\d+}")
    public CarDetailDTO getCar(@PathParam("carId") Long carId) {
        CarEntity entity = carLogic.getCar(carId);
        if (entity.getBrand() != null && !brandsId.equals(entity.getBrand().getId())) {
            throw new WebApplicationException(404);
        }
        return new CarDetailDTO(entity);
    }

    /**
     * Asocia un Car existente a un Brand
     *
     * @param dto Objeto de CarDetailDTO con los datos nuevos
     * @return Objeto de CarDetailDTOcon los datos nuevos y su ID.
     * @generated
     */
    @POST
    @StatusCreated
    public CarDetailDTO createCar(CarDetailDTO dto)  {
        return new CarDetailDTO(carLogic.createCar(brandsId, dto.toEntity()));
    }

    /**
     * Actualiza la información de una instancia de Car.
     *
     * @param carId Identificador de la instancia de Car a modificar
     * @param dto Instancia de CarDetailDTO con los nuevos datos.
     * @return Instancia de CarDetailDTO con los datos actualizados.
     * @generated
     */
    @PUT
    @Path("{carId: \\d+}")
    public CarDetailDTO updateCar(@PathParam("carId") Long carId, CarDetailDTO dto) {
        CarEntity entity = dto.toEntity();
        entity.setId(carId);
        return new CarDetailDTO(carLogic.updateCar(brandsId, entity));
    }

    /**
     * Elimina una instancia de Car de la base de datos.
     *
     * @param carId Identificador de la instancia a eliminar.
     * @generated
     */
    @DELETE
    @Path("carId: \\d+}")
    public void deleteCar(@PathParam("carId") Long carId) {
        carLogic.deleteCar(carId);
    }
    


}
