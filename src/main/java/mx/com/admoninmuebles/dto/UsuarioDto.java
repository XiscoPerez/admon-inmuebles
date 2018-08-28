package mx.com.admoninmuebles.dto;

import java.util.Collection;


import lombok.Data;

@Data
public class UsuarioDto {

    private Long id;

    private String username;

    private String nombre;

    private String apellidoPaterno;

    private String apellidoMaterno;

    private String correo;

    private boolean cuentaExpirada;

    private boolean cuentaBloqueada;

    private boolean credencialesExpiradas;

    private boolean activo = true;

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
