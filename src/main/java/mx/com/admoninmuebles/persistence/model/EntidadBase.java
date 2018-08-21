
package mx.com.admoninmuebles.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Data;

@MappedSuperclass
@Data
public abstract class EntidadBase implements Serializable {
    private static final long serialVersionUID = 1L;

    @CreatedBy
    @ManyToOne
    private Usuario creadoPor;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    @LastModifiedBy
    @ManyToOne
    private Usuario modificadoPor;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;

}