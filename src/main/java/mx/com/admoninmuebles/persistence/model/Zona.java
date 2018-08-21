package mx.com.admoninmuebles.persistence.model;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import mx.com.admoninmuebles.utils.Utils;

@Entity
@Table(name = "zonas")
@NamedQuery(name = "Zona.findAll", query = "SELECT z FROM Zona z")
@Data
public class Zona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_zona")
    private Long id;

    @NotNull
    @Size(min = 4, max = 100)
    @Column(length = 100, nullable = false)
    private String nombre;

    @NotNull
    @Size(min = 4, max = 10)
    @Column(length = 10, unique = true, nullable = false)
    private String codigo;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "zona")
    private Collection<Asentamiento> asentamientos;

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", nullable = true)
    private Usuario usuario;

    @ManyToOne
    private Usuario creadoPor;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    @ManyToOne
    private Usuario modificadoPor;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;

    @PrePersist
    public void prePersist() {
        Optional<Usuario> optUsuario = Utils.getCurrentAuditor();
        if (optUsuario.isPresent()) {
            creadoPor = optUsuario.get();
        }
        fechaCreacion = new Date();

    }

    @PreUpdate
    public void preUpdate() {
        Optional<Usuario> optUsuario = Utils.getCurrentAuditor();
        if (optUsuario.isPresent()) {
            modificadoPor = optUsuario.get();
        }
        fechaModificacion = new Date();

    }

}
