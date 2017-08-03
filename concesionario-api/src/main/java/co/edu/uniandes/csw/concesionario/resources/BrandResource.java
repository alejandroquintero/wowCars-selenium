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
import co.edu.uniandes.csw.concesionario.api.IBrandLogic;
import co.edu.uniandes.csw.concesionario.dtos.detail.BrandDetailDTO;
import co.edu.uniandes.csw.concesionario.entities.BrandEntity;
import java.util.ArrayList;
import javax.ws.rs.WebApplicationException;
import co.edu.uniandes.csw.concesionario.exceptions.BusinessLogicException;

/**
 * URI: brands/
 * @generated
 */
@Path("/brands")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BrandResource {

    @Inject private IBrandLogic brandLogic;
    @Context private HttpServletResponse response;
    @QueryParam("page") private Integer page;
    @QueryParam("limit") private Integer maxRecords;

   
    /**
     * Convierte una lista de BrandEntity a una lista de BrandDetailDTO.
     *
     * @param entityList Lista de BrandEntity a convertir.
     * @return Lista de BrandDetailDTO convertida.
     * @generated
     */
    private List<BrandDetailDTO> listEntity2DTO(List<BrandEntity> entityList){
        List<BrandDetailDTO> list = new ArrayList<>();
        for (BrandEntity entity : entityList) {
            list.add(new BrandDetailDTO(entity));
        }
        return list;
    }


    /**
     * Obtiene la lista de los registros de Brand
     *
     * @return Colección de objetos de BrandDetailDTO
     * @generated
     */
    @GET
    public List<BrandDetailDTO> getBrands() {
        if (page != null && maxRecords != null) {
            this.response.setIntHeader("X-Total-Count", brandLogic.countBrands());
            return listEntity2DTO(brandLogic.getBrands(page, maxRecords));
        }
        return listEntity2DTO(brandLogic.getBrands());
    }

    /**
     * Obtiene los datos de una instancia de Brand a partir de su ID
     *
     * @param id Identificador de la instancia a consultar
     * @return Instancia de BrandDetailDTO con los datos del Brand consultado
     * @generated
     */
    @GET
    @Path("{id: \\d+}")
    public BrandDetailDTO getBrand(@PathParam("id") Long id) {
        return new BrandDetailDTO(brandLogic.getBrand(id));
    }

    /**
     * Se encarga de crear un Brand en la base de datos
     *
     * @param dto Objeto de BrandDetailDTO con los datos nuevos
     * @return Objeto de BrandDetailDTOcon los datos nuevos y su ID
     * @generated
     */
    @POST
    @StatusCreated
    public BrandDetailDTO createBrand(BrandDetailDTO dto)throws BusinessLogicException  {
         
        return new BrandDetailDTO(brandLogic.createBrand(dto.toEntity()));
    }

    /**
     * Actualiza la información de una instancia de Brand
     *
     * @param id Identificador de la instancia de Brand a modificar
     * @param dto Instancia de BrandDetailDTO con los nuevos datos
     * @return Instancia de BrandDetailDTO con los datos actualizados
     * @generated
     */
    @PUT
    @Path("{id: \\d+}")
    public BrandDetailDTO updateBrand(@PathParam("id") Long id, BrandDetailDTO dto) {
        BrandEntity entity = dto.toEntity();
        entity.setId(id);
        return new BrandDetailDTO(brandLogic.updateBrand(entity));
    }

    /**
     * Elimina una instancia de Brand de la base de datos
     *
     * @param id Identificador de la instancia a eliminar
     * @generated
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteBrand(@PathParam("id") Long id) {
        brandLogic.deleteBrand(id);
    }
    public void existsBrand(Long brandsId){
        BrandDetailDTO brand = getBrand(brandsId);
        if (brand== null) {
            throw new WebApplicationException(404);
        }
    }
    
    
    @Path("{brandsId: \\d+}/car")
    public Class<CarResource> getCarResource(@PathParam("brandsId") Long brandsId){
        existsBrand(brandsId);
        return CarResource.class;
    }
   
}
