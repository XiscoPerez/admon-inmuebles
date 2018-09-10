package mx.com.admoninmuebles.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class MensajeContactoDto {
	
    private Long id;

    @NotNull
    private String nombre;
    
    private String inmueble;

    @NotNull
    private String correo;

    @NotNull
    private String telefono;

    @NotNull
    private String mensaje;
    
    private boolean atendido;

}
