package mx.com.admoninmuebles.persistence.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "notificaciones")
@Data
@EqualsAndHashCode(callSuper = false)
public class Notificacion extends EntidadBase {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificacion")
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(length = 100, unique = true, nullable = false)
    private String titulo;

    @NotNull
    @Size(min = 1, max = 1000)
    @Column(length = 1000, columnDefinition = "text", nullable = false)
    private String descripcion;

    @NotNull
    @Column(nullable = false)
    private Date fechaExposicionInicial;

    @NotNull
    @Column(nullable = false)
    private Date fechaExposicionFinal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_bien_inmueble", referencedColumnName = "id_bien_inmueble", nullable = false)
    private BienInmueble bienInmueble;

}
