package mx.com.admoninmuebles.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
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
    @Size(min = 1, max = 100)
    @Column(length = 100, nullable = false)
    private String calle;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(length = 50, nullable = false)
    private String numeroExterior;

    @Size(min = 1, max = 50)
    @Column(length = 50)
    private String numeroInterior;

    @Size(min = 0, max = 200)
    @Column(length = 50)
    private String entreCalles;

    @Size(min = 0, max = 500)
    @Column(length = 500, columnDefinition = "text")
    private String referencias;

    @ManyToOne
    @JoinColumn(name = "id_asentamiento", referencedColumnName = "id_asentamiento", nullable = false)
    private Asentamiento asentamiento;

}
