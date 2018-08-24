package mx.com.admoninmuebles.persistence.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "zonas")
@NamedQuery(name = "Zona.findAll", query = "SELECT z FROM Zona z")
@Data
@EqualsAndHashCode(callSuper = false)
public class Zona extends EntidadBase {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_zona")
    private Long idZona;

    @NotNull
    @Size(min = 4, max = 10)
    @Column(length = 10, unique = true, nullable = false)
    private String codigo;

    @NotNull
    @Size(min = 4, max = 100)
    @Column(length = 100, nullable = false)
    private String nombre;

    @ManyToOne
    public Usuario adminZona;

    @OneToMany(mappedBy = "zona")
    public Collection<Asentamiento> asentamientos;

}
