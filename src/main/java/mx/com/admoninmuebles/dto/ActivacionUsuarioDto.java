package mx.com.admoninmuebles.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ActivacionUsuarioDto {
	
	@NotNull
	private String token;
	
	@NotNull
    @Size(min = 6, max = 100)
	private String contrasenia;
	
	@NotNull
    @Size(min = 6, max = 100)
	private String confirmacionContrasenia;
	
	
	

}
