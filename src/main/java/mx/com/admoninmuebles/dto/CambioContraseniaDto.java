package mx.com.admoninmuebles.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CambioContraseniaDto {
	
    @NotNull
    @Size(min = 6, max = 100)
	private String contraseniaAnterior;
    
    @NotNull
    @Size(min = 6, max = 100)
	private String contraseniaNueva;
    
    @NotNull
    @Size(min = 6, max = 100)
	private String contraseniaConfirmacion;
	
	

}
