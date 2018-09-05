package mx.com.admoninmuebles.dto;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ProveedorDto {
	
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

    private String direccionCalle;
    private String direccionNumeroExterior;
    private String direccionNumeroInterior;
    private String direccionEntreCalles;
    private String direccionReferencias;
    private Long direccionAsentamientoId;
    private String direccionAsentamientoNombre;
    private String datosAdicionalesNombreRepresentante;
    private String datosAdicionalesRazonSocial;
    private String datosAdicionalesRfc;
    private String datosAdicionalesTelefono;
    private String datosAdicionalesCorreo;
    private String datosAdicionalesNumeroCuenta;
    
    private Collection<TelefonoDto> telefonos;

}
