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
package co.edu.uniandes.csw.concesionario.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import co.edu.uniandes.csw.crud.spi.entity.BaseEntity;


/**
 * @generated
 */
@Entity
public class ClientEntity extends BaseEntity implements Serializable {

    private String email;

    private Long phone;
    
    private String address; // atributo nuevo

    /**
     * Obtiene el atributo email.
     *
     * @return atributo email.
     * @generated
     */
    public String getEmail(){
        return email;
    }

    /**
     * Establece el valor del atributo email.
     *
     * @param email nuevo valor del atributo
     * @generated
     */
    public void setEmail(String email){
        this.email = email;
    }

    /**
     * Obtiene el atributo phone.
     *
     * @return atributo phone.
     * @generated
     */
    public Long getPhone(){
        return phone;
    }

    /**
     * Establece el valor del atributo phone.
     *
     * @param phone nuevo valor del atributo
     * @generated
     */
    public void setPhone(Long phone){
        this.phone = phone;
    }
    
    /**
     * Obtiene el atributo address.
     *
     * @return atributo address.
     * @generated
     */
    public String getAddress(){
       return address; 
   }
   /**
     * Establece el valor del atributo address.
     *
     * @param address nuevo valor del atributo
     * @generated
     */
   public void setAddress(String address){
       this.address = address;
   }
}
