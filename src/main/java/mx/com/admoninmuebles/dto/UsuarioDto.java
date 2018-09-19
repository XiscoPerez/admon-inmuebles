package mx.com.admoninmuebles.dto;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonInclude;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

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

    private String telefonoFijo;

    private String telefonoOficina;

    private String telefonoMovil;

    private String telefonoAlternativo;

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
    
    private String datosDomicilio;
    
    private MultipartFile imagen;
    
    private Collection<RolDto> roles;
    private Long rolSeleccionado;
    private String zonaSeleccionado;
    private Long coloniaSeleccionado;
    
    private Long inmuebleId;
    private String inmuebleNombre;
    private Long inmuebleDireccionAsentamientoId;
    private String inmuebleDireccionAsentamientoZonaCodigo;
    

    public String getNombreCompleto() {
        return String.format("%s %s %s", nombre, apellidoPaterno, apellidoMaterno);
    }

}
