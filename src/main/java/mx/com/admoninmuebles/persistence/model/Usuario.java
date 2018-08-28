package mx.com.admoninmuebles.persistence.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The persistent class for the users database table.
 *
 */
@Entity
@Table(name = "usuarios")
@NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
@Data
@EqualsAndHashCode(callSuper = false)
public class Usuario extends EntidadBase {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_usuario", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    private String nombre;

    private String apellidoPaterno;

    private String apellidoMaterno;

    private String correo;

    @Column(name = "cuenta_expirada")
    private boolean cuentaExpirada;

    @Column(name = "cuenta_bloqueada")
    private boolean cuentaBloqueada;

    @Column(name = "credenciales_expiradas")
    private boolean credencialesExpiradas;

    private boolean activo = true;

    private String identificador;

    private String contrasenia;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Collection<Rol> roles;

    @OneToMany(mappedBy = "adminZona")
    public Collection<Zona> zonas;

    @OneToMany(mappedBy = "adminBi")
    private Collection<BienInmueble> bienInmueble;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "usuarioAsignado")
    private Collection<Ticket> tickets;

    // Proveedor
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_datos_adicionales", referencedColumnName = "id_datos_adicionales")
    private DatosAdicionales datosAdicionales;

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_direccion", referencedColumnName = "id_direccion", nullable = true)
    private Direccion direccion;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "usuario")
    private Collection<Telefono> telefonos;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_areas_servicios",
            joinColumns = @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_area_servicio", referencedColumnName = "id_area_servicio"))
    private Collection<AreaServicio> areasServicios;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "usuario")
    private Collection<Comentario> comentarios;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "usuario")
    private Collection<Pago> pagos;
}