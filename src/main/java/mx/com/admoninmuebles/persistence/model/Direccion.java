package mx.com.admoninmuebles.persistence.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "direcciones")
@Data
@EqualsAndHashCode(callSuper = false)
public class Direccion extends EntidadBase {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_direccion")
    private Long id;

    @NotNull
    @Size(min = 1, max = 5)
    @Column(length = 5, nullable = false)
    private String codigoPostal;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(length = 100, nullable = false)
    private String calle;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(length = 50, nullable = false)
    private String numeroExterior;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(length = 50)
    private String numeroInterior;

    @NotNull
    @Size(min = 1, max = 200)
    @Column(length = 50)
    private String entreCalles;

    @NotNull
    @Size(min = 1, max = 500)
    @Column(length = 500, columnDefinition = "text")
    private String referencias;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_asentamiento", referencedColumnName = "id_asentamiento", nullable = false)
    private Asentamiento asentamiento;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "direccion")
    private Usuario usuario;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "direccion")
    private BienInmueble bienInmueble;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "direccion")
    private Sucursal sucursal;

}
