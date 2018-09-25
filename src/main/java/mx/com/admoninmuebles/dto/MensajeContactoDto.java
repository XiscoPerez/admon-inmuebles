package mx.com.admoninmuebles.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class MensajeContactoDto {
	
    private Long id;

    @NotNull
    private String nombre;
    
    @NotNull
    private String correo;

    @NotNull
    private String telefono;

    @NotNull
    private String mensaje;
    
    private Long MensajeContactoEstatusId;
    
    private String MensajeContactoEstatusNombre;
    
    private Long sectorId;
    
    private String sectorNombre;
    
//    private boolean atendido;

}
