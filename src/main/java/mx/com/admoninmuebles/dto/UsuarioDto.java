package mx.com.admoninmuebles.dto;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UsuarioDto {

    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String nombre;

    @NotNull
    private String apellidoPaterno;

    private String apellidoMaterno;

    @NotNull
    private String correo;
    
    private String telefono;
    
    private String facebook;
    
    private String twiter;
    
    private String youtube;
    
    private String googleMapsDir;
    
    private String fotoUrl;

    private boolean cuentaExpirada;

    private boolean cuentaBloqueada;

    private boolean credencialesExpiradas;

    private boolean activo;

    private String identificador;

    private String contrasenia;

    private Collection<RolDto> roles;
    
    private Collection<Long> rolesSeleccionados;
//
//    public Collection<String> zonas;
//
//    private Collection<Long> bienInmueble;
//
//    private Collection<Long> tickets;
//
//    private Long id;
//
//    private Long id;
//
//    private Collection<Long> telefonos;
//
//    private Collection<Long> areasServicios;
//
//    private Collection<Long> comentarios;
//
//    private Collection<Long> pagos;

}
