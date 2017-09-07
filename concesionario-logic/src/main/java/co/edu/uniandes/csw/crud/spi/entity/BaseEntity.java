package co.edu.uniandes.csw.crud.spi.entity;
import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
/**
 * Generic entity with ID and name fields to inherit from.
 * 
 * This entity sets a standar of fields and functions all entities in a project should have.
 * For example, all entities should be compared by ID when not null, otherwise use the object equals method.
 *
 * @author jd.patino10
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String slogan;
    private String originCountry;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }
    
    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this.getId() != null && ((BaseEntity) obj).getId() != null) {
            return this.getId().equals(((BaseEntity) obj).getId());
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        if (this.getId() != null) {
            return this.getId().hashCode();
        }
        return super.hashCode();
    }
}

