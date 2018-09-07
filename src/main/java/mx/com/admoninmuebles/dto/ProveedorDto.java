package mx.com.admoninmuebles.dto;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ProveedorDto extends UsuarioDto{
	
    private String comentario;
    private String codigoPostal;
    @NotNull
    private String direccionCalle;
    
    @NotNull
    private String direccionNumeroExterior;
    private String direccionNumeroInterior;
    private String direccionEntreCalles;
    private String direccionReferencias;
    
    private String direccionAsentamientoNombre;
    
    @NotNull
    private Long direccionAsentamientoId;
    
    @NotNull
    private String datosAdicionalesNombreRepresentante;
    
    @NotNull
    private String datosAdicionalesRazonSocial;
    
    @NotNull
    private String datosAdicionalesRfc;
    private String datosAdicionalesTelefono;
    private String datosAdicionalesCorreo;
    private String datosAdicionalesNumeroCuenta;
    
    private Collection<AreaServicioDto> areasServicio;
    private Collection<ComentarioDto> comentarios;
    private Collection<Long> areasServicioSeleccionados;

}
