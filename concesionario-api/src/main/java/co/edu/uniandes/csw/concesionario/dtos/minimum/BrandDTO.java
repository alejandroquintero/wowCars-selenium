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
package co.edu.uniandes.csw.concesionario.dtos.minimum;

import co.edu.uniandes.csw.concesionario.entities.BrandEntity;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @generated
 */
@XmlRootElement
public class BrandDTO  implements Serializable{

    private Long id;
    private String name;
    private String slogan; 
    private String originCountry;

    /**
     * @generated
     */
    public BrandDTO() {
    }

    /**
     * Crea un objeto BrandDTO a partir de un objeto BrandEntity.
     *
     * @param entity Entidad BrandEntity desde la cual se va a crear el nuevo objeto.
     * @generated
     */
    public BrandDTO(BrandEntity entity) {
	   if (entity!=null){
        this.id=entity.getId();
        this.name=entity.getName();
        this.slogan = entity.getSlogan();
        this.originCountry = entity.getOriginCountry();
       }
    }

    /**
     * Convierte un objeto BrandDTO a BrandEntity.
     *
     * @return Nueva objeto BrandEntity.
     * @generated
     */
    public BrandEntity toEntity() {
        BrandEntity entity = new BrandEntity();
        entity.setId(this.getId());
        entity.setName(this.getName());
        entity.setSlogan(this.getSlogan());
        entity.setOriginCountry(this.getOriginCountry());
        
    return entity;
    }

    /**
     * Obtiene el atributo id.
     *
     * @return atributo id.
     * @generated
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el valor del atributo id.
     *
     * @param id nuevo valor del atributo
     * @generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el atributo name.
     *
     * @return atributo name.
     * @generated
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el valor del atributo name.
     *
     * @param name nuevo valor del atributo
     * @generated
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene el atributo slogan.
     *
     * @return atributo slogan.
     * @generated
     */
    public String getSlogan() {
        return slogan;
    }
    
    /**
     * Establece el valor del atributo slogan.
     *
     * @param slogan nuevo valor del atributo
     * @generated
     */
    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }
    
    /**
     * Obtiene el atributo originCountry.
     *
     * @return atributo originCountry.
     * @generated
     */
    public String getOriginCountry() {
        return originCountry;
    }
    
    /**
     * Establece el valor del atributo originCountry.
     *
     * @param originCountry nuevo valor del atributo
     * @generated
     */
    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

}
