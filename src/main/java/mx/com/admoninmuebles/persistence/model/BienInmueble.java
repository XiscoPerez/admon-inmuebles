package mx.com.admoninmuebles.persistence.model;

import java.math.BigDecimal;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "bienes_inmuebles")
@Data
@EqualsAndHashCode(callSuper = false)
public class BienInmueble extends EntidadBase {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bien_inmueble")
    private Long id;

    @NotNull
    @Size(min = 1, max = 200)
    @Column(length = 200, unique = true, nullable = false)
    private String nombre;

    @Min(value = 1)
    @Max(value = 31)
    @Column(nullable = false)
    private Integer diaCuotaOrdinaria;

    @Digits(integer = 5, fraction = 2)
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal montoCuotaOrdinaria;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(length = 100, nullable = false)
    private String imagenUrl;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_direccion", referencedColumnName = "id_direccion", nullable = false)
    private Direccion direccion;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    public Usuario adminBi;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "bienInmueble")
    private Collection<Notificacion> notificaciones;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_datos_adicionales", referencedColumnName = "id_datos_adicionales")
    private DatosAdicionales datosAdicionales;

}
