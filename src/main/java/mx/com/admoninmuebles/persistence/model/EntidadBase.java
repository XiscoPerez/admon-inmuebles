
package mx.com.admoninmuebles.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class EntidadBase implements Serializable {
    private static final long serialVersionUID = 1L;

    @LastModifiedBy
    @ManyToOne
    private Usuario modificadoPor;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;

}