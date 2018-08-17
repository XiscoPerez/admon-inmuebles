
package mx.com.admoninmuebles.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class EntidadBase implements Serializable {
    private static final long serialVersionUID = 1L;

    @CreatedBy
    private Long creadoPor;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    @LastModifiedBy
    private Long modificadoPor;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;

}